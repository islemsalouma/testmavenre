package fr.sparkit.crm.entities;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Note implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private User user;

}
