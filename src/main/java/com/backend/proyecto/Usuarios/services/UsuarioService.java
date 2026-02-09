package com.backend.proyecto.Usuarios.services;

import com.backend.proyecto.Usuarios.dtos.CreateUsuarioRequestDto;
import com.backend.proyecto.Usuarios.dtos.UpdateUsuarioRequestDto;
import com.backend.proyecto.Usuarios.dtos.UsuarioResponseDto;

import java.util.List;

//Interfaz para el servicio de gestión de usuarios
public interface UsuarioService {
    //Crea un nuevo usuario en el sistema
    UsuarioResponseDto crearUsuario(CreateUsuarioRequestDto dto);

    //Obtiene un usuario por su ID
    UsuarioResponseDto obtenerUsuarioPorId(Long id);

    //Lista todos los usuarios registrados
    List<UsuarioResponseDto> obtenerTodosLosUsuarios();

    //Actualiza la información de un usuario existente
    UsuarioResponseDto actualizarUsuario(Long id, UpdateUsuarioRequestDto dto);

    //Elimina un usuario del sistema por su ID
    void eliminarUsuario(Long id);

    //Obtiene solo los usuarios con rol PROGRAMMER
    List<UsuarioResponseDto> obtenerProgramadores();

    //Obtiene un usuario por su dirección de correo electrónico
    UsuarioResponseDto obtenerUsuarioPorEmail(String email);
}
