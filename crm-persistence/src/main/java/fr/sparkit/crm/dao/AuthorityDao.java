package fr.sparkit.crm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.sparkit.crm.entities.Authority;

@Repository
public interface AuthorityDao extends BaseRepository<Authority, Long> {

    Authority findByRoleNameAndIsDeletedFalse(String findByRoleName);

    List<Authority> findByPermissionsPermissionNameAndIsDeletedFalse(String name);

}
