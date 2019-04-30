package fr.sparkit.crm.auditing;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateAttributeConvertor implements AttributeConverter<LocalDate, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(LocalDate locDate) {
        if (locDate == null) {
            return null;
        } else {
            return Timestamp.valueOf(locDate.atStartOfDay());
        }

    }

    @Override
    public LocalDate convertToEntityAttribute(Timestamp sqlTimestamp) {
        if (sqlTimestamp == null) {
            return null;
        } else {
            return sqlTimestamp.toLocalDateTime().toLocalDate();
        }
    }
}
