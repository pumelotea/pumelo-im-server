package io.pumelo.data.im.repo;

import io.pumelo.data.im.entity.FriendEntity;
import io.pumelo.db.repo.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendEntityRepo  extends BaseDao<FriendEntity, String> {
    /**
     * 查找单个好友
     * @param uid
     * @param friendUid
     * @return
     */
    @Query(value = "select a from FriendEntity a where a.uid = :uid and a.friendUid = :friendUid and a.isTrash = false")
    FriendEntity findByUidAndFriendUid(@Param("uid") String uid,@Param("friendUid") String friendUid);

    @Query(value = "select a from FriendEntity a where a.uid = :uid and a.isTrash = false")
    List<FriendEntity> findListByUidAndFriendUid(@Param("uid") String uid);
}
