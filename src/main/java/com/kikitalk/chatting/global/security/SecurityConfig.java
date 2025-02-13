package com.kikitalk.chatting.global.security;

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

@RequiredArgsConstructor
@Configuration @EnableWebSecurity
public class SecurityConfig {
    @Autowired private final JwtProvider jwtProvider;
    @Autowired private final CustomOAuth2UserService oauth2UserService;
    @Autowired private final OAuth2SuccessHandler oauth2SuccessHandler;
    @Autowired private final OAuth2FailureHandler oauth2FailureHandler;

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
                                            .requestMatchers("/api/v1/token").permitAll()
                                            .anyRequest().authenticated())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring()
                .requestMatchers("/favicon.ico");
    }
}
