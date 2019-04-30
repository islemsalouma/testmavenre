package fr.sparkit.crm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.sparkit.crm.entities.Authority;
import fr.sparkit.crm.entities.User;

@Repository
public interface UserDao extends BaseRepository<User, Long> {

    User findByUsernameAndIsDeletedFalse(String username);

    User findByEmailAndIsAdminFalseAndIsDeletedFalse(String email);

    User findByResetPasswordToken(String resetPasswordToken);

    List<User> findByLastNameAndIsAdminFalseAndIsDeletedFalse(String lastName);

    Page<User> findByFirstNameOrLastNameAndIsAdminFalseAndIsDeletedFalse(String firstName, String lastName,
            Pageable pageRequest);

    List<User> findDistinctByAuthoritiesInAndIsAdminFalseAndIsDeletedFalse(List<Authority> listAuthorities);

    List<User> findByEnabledAndIsAdminFalse(boolean enabled);

    List<User> findByManagerIdAndIsAdminFalseAndIsDeletedFalse(Long id);

    List<User> findDistinctByAuthoritiesInAndEnabledAndIsAdminFalseAndIsDeletedFalse(List<Authority> listAuthorities,
            boolean enabled);

    User findByIdAndIsAdminFalseAndIsDeletedFalse(Long id);

    User findByIsAdminFalseAndManagerIdIsNullAndIsDeletedFalse();

    List<User> findByAuthoritiesPermissionsPermissionNameAndIsDeletedFalse(String permissionName);

    User findByIdAndIsAdminTrueAndIsDeletedFalse(Long id);

    @Query("SELECT u FROM user u WHERE u.id =?1 and u.isDeleted = false")
    User findByUser(Long id);

    User findByEmailAndIsDeletedFalse(String email);

}
