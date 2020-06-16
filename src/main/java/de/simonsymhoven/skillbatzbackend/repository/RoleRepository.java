package de.simonsymhoven.skillbatzbackend.repository;

import java.util.Optional;

import de.simonsymhoven.skillbatzbackend.model.Permission;
import de.simonsymhoven.skillbatzbackend.model.PermissionName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Permission, Long> {
  Optional<Permission> findByName(PermissionName permissionName);
}
