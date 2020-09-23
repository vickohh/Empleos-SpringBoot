package com.vck.empleos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	// configuracion con tablas por defecto de Spring Security
	// protected void configure(AuthenticationManagerBuilder auth) throws Exception
	// {
	// auth.jdbcAuthentication().dataSource(dataSource);
	// }

	
	//CONFIGURACION CON BASE DE DATOS MYSQL EMPLEADO POR EL PROYECTO
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username, password, estatus from Usuarios where username=?")
				.authoritiesByUsernameQuery("select u.username, p.perfil from UsuarioPerfil up "
						+ "inner join Usuarios u on u.id = up.idUsuario "
						+ "inner join Perfiles p on p.id = up.idPerfil" + " where u.username = ?");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
	
		//RECURSOS ESTATICOS NO REQUIEREN DE AUTENTICACION
		http.authorizeRequests().antMatchers(
				"/bootstrap/**",
				"/images/**",
				"/logos/**",
				"/tinymce/**",
				"/js/**",
				"/css/**").permitAll()
		//VISTAS PUBLICAS NO REQUIRES AUTENTICACION
		.antMatchers(
				"/",
				"/usuario/signup",
				"/search",
				"/vacantes/view/**").permitAll()
		
		.antMatchers("/vacantes/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
		.antMatchers("/categorias/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
		.antMatchers("/categorias/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
		.antMatchers("/usuario/**").hasAnyAuthority("ADMINISTRADOR")
		
			
		//REQUIERE AUTENTICACION TODAS LAS DEMAS URLS
		.anyRequest().authenticated()
		//FORMULARIO LOGIN NO REQUIERE AUTENTICACION		
		.and().formLogin().permitAll();						
	}
	

}
