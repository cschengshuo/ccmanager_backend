package com.winsyo.ccmanager.typehandler;

import com.winsyo.ccmanager.domain.enumerate.PayWayTag;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class PayWayTagConverter implements AttributeConverter<PayWayTag, String> {

  @Override
  public String convertToDatabaseColumn(PayWayTag attribute) {
    return attribute.index();
  }

  @Override
  public PayWayTag convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    return PayWayTag.indexOf(dbData);
  }
}
