package fr.sparkit.crm.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.sparkit.crm.auditing.LocalDateAttributeConvertor;
import fr.sparkit.crm.enumuration.Function;
import fr.sparkit.crm.enumuration.Language;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "user")
@Table(name = "[UserTable]")
@JsonIgnoreProperties({ "createdBy", "lastModifiedBy", "lastModifiedDate", "createdDate" })
@Getter
@Setter
public class User extends AbstractAuditEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "UserName", unique = true, nullable = false, length = 50)
    @Size(max = 50)
    private String username;

    @Column(name = "FirstName", nullable = false, length = 50)
    @Size(max = 50)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 50)
    @Size(max = 50)
    private String lastName;

    @Column(name = "Email", unique = true, nullable = false, length = 50)
    @Size(max = 50)
    private String email;

    @Column(name = "Enabled", columnDefinition = "bit default 1")
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "Language", nullable = false)
    private Language language;

    @Column(name = "ResetPasswordToken")
    private String resetPasswordToken;

    @Column(name = "FirstLogin")
    private boolean firstLogin;

    @Column(name = "[Function]")
    @Enumerated(EnumType.STRING)
    private Function function;

    @Column(name = "Birthday")
    @Convert(converter = LocalDateAttributeConvertor.class)
    private LocalDate birthday;

    @Column(name = "DesactivationDate")
    @Convert(converter = LocalDateAttributeConvertor.class)
    private LocalDate desactivationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserAuthority", joinColumns = {
            @JoinColumn(name = "UserId", referencedColumnName = "Id") }, inverseJoinColumns = {
                    @JoinColumn(name = "AuthorityId", referencedColumnName = "Id") }, uniqueConstraints = {
                            @UniqueConstraint(columnNames = { "UserId", "AuthorityId" }) })
    private List<Authority> authorities;

    @JsonIgnore
    @JoinColumn(name = "ManagerId")
    @ManyToOne
    private User manager;

    @Column(name = "FlagAdmin")
    private boolean isAdmin;

    @Column(name = "IsDeleted", columnDefinition = "bit default 0")
    private boolean isDeleted;

    @Column(name = "DeletedToken")
    private UUID deletedToken;

    public User(String username, String firstName, String lastName, String email, boolean enabled, boolean firstLogin) {
        super();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enabled = enabled;
        this.firstLogin = firstLogin;

    }

    public User() {
        super();
    }

    @Override
    public String entityIdentifier() {
        StringBuilder identityName = new StringBuilder();
        identityName.append(firstName);
        identityName.append(" ");
        identityName.append(lastName.toUpperCase(Locale.ENGLISH));
        return identityName.toString();
    }

}
