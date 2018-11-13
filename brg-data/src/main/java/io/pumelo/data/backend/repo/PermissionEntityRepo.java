package io.pumelo.data.backend.repo;

import io.pumelo.common.basebean.PermissionType;
import io.pumelo.data.backend.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PermissionEntityRepo extends JpaRepository<PermissionEntity,String> {
    @Query("select p from PermissionEntity p where p.permissionType = :permissionType and p.isTrash = false order by p.sortNum")
    List<PermissionEntity> findByPermissionType(@Param(value = "permissionType") PermissionType permissionType);

    @Query("select p from PermissionEntity p where p.permissionType = :permissionType and p.parentKey = :parentKey and p.isTrash = false  order by p.sortNum")
    List<PermissionEntity> findByPermissionType(@Param(value = "permissionType") PermissionType permissionType,@Param(value = "parentKey") String parentKey);

    @Query("select p from PermissionEntity p where p.permissionType = :permissionType and p.permissionKey in (:permissionKeys) and p.isTrash = false  order by p.sortNum")
    List<PermissionEntity> findByPermissionType(@Param(value = "permissionType") PermissionType permissionType,@Param(value = "permissionKeys") List<String> permissionKeys);

    @Query("select p from PermissionEntity p where p.permissionType = :permissionType and p.parentKey = :parentKey and p.permissionKey in (:permissionKeys) and p.isTrash = false  order by p.sortNum")
    List<PermissionEntity> findByPermissionType(@Param(value = "permissionType") PermissionType permissionType,@Param(value = "parentKey") String parentKey,@Param(value = "permissionKeys") List<String> permissionKeys);

    Optional<PermissionEntity> findByPermissionKey(String permissionKey);

}
