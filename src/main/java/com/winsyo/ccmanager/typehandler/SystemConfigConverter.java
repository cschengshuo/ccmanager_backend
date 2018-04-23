package com.winsyo.ccmanager.typehandler;

import com.winsyo.ccmanager.domain.enumerate.PayWayTag;
import com.winsyo.ccmanager.domain.enumerate.SystemConfigType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class SystemConfigConverter implements AttributeConverter<SystemConfigType, Integer> {

  @Override
  public Integer convertToDatabaseColumn(SystemConfigType attribute) {
    return attribute.index();
  }

  @Override
  public SystemConfigType convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      return null;
    }
    return SystemConfigType.indexOf(dbData);
  }
}
