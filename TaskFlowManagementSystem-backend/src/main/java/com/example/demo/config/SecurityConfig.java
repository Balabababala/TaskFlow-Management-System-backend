package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // 開啟註解控制權限，如 @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 個人專題開發階段建議先關閉，方便 Postman 測試
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // 登入註冊 API 不檢查
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // 限制管理員
                .anyRequest().authenticated() // 其他所有請求都要登入
            )
            .httpBasic(Customizer.withDefaults()) // 開啟基礎認證（Postman 測試好用）
            .formLogin(Customizer.withDefaults()); // 開啟網頁登入表單

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 密碼加密器（不可逆雜湊）
    }
}
