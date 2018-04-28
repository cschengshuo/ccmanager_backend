package com.winsyo.ccmanager.typehandler;

import com.winsyo.ccmanager.domain.enumerate.SettlementStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class SettlementStatusConverter implements AttributeConverter<SettlementStatus, Integer> {

  @Override
  public Integer convertToDatabaseColumn(SettlementStatus attribute) {
    return attribute.index();
  }

  @Override
  public SettlementStatus convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      return null;
    }
    return SettlementStatus.indexOf(dbData);
  }
}
