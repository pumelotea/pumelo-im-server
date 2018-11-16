package io.pumelo.data.im.repo;

import io.pumelo.data.im.entity.GroupAskJoinEntity;
import io.pumelo.db.repo.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupAskJoinEntityRepo extends BaseDao<GroupAskJoinEntity, String> {

    /**
     * 获取某个群组的申请记录
     * @param groupId
     * @return
     */
    @Query("select a from GroupAskJoinEntity a where a.groupId = :groupId and a.isTrash = false and a.isProcess =false ")
    List<GroupAskJoinEntity> findListByGroupId(@Param("groupId") String groupId);


    @Query("select a from GroupAskJoinEntity a where a.groupAskId =:groupAskId and a.isTrash = false  and a.isProcess = false ")
    GroupAskJoinEntity findByGroupAskId(@Param("groupAskId") String groupAskId);

}
