package io.pumelo.data.im.repo;

import io.pumelo.data.im.entity.UserEntity;
import io.pumelo.db.repo.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserEntityRepo extends BaseDao<UserEntity, String> {

    @Query(value = "select user from UserEntity user where user.uid = :uid and user.isTrash=false")
    UserEntity findByUid(@Param("uid") String uid);
}
