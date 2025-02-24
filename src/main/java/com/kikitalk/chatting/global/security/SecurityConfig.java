package com.kikitalk.chatting.global.security;

import com.kikitalk.chatting.global.security.jwt.JwtAuthEntryPoint;
import com.kikitalk.chatting.global.security.jwt.JwtFilter;
import com.kikitalk.chatting.global.security.jwt.JwtProvider;
import com.kikitalk.chatting.global.security.oauth2.handler.OAuth2FailureHandler;
import com.kikitalk.chatting.global.security.oauth2.handler.OAuth2SuccessHandler;
import com.kikitalk.chatting.global.security.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Configuration @EnableWebSecurity
public class SecurityConfig {
    @Autowired private final JwtProvider jwtProvider;
    @Autowired private final CustomOAuth2UserService oauth2UserService;
    @Autowired private final OAuth2SuccessHandler oauth2SuccessHandler;
    @Autowired private final OAuth2FailureHandler oauth2FailureHandler;
    @Autowired private final JwtAuthEntryPoint jwtAuthEntryPoint;

    @Value("${auth.url.login}") private String LOGIN_URL;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)      // CSRF 보호 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP 기본 인증 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 기본 폼 로그인 비활성화

                .oauth2Login(oauth -> oauth
                                .loginPage(LOGIN_URL)
                                .userInfoEndpoint(userInfo -> userInfo.userService(oauth2UserService))
                                .successHandler(oauth2SuccessHandler)
                                .failureHandler(oauth2FailureHandler)
                )

                .authorizeHttpRequests(authorize -> authorize
                                            .requestMatchers("/api/v1/token", "/stomp-connect/**").permitAll()
                                            .anyRequest().authenticated())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint)); // jwt 토큰 예외 처리

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring()
                .requestMatchers("/favicon.ico");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("*")); //HTTP메서드
        configuration.setAllowedHeaders(List.of("*")); //헤더값
        configuration.setAllowCredentials(true); //자격증명

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); //모든 url 패턴 cors 허용
        return source;
    }
}
