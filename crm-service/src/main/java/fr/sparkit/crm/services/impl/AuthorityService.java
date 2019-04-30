package fr.sparkit.crm.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import fr.sparkit.crm.dao.AuthorityDao;
import fr.sparkit.crm.entities.Authority;
import fr.sparkit.crm.services.IAuthorityService;

@Service
public class AuthorityService extends GenericService<Authority, Long> implements IAuthorityService {

    private AuthorityDao authorityDao;

    @Autowired
    public AuthorityService(AuthorityDao authorityDao) {
        super();
        this.authorityDao = authorityDao;
    }

    @Override
    @Cacheable(value = "AuthorityCache", key = "#id", unless = "#result==null")
    public Authority findById(Long id) {

        return authorityDao.findOne(id);
    }

    @CacheEvict(value = "AuthorityCache", key = "#id")
    @Override
    public void delete(Long id) {
        authorityDao.delete(id);
    }

    @Override
    @CachePut(value = "AuthorityCache", key = "#authority.id")
    public Authority editAuthority(Authority authority, Long id) {
        authority.setId(id);
        return authorityDao.saveAndFlush(authority);
    }

    @Cacheable(value = "AuthorityCache", key = "#roleName", unless = "#result==null")
    @Override
    public Authority findByRoleName(String roleName) {
        return authorityDao.findByRoleNameAndIsDeletedFalse(roleName);
    }

    @Override
    public List<Authority> findByPermissionsPermissionName(String name) {
        return authorityDao.findByPermissionsPermissionNameAndIsDeletedFalse(name);
    }

}
