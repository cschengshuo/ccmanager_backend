package com.winsyo.ccmanager.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {


  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    // 使用FastJSON
    FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    converters.add(converter);
  }

}
