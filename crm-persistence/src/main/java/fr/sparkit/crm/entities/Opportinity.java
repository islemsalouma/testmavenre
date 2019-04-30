package fr.sparkit.crm.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate(true)
@DynamicInsert(true)
@Entity
public class Opportinity implements Serializable {

    private static final long serialVersionUID = -44568761965518626L;

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private boolean isDeleted;
    private double rating;
    private LocalDateTime createdDate;
    @OneToOne
    private UserCrm createdBy;
    private LocalDateTime endDate;
    private double revenueEstime;
    private String description;
    private UUID deletedToken;

}
