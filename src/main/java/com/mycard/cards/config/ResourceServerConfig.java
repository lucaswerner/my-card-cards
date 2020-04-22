package com.mycard.cards.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/cards/{bin}/{number}/{userId}").hasAnyRole("ADMIN", "SYSTEM")
                .antMatchers(HttpMethod.POST).hasAuthority("WRITE_CARD")
                .antMatchers(HttpMethod.GET).hasAuthority("READ_CARD")
                .antMatchers(HttpMethod.PUT).hasAuthority("UPDATE_CARD");
    }

}
