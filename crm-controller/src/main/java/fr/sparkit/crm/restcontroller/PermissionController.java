
package fr.sparkit.crm.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.sparkit.crm.entities.Permission;
import fr.sparkit.crm.services.IPermissionService;

@RestController()
@CrossOrigin("*")

public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    public PermissionController() {
        super();
    }

    @RequestMapping(value = "/permissions-management/permissions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('view_PERMISSION')")
    public List<Permission> getAll() {
        return permissionService.findAll();

    }

    @RequestMapping(value = "/permissions-management/permissions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('add_PERMISSION')")
    public Permission savePermission(@RequestBody Permission permission) {

        return permissionService.saveAndFlush(permission);
    }

    @RequestMapping(value = "/permissions-management/permissions/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('view_PERMISSION')")
    public Permission findPermission(@PathVariable long id) {
        return permissionService.findOne(id).get();
    }

    @RequestMapping(value = "/permissions-management/permissions/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('delete_PERMISSION')")
    public void deletePermission(@PathVariable long id) {
        permissionService.delete(id);
    }

    @RequestMapping(value = "/permissions-management/permissions/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('edit_PERMISSION')")
    public void editPermission(@PathVariable long id, @RequestBody Permission permission) {
        permission.setId(id);
        permissionService.saveAndFlush(permission);
    }

}
