package io.pumelo.db.repo;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础数据Dao访问封装实现
 */
public class BaseDaoImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseDao<T, ID> {
    private final EntityManager em;
    private Class<T> domainClass;

    public final static Map<String,AbstractSingleColumnStandardBasicType> standardBasicTypeMap;

    static {
        standardBasicTypeMap = new HashMap<>();//java类型对应的hibernate类型
        standardBasicTypeMap.put("int", StandardBasicTypes.INTEGER);
        standardBasicTypeMap.put("java.lang.Integer", StandardBasicTypes.INTEGER);
        standardBasicTypeMap.put("long", StandardBasicTypes.LONG);
        standardBasicTypeMap.put("java.lang.Long", StandardBasicTypes.LONG);
        standardBasicTypeMap.put("float", StandardBasicTypes.FLOAT);
        standardBasicTypeMap.put("java.lang.Float", StandardBasicTypes.FLOAT);
        standardBasicTypeMap.put("char", StandardBasicTypes.CHARACTER);
        standardBasicTypeMap.put("java.lang.Character", StandardBasicTypes.CHARACTER);
        standardBasicTypeMap.put("byte", StandardBasicTypes.BYTE);
        standardBasicTypeMap.put("java.lang.Byte", StandardBasicTypes.BYTE);
        standardBasicTypeMap.put("boolean", StandardBasicTypes.BOOLEAN);
        standardBasicTypeMap.put("java.lang.Boolean", StandardBasicTypes.BOOLEAN);
        standardBasicTypeMap.put("short", StandardBasicTypes.SHORT);
        standardBasicTypeMap.put("java.lang.Short", StandardBasicTypes.SHORT);
        standardBasicTypeMap.put("double", StandardBasicTypes.DOUBLE);
        standardBasicTypeMap.put("java.lang.Double", StandardBasicTypes.DOUBLE);
        standardBasicTypeMap.put("java.lang.String", StandardBasicTypes.STRING);
        standardBasicTypeMap.put("java.math.BigInteger", StandardBasicTypes.BIG_INTEGER);
        standardBasicTypeMap.put("java.util.Date", StandardBasicTypes.DATE);
    }

    public BaseDaoImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.em = entityManager;
        this.domainClass = domainClass;
    }

    public <S> List<S> findBySql(String sql) {
        Query query = em.createNativeQuery(sql);
        return query.getResultList();
    }

    public <S> List<S> findAllByCustom(String fromSql, Class<S> clazz){
        if(StringUtils.isBlank(fromSql)){
            return null;
        }
        if(clazz == null){
            return null;
        }
        String querySql = "select " + getFieldSql(clazz) + " from " + checkSql(fromSql);
        NativeQuery sqlQuery = em.createNativeQuery(querySql).unwrap(NativeQuery.class);
        sqlQuery = setResultTransformer(sqlQuery, clazz);
        return sqlQuery.list();
    }

    @Override
    public <S> Page<S> findPageByCustom(String fromSql, Pageable pageable, Class<S> clazz) {//目前仅适用于mysql
        if(StringUtils.isBlank(fromSql)){
            return null;
        }
        if(clazz == null){
            return null;
        }
        if(pageable == null){
            pageable = PageRequest.of(0,10);
        }
        int index = pageable.getPageNumber() * pageable.getPageSize();
        int page = pageable.getPageSize();
        String sql = "select " + getFieldSql(clazz) + " from " + checkSql(fromSql);
        Query countQuery = em.createNativeQuery(sql);
        int count = countQuery.getResultList().size();
        String pageSql = sql + " limit " + index + "," + page ;
        NativeQuery pageQuery = em.createNativeQuery(pageSql).unwrap(NativeQuery.class);
        pageQuery = setResultTransformer(pageQuery, clazz);
        List<S> list = pageQuery.list();
        return new PageImpl<>(list,pageable,count);
    }

    @Override
    public int executeUpdateSql(String sql) {
        Query query = em.createNativeQuery(checkSql(sql));
        return query.executeUpdate();
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    private <S> NativeQuery setResultTransformer(NativeQuery query, Class<S> clazz){//设置返回值
        query.setResultTransformer(Transformers.aliasToBean(clazz));
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            String fieldType = fields[i].getGenericType().getTypeName();
            AbstractSingleColumnStandardBasicType basicType = standardBasicTypeMap.get(fieldType);
            if(basicType == null){
                throw new UnsupportedOperationException("不支持的字段类型：" + fieldType + ",字段名称：" + fieldName);
            }
            query.addScalar(fieldName, basicType);
        }
        return query;
    }

    private <S> String getFieldSql(Class<S> clazz){//拼装查询字段
        String fieldSql = "";
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            Column annotation = fields[i].getAnnotation(Column.class);
            if(annotation == null){
                fieldSql += "," + change2field(fieldName) + " as " + fieldName;
            }else {
                fieldSql += "," + annotation.name() + " as " + fieldName;
            }
        }
        return fieldSql.substring(1);
    }


    private String checkSql(String sql){//防止sql注入
        return sql.replaceAll("([;])+|(--)+"," ");
    }

    private String change2field(String fieldName) {//转成默认生成的数据库字段
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<fieldName.length();i++){
            char c = fieldName.charAt(i);
            if(Character.isUpperCase(c)){
                sb.append("_" + Character.toLowerCase(c));
            }else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
