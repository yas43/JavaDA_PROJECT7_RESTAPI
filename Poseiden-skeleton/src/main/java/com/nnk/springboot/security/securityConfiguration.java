package com.nnk.springboot.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.*;
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
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.authority.mapping.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;

import org.springframework.security.web.*;

import java.util.*;
import java.util.stream.*;

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
                            registry.requestMatchers("/home","/api/logout","/api/login","/user/add","/user/delete/*","/adduser")
                                .permitAll();
                            registry.requestMatchers("/admin/*").hasRole("ADMIN");
                            registry.anyRequest().authenticated();
                        })

//                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
//                    httpSecurityOAuth2LoginConfigurer
//                            .userInfoEndpoint(userInfo -> userInfo
//                                    .userService(oAuth2UserService()))
//                            .defaultSuccessUrl("/bidList/list")
//                            .failureUrl("/login?error=true");
//                })
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
//                            .usernameParameter("email")
//                            .passwordParameter("password")
                            .loginProcessingUrl("/api/login")
                            .successHandler((request, response, authentication) ->
                            {
                                response.setStatus(HttpServletResponse.SC_OK);
                                response.getWriter().write("login successful");

                            })
                            .failureHandler((request, response, exception) -> {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.getWriter().write("login failed");
                            })
                            .defaultSuccessUrl("/app/default",true)
                            .permitAll();

                })
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

//    @Bean
//    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
//        return userRequest -> {
//            OAuth2User oauth2User = (OAuth2User) userRequest.getAdditionalParameters().get("user");
//
//            String githubUsername = oauth2User.getAttribute("login"); // GitHub username
//
//            // Fetch the user from the database by GitHub login
//            User user = userRepository.findByUsername(githubUsername)
//                    .orElseThrow(() -> new RuntimeException("User not found in the database"));



            // Convert the user's roles from the database to SimpleGrantedAuthority
//            Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
//                    .map(role -> new SimpleGrantedAuthority(role.getName()))
//                    .collect(Collectors.toSet());

//            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//            authorities.stream()
//                    .map(s -> new SimpleGrantedAuthority(user.getRole()))
//                    .collect(Collectors.toList());
//
//            // Return the authenticated OAuth2User with the roles fetched from the database
//            return new DefaultOAuth2User(authorities, oauth2User.getAttributes(), "login");
//        };
//    }


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
