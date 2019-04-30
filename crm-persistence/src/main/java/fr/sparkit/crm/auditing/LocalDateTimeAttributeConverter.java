package fr.sparkit.crm.auditing;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
        if (locDateTime == null) {
            return null;
        } else {
            return Timestamp.valueOf(locDateTime);
        }

    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
        if (sqlTimestamp == null) {
            return null;
        } else {
            return sqlTimestamp.toLocalDateTime();
        }
    }
}
