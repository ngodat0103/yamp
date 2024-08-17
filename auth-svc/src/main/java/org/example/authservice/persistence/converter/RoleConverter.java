package org.example.authservice.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;



@Converter
public class RoleConverter implements AttributeConverter<Set<String>, String> {

    @Override
    public String convertToDatabaseColumn(Set<String> roles) {
        StringBuilder sb = new StringBuilder();
        roles.forEach(role->sb.append(role).append(", "));
        return sb.toString();
    }
    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        Set<String> roles = new HashSet<>();
        if (dbData != null && !dbData.isEmpty()) {
            roles.addAll(Arrays.asList(dbData.split(", ")));
        }
        return roles;
    }
}
