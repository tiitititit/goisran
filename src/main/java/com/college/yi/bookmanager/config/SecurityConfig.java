package com.college.yi.bookmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.college.yi.bookmanager.security.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    

    private CustomUserDetailsService userDetailsService;

	public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // 認可のルールを設定
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf() // CSRF対策は有効のままでOK（HTMLフォームなら）
                .and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/books").hasAnyRole("USER", "ADMIN") // GET用
                .requestMatchers("/api/books/**").hasRole("ADMIN") // POST/PUT/DELETEなど全部
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // デフォルトを使うなら不要
                .defaultSuccessUrl("/api/books", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }

    // パスワードエンコーダー（BCryptを使用）
    @Bean
     PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 認証マネージャー
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
  
    }

