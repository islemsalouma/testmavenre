package fr.sparkit.crm.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class UserCrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    public Long idUser;

    @OneToMany
    public List<Opportinity> opportinities;

}
