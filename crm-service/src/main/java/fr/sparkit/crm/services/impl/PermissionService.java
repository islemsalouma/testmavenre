package fr.sparkit.crm.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.sparkit.crm.dao.PermissionDao;
import fr.sparkit.crm.entities.Permission;
import fr.sparkit.crm.services.IPermissionService;

@Service
public class PermissionService extends GenericService<Permission, Long> implements IPermissionService {

    @Autowired
    public PermissionService(PermissionDao permissionDao) {
        super();
    }

    @Override
    public List<Permission> findAllowedWidgetPermission(Long userId) {
        return null;
    }
}
