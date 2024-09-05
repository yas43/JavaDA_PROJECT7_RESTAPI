package com.nnk.springboot.security;

import com.nnk.springboot.service.*;
import jakarta.servlet.http.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;

@Configuration
@EnableWebSecurity
public class securityConfiguration {
    private final CustomUserDetailsService customUserDetailsService;

    public securityConfiguration(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        registry->{
                            registry.requestMatchers("/api/logout","/api/login","/user/add","/user/delete/*","/adduser")
                                .permitAll();
                            registry.requestMatchers("/admin/*").hasRole("ADMIN");
                            registry.anyRequest().authenticated();
                        })

                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
                    httpSecurityOAuth2LoginConfigurer
                            .defaultSuccessUrl("/bidList/list")
                            .failureUrl("/login?error=true");
                })
//                .formLogin(httpSecurityFormLoginConfigurer -> {
//                    httpSecurityFormLoginConfigurer
////                            .usernameParameter("email")
////                            .passwordParameter("password")
//                            .loginProcessingUrl("/api/login")
//                            .successHandler((request, response, authentication) ->
//                            {
//                                response.setStatus(HttpServletResponse.SC_OK);
//                                response.getWriter().write("login successful");
//
//                            })
//                            .failureHandler((request, response, exception) -> {
//                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                                response.getWriter().write("login failed");
//                            })
//                            .defaultSuccessUrl("/home",true)
//                            .permitAll();
//
//                })
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer
                            .logoutUrl("/api/logout")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
                            .clearAuthentication(true)
                            .permitAll();
                })


                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider (){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;

    }
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();

    }
}
