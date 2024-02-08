package com.example.demo_mall.sample.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 스프링 시큐리티 6.0 이상부터는 @Bean으로 사용
     * Void함수는 사용할 수 없고, AuthenticationManagerBuilder를 리턴하도록 변경
     *
     * @param auth
     * @return
     * @throws Exception
     */
    @Primary
    @Bean
    public AuthenticationManagerBuilder configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder().encode("1111")).roles("USER")
                .and().withUser("sys").password(passwordEncoder().encode("1111")).roles("SYS", "USER")
                .and().withUser("admin").password(passwordEncoder().encode("1111")).roles("ADMIN", "SYS", "USER");
        return auth;
    }

    @Bean
    public HttpSessionCsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-CSRF-TOKEN");
        return repository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(ar -> ar
//                        .requestMatchers("/login").permitAll() // 로그인 페이지는 모두 접근이 가능하도록 해야함
//                        .requestMatchers("/user").hasRole("USER")
//                        .requestMatchers("/admin/pay").hasRole("ADMIN")  // 더 구체적인 범위가 위에 나와야합니다.
//                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "SYS") // 6.0 이하 버전에서는 antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
                        .anyRequest().permitAll()
                )
                .csrf(c -> c
                        .csrfTokenRepository(csrfTokenRepository()))
                .formLogin(f -> f
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                RequestCache requestCache = new HttpSessionRequestCache();
                                SavedRequest savedRequest = requestCache.getRequest(request, response);
                                String redirectUrl = savedRequest.getRedirectUrl();
                                response.sendRedirect(redirectUrl);
                            }
                        })
                )
//                .exceptionHandling(xh -> xh
//                        .authenticationEntryPoint(new AuthenticationEntryPoint() { // 인증이 안된거면 이쪽을 타게됨
//                            @Override
//                            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//                                response.sendRedirect("/login");
//                            }
//                        })
//                        .accessDeniedHandler(new AccessDeniedHandler() { // 인가가 안된거면 이쪽을 타게됨
//                            @Override
//                            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                                response.sendRedirect("/denied");
//                            }
//                        })
//                )
                .build();
    }

}
