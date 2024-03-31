package com.in28minutes.rest.webservices.restfulwebservices.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        // 1. 모든 요청이 인증되도록 한다.
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );
        // 2. 자격 증명을 위해 팝업창으로 인증
        http.httpBasic(withDefaults()); // 사용자 이름과 비밀번호를 묻는 팝업창이 뜬다.

        // 3. CSRF -> POST, PUT
//        http.csrf().disable();
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }


}
