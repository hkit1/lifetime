package com.hkit.lifetime.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    protected SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requsts -> requsts
                        //권한 없이 들어갈 수 있는 페이지
                        .requestMatchers("/", "/api/account/login", "/api/account/register").permitAll()
                        //이외의 페이지는 모두 권한 필요
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        //로그인 페이지 커스텀 시 아래에 등록
                        //.loginPage()

                        //로그인 URL 매핑
                        .loginProcessingUrl("/api/account/login")

                        //로그인 정보 파라미터
                        .usernameParameter("id")
                        .passwordParameter("pw")

                        //로그인 성공시 URL 후에 핸들러 작성시 아래의 실패 핸들러 처럼 등록
                        .successForwardUrl("/")

                        //로그인 실패 핸들러 등록
                        .failureHandler(new CustomAuthenticationFailHandler())
                )
                //로그아웃 기능은 Security가 제공하는 default 기능 사용
                .logout(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .csrf(Customizer.withDefaults());

        return http.build();
    }

    //Spring security 의 권장 Password Encoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
