package com.backend.proyecto.Usuarios.entities;

import com.backend.proyecto.Usuarios.entities.UsuarioEntity.RolUsuario;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RolUsuarioConverter implements AttributeConverter<RolUsuario, String> {

    @Override
    public String convertToDatabaseColumn(RolUsuario attribute) {
        //Si el rol es nulo, se guarda como null en la BD
        if (attribute == null) {
            return null;
        }
        //Guardamos el nombre del enum (ej: ADMIN, PROGRAMMER, EXTERNAL)
        return attribute.name();
    }

    @Override
    public RolUsuario convertToEntityAttribute(String dbData) {
        //Si la BD trae null, usamos un valor por defecto
        if (dbData == null) {
            return RolUsuario.EXTERNAL;
        }
        try {
            //Quitamos espacios y convertimos a mayúsculas para evitar errores por formato
            return RolUsuario.valueOf(dbData.trim().toUpperCase());
        } catch (Exception e) {
            //Si el valor no coincide con ningún enum, usamos un valor por defecto
            return RolUsuario.EXTERNAL;
        }
    }
}
