package io.pumelo.data.backend.repo;

import io.pumelo.data.backend.entity.BackendUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BackendUserEntityRepo extends JpaRepository<BackendUserEntity, String> {
    BackendUserEntity findByUsernameAndIsTrashFalse(String username);
    BackendUserEntity findByUserIdAndIsTrashFalse(String userId);

    @Query("select user from BackendUserEntity user where " +
            "not(user.userId = :uid) and user.isTrash = false")
    Page<BackendUserEntity> findByPage(@Param(value = "uid") String uid, Pageable pageable);

}
