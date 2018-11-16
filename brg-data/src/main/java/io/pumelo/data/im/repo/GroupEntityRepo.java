package io.pumelo.data.im.repo;

import io.pumelo.data.im.entity.GroupEntity;
import io.pumelo.db.repo.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupEntityRepo extends BaseDao<GroupEntity, String> {

    /**
     * 查找创建人的群组列表
     * @param uid
     * @return
     */
    @Query(value = "select g from GroupEntity g where g.uid = :uid and g.isTrash=false ")
    List<GroupEntity> findListByUid(@Param("uid") String uid);

    /**
     * 查找创建人的单个群组
     * @param uid
     * @param groupId
     * @return
     */
    @Query(value = "select g from GroupEntity g where g.uid = :uid and g.groupId = :groupId  and g.isTrash=false ")
    GroupEntity findByUid(@Param("uid") String uid,@Param("groupId") String groupId);

    /**
     * 直接查单个群组
     * @param groupId
     * @return
     */
    @Query("select g from GroupEntity g where g.groupId = :groupId and g.isTrash = false ")
    GroupEntity findByGroupId(@Param("groupId") String groupId);
}
