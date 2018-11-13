package io.pumelo.db.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * 基础数据Dao访问封装
 */

@Transactional(readOnly = true)
@NoRepositoryBean
public interface BaseDao<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * 原生sql查询
     * @param sql
     * @param <S>
     * @return
     */
    <S> List<S> findBySql(String sql);

    /**
     * sql查询
     * @param fromSql sql，从from开始，也就是说select * from (fromSql)
     * @param clazz 需要生成的对象，如果字段是自定义的，请加上注解@Column
     * @param <S>
     * @return
     */
    <S> List<S> findAllByCustom(String fromSql, Class<S> clazz);

    /**
     * sql分页查询
     * @param fromSql sql，从from开始，也就是说select * from (fromSql)
     * @param clazz 需要生成的对象，如果字段是自定义的，请加上注解@Column
     * @param pageable
     * @return
     */
    <S> Page<S> findPageByCustom(String fromSql, Pageable pageable, Class<S> clazz);

    /**
     * 执行sql更新或删除，注意需要事物@Transactional
     * @param sql 原生sql
     * @return 成功数
     */
    @Transactional(readOnly = false)
    int executeUpdateSql(String sql);

    EntityManager getEntityManager();

}
