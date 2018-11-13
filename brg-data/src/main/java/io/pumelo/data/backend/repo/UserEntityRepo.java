package io.pumelo.data.backend.repo;

import io.pumelo.data.backend.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserEntityRepo extends JpaRepository<UserEntity, String> {
    UserEntity findByUsernameAndIsTrashFalse(String username);
    UserEntity findByUserIdAndIsTrashFalse(String userId);

    @Query("select user from UserEntity user where " +
            "not(user.userId = :uid) and user.isTrash = false")
    Page<UserEntity> findByPage(@Param(value = "uid") String uid, Pageable pageable);

}
