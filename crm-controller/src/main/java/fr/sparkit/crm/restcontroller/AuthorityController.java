package fr.sparkit.crm.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.sparkit.crm.entities.Authority;
import fr.sparkit.crm.services.IAuthorityService;

@RestController()
@CrossOrigin("*")

public class AuthorityController {

    @Autowired
    private IAuthorityService authorityService;

    public AuthorityController() {
        super();
    }

    @RequestMapping(value = "/roles-management/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('view_ROLE')")
    public List<Authority> getAll() {
        return authorityService.findAll();

    }

    @RequestMapping(value = "/roles-management/roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('add_ROLE')")
    public Authority saveRole(@RequestBody Authority role) {

        return authorityService.saveAndFlush(role);
    }

    @Cacheable(value = "AuthorityCache", key = "#id", unless = "#result==null")
    @RequestMapping(value = "/roles-management/roles/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Authority findRole(@PathVariable long id) {
        return authorityService.findOne(id).get();
    }

    @CacheEvict(value = "AuthorityCache", key = "#id")
    @RequestMapping(value = "/roles-management/roles/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('delete_ROLE')")
    public void deleteRole(@PathVariable long id) {
        authorityService.delete(id);
    }

    @RequestMapping(value = "/roles-management/roles/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('edit_ROLE')")
    public void editRole(@PathVariable long id, @RequestBody Authority authority) {
        authorityService.editAuthority(authority, id);
    }

    @RequestMapping(value = "/roles-management/roles", method = RequestMethod.GET, params = "roleName", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('view_ROLE')")
    public Authority findRoleName(@RequestParam("roleName") String roleName) {
        return authorityService.findByRoleName(roleName);
    }
}
