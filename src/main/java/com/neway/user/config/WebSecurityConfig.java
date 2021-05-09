package com.neway.user.config;

import com.neway.user.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private CustomerUserDetailsService customerUserDetailsService;

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers( "/login",
                "/register",
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(
            new LoginFilter("/user/login", authenticationManager()),
            UsernamePasswordAuthenticationFilter.class)

        // Las demás peticiones pasarán por este filtro para validar el token
        .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
    ;
  }

  //  @Bean
  // public AuthenticationManager customAuthenticationManager() throws Exception {
  // return authenticationManager();
  // }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customerUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }
}
