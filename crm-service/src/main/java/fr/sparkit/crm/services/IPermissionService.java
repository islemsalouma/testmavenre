package fr.sparkit.crm.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import fr.sparkit.crm.entities.Permission;

@Transactional
public interface IPermissionService extends IGenericService<Permission, Long> {
    List<Permission> findAllowedWidgetPermission(Long userId);
}
