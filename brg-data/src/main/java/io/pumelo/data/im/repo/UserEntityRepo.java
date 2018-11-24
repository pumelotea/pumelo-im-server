package io.pumelo.data.im.repo;

import io.pumelo.data.im.entity.UserEntity;
import io.pumelo.db.repo.BaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserEntityRepo extends BaseDao<UserEntity, String> {

    @Query(value = "select user from io.pumelo.data.im.entity.UserEntity user where user.uid = :uid and user.isTrash=false")
    UserEntity findByUid(@Param("uid") String uid);

    @Query("select user from UserEntity user where user.uid like :keyword or user.name like :keyword and user.isTrash=false")
    Page<UserEntity> findListByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
