
package fr.sparkit.crm.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Permission")
@Getter
@Setter
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "PermissionName", unique = true, length = 50)
    @Size(min = 1)
    private String permissionName;

    @Column(name = "PermissionDescription", length = 100)
    @Size(min = 1)
    private String permissionDescription;

    @Column(name = "IsDeleted", columnDefinition = "bit default 0")
    private boolean isDeleted;

    @Column(name = "DeletedToken")
    private UUID deletedToken;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private List<Authority> authorities;

    public Permission() {
        super();
    }

    public Permission(String permissionName, String permissionDescription) {
        super();
        this.permissionName = permissionName;
        this.permissionDescription = permissionDescription;
    }

}
