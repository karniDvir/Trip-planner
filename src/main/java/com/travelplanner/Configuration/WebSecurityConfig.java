package com.travelplanner.Configuration;

import com.travelplanner.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for setting up web security.
 */
@Configuration
public class WebSecurityConfig {

    /**
     * Bean for the UserDetailsService, which provides user-specific data.
     *
     * @return a CustomUserDetailsService instance.
     */
    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    /**
     * Bean for the PasswordEncoder, which encrypts passwords using BCrypt.
     *
     * @return a BCryptPasswordEncoder instance.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for the DaoAuthenticationProvider, which uses the custom UserDetailsService
     * and PasswordEncoder for authentication.
     *
     * @return a DaoAuthenticationProvider instance.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Configures the HttpSecurity for the application, setting up custom login and logout pages,
     * authentication providers, and access rules for different endpoints.
     *
     * @param http the HttpSecurity to modify
     * @return a configured SecurityFilterChain instance
     * @throws Exception if an error occurs while configuring security
     */
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/users","/trip/save", "/trip/afterLogin", "/trip/myTrips").authenticated()
                                .anyRequest().permitAll()
                )
                .formLogin(login ->
                        login.loginPage("/login")  // Use custom login page
                                .usernameParameter("username")
                                .defaultSuccessUrl("/trip/afterLogin", true)
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
