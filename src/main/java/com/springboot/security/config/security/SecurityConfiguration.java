package com.springboot.security.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

	private final JwtTokenProvider jwtTokenProvider;
	
	
    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity.httpBasic(AbstractHttpConfigurer::disable)

        		.csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT Token 인증방식으로 세션은 필요 없으므로 비활성화

                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests  // 리퀘스트에 대한 사용권한 체크
                        .requestMatchers("/sign-api/sign-in", "/sign-api/sign-up", "/sign-api/exception").permitAll() // 가입,로그인 등 허용
                        .requestMatchers(HttpMethod.GET, "/product/**").permitAll() // product로 시작하는 Get 요청은 허용
                        .requestMatchers("**exception**").permitAll()
                        .anyRequest().hasRole("ADMIN")) // 나머지 요청은 인증된 ADMIN만 접근 가능
                .exceptionHandling(exceptionHandling -> exceptionHandling
                		.accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))


                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),  // JWT Token 필터를 id/password 인증 필터 이전에 추가
                        UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }	
   // Swagger 페이지 접근에 대한 예외 처리  @param webSecurity
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/v3/api-docs", "/index.html", "/swagger-ui/**"
                        , "/sign-api/sign-in", "/sign-api/sign-up", "/sign-api/exception");
    }						

}
