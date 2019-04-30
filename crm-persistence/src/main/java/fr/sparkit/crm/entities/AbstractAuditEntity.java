package fr.sparkit.crm.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import fr.sparkit.crm.auditing.LocalDateTimeAttributeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractAuditEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAuditEntity.class);
    private static final String FAILED_EXCEPTION = "Exception while getting fields {}";

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @CreatedBy
    @Column(name = "CreatedBy", updatable = false, length = 50)
    private String createdBy;

    @Column(name = "CreatedDate", updatable = false)
    @CreatedDate
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createdDate;

    @Column(name = "LastModifiedBy", length = 50)
    @LastModifiedBy
    private String lastModifiedBy;

    @Column(name = "LastModifiedDate")
    @LastModifiedDate
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime lastModifiedDate;

    public AbstractAuditEntity() {
        super();
    }

    // Method to identify an entity
    public abstract String entityIdentifier();

    @Override
    public String toString() {

        return "";
    }

    public static boolean isNotObjectClass(Class<?> clazz) {
        return clazz != null && !clazz.equals(Object.class);
    }

    public static boolean isParametrizedType(Field field) {
        return field.getGenericType() instanceof ParameterizedType;
    }

    public static boolean isAbstractAuditEntity(Field field) {
        return AbstractAuditEntity.class.isAssignableFrom(getGenericTypeOfCollectionField(field));
    }

    public static Class<?> getGenericTypeOfCollectionField(Field field) {
        return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
    }

    public static boolean isCollection(Field f) {
        return Collection.class.isAssignableFrom(f.getType());
    }

    public static String generateFieldAndValueForAbstractAuditInstance(Field abstractAuditEntityTypeField,
                                                                       Class<?> currentClass, Object filedInstance) {
        StringBuilder filedsNameAndValue = new StringBuilder();
        filedsNameAndValue.append("[");
        if (filedInstance != null) {
            Field[] fields = abstractAuditEntityTypeField.getType().getDeclaredFields();
            Arrays.stream(fields)
                    .filter(Objects::nonNull)
                    .filter(field -> !Modifier.isStatic(field.getModifiers()) &&
                            !field.getType().isAssignableFrom(currentClass))
                    .forEach((Field field) -> {
                        try {
                            field.setAccessible(true);
                            filedsNameAndValue.append(field.getName());
                            filedsNameAndValue.append("=");
                            filedsNameAndValue.append(field.get(filedInstance));
                            filedsNameAndValue.append(",");
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            LOGGER.error(FAILED_EXCEPTION, e);
                        }
                    });
            if (filedsNameAndValue.lastIndexOf(",") != -1) {
                filedsNameAndValue.deleteCharAt(filedsNameAndValue.lastIndexOf(","));
            }
        }
        filedsNameAndValue.append("]");
        return filedsNameAndValue.toString();
    }
}
