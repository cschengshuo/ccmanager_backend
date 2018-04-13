package com.winsyo.ccmanager.typehandler;

import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.domain.enumerate.OK;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class OKConverter implements AttributeConverter<OK, String> {

  @Override
  public String convertToDatabaseColumn(OK attribute) {
    System.out.println("1111111111111111");
    return attribute.index();
  }

  @Override
  public OK convertToEntityAttribute(String dbData) {
    System.out.println("1111111111111111");
    if (StringUtils.isEmpty(dbData)) {

      return null;
    }
    return OK.indexOf(dbData);
  }
}
