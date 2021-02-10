package com.test.tracker.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationConfig config;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(
                (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(config),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(config.getUrl()).permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public JwtAuthenticationConfig jwtConfig(Environment env) {
        return JwtAuthenticationConfig.builder()
                .expiration(Integer.parseInt(Objects.requireNonNull(env.getProperty("TRACKER_JWT_EXPIRATION"))))
                .header(Objects.requireNonNull(env.getProperty("TRACKER_JWT_HEADER")))
                .prefix(Objects.requireNonNull(env.getProperty("TRACKER_JWT_PREFIX")))
                .url(Objects.requireNonNull(env.getProperty("TRACKER_JWT_LOGIN_URL")))
                .secret(Objects.requireNonNull(env.getProperty("TRACKER_JWT_SECRET")))
                .build();
    }
}

