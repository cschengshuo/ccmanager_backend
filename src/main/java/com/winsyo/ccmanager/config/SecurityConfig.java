package com.winsyo.ccmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private UserDetailsService userDetailsService;
  private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
  private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
  private RestAccessDeniedHandler restAccessDeniedHandler;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public SecurityConfig(@Qualifier("jwtUserDetailsServiceImpl") UserDetailsService userDetailsService, JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
      EntryPointUnauthorizedHandler entryPointUnauthorizedHandler, RestAccessDeniedHandler restAccessDeniedHandler) {
    this.userDetailsService = userDetailsService;
    this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    this.entryPointUnauthorizedHandler = entryPointUnauthorizedHandler;
    this.restAccessDeniedHandler = restAccessDeniedHandler;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf().disable().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeRequests()
        .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
        .anyRequest().authenticated()
        .and().headers().cacheControl();
    httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    httpSecurity.exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler).accessDeniedHandler(restAccessDeniedHandler);
  }

  @Autowired
  public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
