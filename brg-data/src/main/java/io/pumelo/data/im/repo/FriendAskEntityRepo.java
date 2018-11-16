package io.pumelo.data.im.repo;

import io.pumelo.data.im.entity.FriendAskEntity;
import io.pumelo.db.repo.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendAskEntityRepo extends BaseDao<FriendAskEntity, String> {

    @Query(value = "select a from FriendAskEntity a where a.friendAskId = :friendAskId and a.isTrash = false and a.isProcess=false ")
    FriendAskEntity findByFriendAskId(@Param("friendAskId") String friendAskId);

    @Query(value = "select a from FriendAskEntity a where a.targetUid = :uid and a.isTrash = false and a.isProcess = false ")
    List<FriendAskEntity> findListByTargetUid(@Param("uid") String uid);
}
