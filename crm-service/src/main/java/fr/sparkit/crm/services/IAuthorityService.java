package fr.sparkit.crm.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import fr.sparkit.crm.entities.Authority;

@Transactional
public interface IAuthorityService extends IGenericService<Authority, Long> {

    Authority findById(Long id);

    Authority findByRoleName(String roleName);

    Authority editAuthority(Authority authority, Long id);

    List<Authority> findByPermissionsPermissionName(String name);
}
