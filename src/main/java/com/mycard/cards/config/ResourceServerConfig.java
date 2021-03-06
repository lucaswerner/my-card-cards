package com.mycard.cards.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        final String admin = "ADMIN";
        final String system = "SYSTEM";
        final String[] adminAndSystem = {admin, system};

        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/cards/bill-page").hasAnyRole(adminAndSystem)
                .antMatchers("/cards/all/**").hasAnyRole(adminAndSystem)
                .antMatchers("/card-classes/**").hasAnyRole(adminAndSystem)
                .antMatchers(HttpMethod.POST).hasAuthority("WRITE_CARD")
                .antMatchers(HttpMethod.GET).hasAuthority("READ_CARD")
                .antMatchers(HttpMethod.PUT).hasAuthority("UPDATE_CARD");
    }

}
