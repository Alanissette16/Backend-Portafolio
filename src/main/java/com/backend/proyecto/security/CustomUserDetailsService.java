package com.backend.proyecto.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.proyecto.Usuarios.entities.UsuarioEntity;
import com.backend.proyecto.Usuarios.repositories.UsuarioRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;

//Servicio para cargar detalles de usuario para Spring Security
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository UsuarioRepository;

    //Constructor para inyección de dependencias
    public CustomUserDetailsService(UsuarioRepository UsuarioRepository) {
        this.UsuarioRepository = UsuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Buscar usuario por email en la base de datos
        UsuarioEntity user = UsuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        //Retornar un UserPrincipal (que incluye el ID del usuario)
        return UserPrincipal.create(user);
    }

    //Método auxiliar para obtener la entidad de usuario completa
    public UsuarioEntity getUsuarioEntityByEmail(String email) {
        return UsuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }
}
