package fr.sparkit.crm.dao;

import org.springframework.stereotype.Repository;

import fr.sparkit.crm.entities.Permission;

@Repository
public interface PermissionDao extends BaseRepository<Permission, Long> {

}
