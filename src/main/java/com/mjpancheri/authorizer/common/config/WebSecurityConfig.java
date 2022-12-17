package com.mjpancheri.authorizer.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf().disable()
        .authorizeRequests()
        .antMatchers("/actuator/**").permitAll()
        .antMatchers("/migration/**").permitAll()
        .antMatchers(HttpMethod.POST, "/cartoes").permitAll()
        .antMatchers(HttpMethod.GET, "/cartoes/*").permitAll()
        .antMatchers(HttpMethod.POST, "/transacoes/*").permitAll();
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
        .antMatchers("/**.html", "/v3/api-docs", "/webjars/**", "/configuration/**",
            "/swagger-resources/**");
  }
}
