package fr.sparkit.crm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Authority")
@JsonIgnoreProperties({"createdBy", "lastModifiedBy", "lastModifiedDate", "createdDate"})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Authority extends AbstractAuditEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "RoleName", unique = true, nullable = false, length = 50)
    @Size(min = 1)
    @EqualsAndHashCode.Include
    private String roleName;

    @Column(name = "RoleDescription", length = 100)
    @Size(min = 1)
    private String roleDescription;

    @JsonIgnore
    @ManyToMany(mappedBy = "authorities")
    private List<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "AuthorityPermission", joinColumns = {
            @JoinColumn(name = "AuthorityId", referencedColumnName = "Id")}, inverseJoinColumns = {
            @JoinColumn(name = "PermissionId", referencedColumnName = "Id")}, uniqueConstraints = {
            @UniqueConstraint(columnNames = {"AuthorityId", "PermissionId"})})
    private List<Permission> permissions;

    @Column(name = "IsDeleted", columnDefinition = "bit default 0")
    private boolean isDeleted;

    @Column(name = "DeletedToken")
    private UUID deletedToken;

    public Authority() {
        super();
    }

    public Authority(String roleName, String roleDescription) {
        super();
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    @Override
    public String entityIdentifier() {
        StringBuilder identityName = new StringBuilder();
        identityName.append(roleName);
        return identityName.toString();
    }

}
