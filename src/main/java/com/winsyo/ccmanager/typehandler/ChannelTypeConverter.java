package com.winsyo.ccmanager.typehandler;

import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class ChannelTypeConverter implements AttributeConverter<ChannelType, Integer> {

  @Override
  public Integer convertToDatabaseColumn(ChannelType attribute) {
    return attribute.index();
  }

  @Override
  public ChannelType convertToEntityAttribute(Integer dbData) {
    if (dbData == null){
      return null;
    }
    return ChannelType.indexOf(dbData);
  }
}
