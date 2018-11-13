package io.pumelo.data.backend.repo;

import io.pumelo.data.backend.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Fyb
 * on 2017/9/22.
 */

public interface RolePermissionEntityRepo extends JpaRepository<RolePermissionEntity, String> {
    @Query("select rp from RolePermissionEntity rp where rp.roleId = :roleId and rp.isTrash = false")
    List<RolePermissionEntity> findByRoleId(@Param("roleId") String roleId);

    @Query("select rp.permissionKey from RolePermissionEntity rp where rp.roleId = :roleId and rp.isTrash = false")
    List<String> findPermissionKeysByRoleId(@Param("roleId") String roleId);

    @Query("select rp from RolePermissionEntity rp " +
            "where rp.roleId in (:roleIds) and rp.isTrash = false")
    List<RolePermissionEntity> findByRoleIds(@Param("roleIds") List<String> roleIds);

    int deleteByRoleId(String roleId);
}
