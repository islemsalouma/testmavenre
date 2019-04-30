package fr.sparkit.crm.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import fr.sparkit.crm.dao.UserDao;
import fr.sparkit.crm.entities.Authority;
import fr.sparkit.crm.entities.User;
import fr.sparkit.crm.services.IAuthorityService;
import fr.sparkit.crm.services.IUserService;

@Service
public class UserService extends GenericService<User, Long> implements IUserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;

    private UserDetailsService userDetailsService;

    private JwtTokenUtil jwtTokenUtil;

    private IAuthorityService authorityService;

    @Autowired
    public UserService(UserDao userDao, UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil,
            IAuthorityService authorityService) {
        super();
        this.userDao = userDao;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authorityService = authorityService;
    }

    @Override
    @Cacheable(value = "UserCache", key = "#id", unless = "#result==null")
    public User findById(Long id) {
        return userDao.findByUser(id);
    }

    @Override
    @Cacheable(value = "UserCache", key = "#id", unless = "#result==null")
    public User findByIdAndIsAdminFalse(Long id) {
        return userDao.findByIdAndIsAdminFalseAndIsDeletedFalse(id);
    }

    @Override
    @Cacheable(value = "UserCache", key = "#id", unless = "#result==null")
    public User findByIdIfAdmin(Long id) {
        return userDao.findByIdAndIsAdminTrueAndIsDeletedFalse(id);
    }

    @Override
    public List<User> findByLastName(String lastname) {
        return userDao.findByLastNameAndIsAdminFalseAndIsDeletedFalse(lastname);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmailAndIsAdminFalseAndIsDeletedFalse(email);
    }

    @Override
    public User findByResetPasswordToken(String token) {

        return userDao.findByResetPasswordToken(token);
    }

    @Override
    public String resetPassword(String email, Device device) {

        User user = userDao.findByEmailAndIsAdminFalseAndIsDeletedFalse(email);
        if (user != null) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails, device);
            user.setResetPasswordToken(token);
            userDao.saveAndFlush(user);
            return token;
        } else {
            return null;
        }

    }

    @Override
    public User findByUsername(String username) {

        return userDao.findByUsernameAndIsDeletedFalse(username);
    }

    @CacheEvict(value = "UserCache", key = "#id")
    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public Page<User> findByFirstNameOrLastName(String firstName, String lastName, Pageable pageRequest) {
        return userDao.findByFirstNameOrLastNameAndIsAdminFalseAndIsDeletedFalse(firstName, lastName, pageRequest);
    }

    @Override

    @CachePut(value = "UserCache", key = "#user.id")
    public User editUser(User user) {
        return userDao.saveAndFlush(user);
    }

    @Override
    public List<User> saveUserfromCsv(List<User> users) {
        users.forEach((User user) -> {
            User updatedUser = userDao.findByUsernameAndIsDeletedFalse(user.getUsername());
            if (updatedUser != null) {
                updatedUser = userDao.findOne(updatedUser.getId());
                userDao.save(updatedUser);
            } else {
                userDao.save(user);
            }
        });
        return users;
    }

    @Override
    public List<User> findAllManagers(Long managerId) {

        List<Authority> listAuthorities = authorityService.findByPermissionsPermissionName("view_EMPLOYEE_CRA");
        List<User> users = userDao
                .findDistinctByAuthoritiesInAndEnabledAndIsAdminFalseAndIsDeletedFalse(listAuthorities, true);
        List<User> listAllColaborateurs = new ArrayList<>();
        findColaborateurChildren(managerId, listAllColaborateurs);
        users.removeAll(listAllColaborateurs);
        return users;
    }

    @Override
    public List<User> findAllManagers() {
        List<Authority> listAuthorities = authorityService.findByPermissionsPermissionName("view_EMPLOYEE_CRA");
        return userDao.findDistinctByAuthoritiesInAndEnabledAndIsAdminFalseAndIsDeletedFalse(listAuthorities, true);
    }

    @Override
    public List<User> findAllByEnabled(boolean enabled) {
        return userDao.findByEnabledAndIsAdminFalse(enabled);

    }

    @Override
    public List<User> findByManagerId(Long managerId) {

        return userDao.findByManagerIdAndIsAdminFalseAndIsDeletedFalse(managerId);
    }

    @Override
    public void findColaborateurChildren(Long managerId, List<User> listAllColaborateurs) {
        List<User> listColaborateursChildren = findByManagerId(managerId);
        if (listColaborateursChildren == null || listColaborateursChildren.isEmpty()) {
            return;
        }

        listAllColaborateurs.addAll(listColaborateursChildren);
        for (User u : listColaborateursChildren) {
            findColaborateurChildren(u.getId(), listAllColaborateurs);
        }

    }

    @Override
    public void updateUserValidation(Long userId, boolean enabled, String dateDesactivation) {
        User user = findById(userId);
        if (user == null) {
            LOG.error("cannot find user with id {}", userId);
            return;
        }
        if (!enabled) {
            LocalDate date = null;
            if (StringUtils.isNotEmpty(dateDesactivation)) {
                date = LocalDate.parse(dateDesactivation, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            user.setDesactivationDate(date);
            if (LocalDate.now().isEqual(date) || LocalDate.now().isAfter(date)) {
                user.setEnabled(false);
            } else {
                user.setEnabled(true);

            }
        } else {
            user.setEnabled(enabled);
        }
        saveAndFlush(user);

    }

    @Override
    public User findByIsAdminFalseAndManagerIdIsNull() {
        return userDao.findByIsAdminFalseAndManagerIdIsNullAndIsDeletedFalse();
    }

    @Override
    public List<User> findCollaboratorList(Long userId) {
        return findByManagerId(userId);
    }

    @Override
    public List<User> findByAuthoritiesByPermission(String roleName) {
        return userDao.findByAuthoritiesPermissionsPermissionNameAndIsDeletedFalse(roleName);
    }

}
