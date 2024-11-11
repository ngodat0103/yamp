package com.github.ngodat0103.yamp.authsvc.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ToUppercaseConverter implements AttributeConverter<String, String> {
  @Override
  public String convertToDatabaseColumn(String attribute) {
    return attribute.toUpperCase();
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    return dbData;
  }
}
