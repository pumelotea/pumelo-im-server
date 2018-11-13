package io.pumelo.data.backend.repo;

import io.pumelo.data.backend.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Fyb
 * on 2017/9/22.
 */

public interface UserRoleEntityRepo extends JpaRepository<UserRoleEntity, String> {
    List<UserRoleEntity> findByUserIdAndIsTrashFalse(String userId);

    @Query("select ur.roleId from UserRoleEntity ur where ur.userId = :userId and ur.isTrash = false")
    List<String> findRoleIdsByUserId(@Param(value = "userId") String userId);

    @Query("select new UserRoleEntity(ur.userId,ur.roleId) from UserRoleEntity ur where ur.userId in (:userIds) and ur.isTrash = false")
    List<UserRoleEntity> findByUserIds(@Param(value = "userIds") List<String> appUserIds);

    @Query("select ur.userId from UserRoleEntity ur where ur.roleId = :roleId and ur.isTrash = false")
    List<String> findUserIdsByRoleId(@Param(value = "roleId") String roleId);
}
