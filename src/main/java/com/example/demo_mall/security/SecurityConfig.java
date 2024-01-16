package com.example.demo_mall.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;


    /**
     * 스프링 시큐리티 6.0 이상부터는 @Bean으로 사용
     * Void함수는 사용할 수 없고, AuthenticationManagerBuilder를 리턴하도록 변경
     * @param auth
     * @return
     * @throws Exception
     */
    @Primary
    @Bean
    public AuthenticationManagerBuilder configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");
        auth.inMemoryAuthentication().withUser("sys").password("{noop}1111").roles("SYS");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("ADMIN");
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(ar -> ar
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/members").hasRole("USER")
                        .requestMatchers("/user").hasRole("USER")
                        .requestMatchers("/admin/pay").hasRole("ADMIN")
//                        .requestMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form -> form
//                        .loginPage("/login")
                                .defaultSuccessUrl("/members")
                                .failureUrl("/login.html?error=true")
                                .usernameParameter("userid")
                                .passwordParameter("passwd")
                                .loginProcessingUrl("/login")
                                .successHandler(new AuthenticationSuccessHandler() {
                                    @Override
                                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                        System.out.println("authentication :" + authentication.getName());
                                        response.sendRedirect("/");
                                    }
                                })
                                .failureHandler(new AuthenticationFailureHandler() {
                                    @Override
                                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                        System.out.println("exception :" + exception.getMessage());
                                        response.sendRedirect("/login");
                                    }
                                })
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .addLogoutHandler(new LogoutHandler() {
                            @Override
                            public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                                HttpSession session = request.getSession();
                                session.invalidate();
                            }
                        })
                        .logoutSuccessHandler(new LogoutSuccessHandler() {
                            @Override
                            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                response.sendRedirect("/login");
                            }
                        })
                        .deleteCookies("remember-me")
                )
                .rememberMe(rm -> rm
                        .rememberMeParameter("remember") // 디폴트 : remember-me
                        .tokenValiditySeconds(3600) // 디폴트 : 14일
                        .alwaysRemember(true)
                        .userDetailsService(userDetailsService)
                )
                .sessionManagement(sm -> sm
                        .maximumSessions(1) // 동시 세션 수
                        .maxSessionsPreventsLogin(true) // true : 현재 사용자 인증실패 , false : 이전 사용자 세션 만료
                        .expiredUrl("/expired") // 세션이 만료됐을 때 이동하는 Url
                )
                .sessionManagement(sm -> sm
                        .sessionFixation()
                        .changeSessionId()
                )
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .build();
    }

} 
