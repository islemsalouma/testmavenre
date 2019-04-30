package fr.sparkit.crm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mobile.device.Device;
import org.springframework.transaction.annotation.Transactional;

import fr.sparkit.crm.entities.User;

@Transactional(rollbackFor = Exception.class)
public interface IUserService extends IGenericService<User, Long> {

    User findById(Long id);

    User findByIdAndIsAdminFalse(Long id);

    List<User> findByLastName(String lastname);

    User findByEmail(String email);

    User findByUsername(String username);

    String resetPassword(String email, Device device);

    User findByResetPasswordToken(String token);

    User editUser(User user);

    Page<User> findByFirstNameOrLastName(String firstName, String lastName, Pageable pageRequest);

    List<User> saveUserfromCsv(List<User> users);

    List<User> findAllManagers(Long id);

    List<User> findAllManagers();

    List<User> findAllByEnabled(boolean enabled);

    List<User> findByManagerId(Long managerId);

    void findColaborateurChildren(Long managerId, List<User> listAllColaborateurs);

    void updateUserValidation(Long userId, boolean enabled, String dateDesactivation);

    User findByIsAdminFalseAndManagerIdIsNull();

    List<User> findCollaboratorList(Long userId);

    List<User> findByAuthoritiesByPermission(String roleName);

    User findByIdIfAdmin(Long id);

}
