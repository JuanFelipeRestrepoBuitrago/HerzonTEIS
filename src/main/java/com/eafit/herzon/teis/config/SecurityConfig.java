package com.eafit.herzon.teis.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Configures security settings and beans for the application, including
 * authentication
 * and authorization mechanisms.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private UserDetailsService userDetailsService;

  /**
   * Provides a BCryptPasswordEncoder for secure password hashing.
   *
   * @return a new instance of BCryptPasswordEncoder
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures the global authentication manager to use the custom
   * UserDetailsService and
   * password encoder.
   *
   * @param auth the AuthenticationManagerBuilder to configure
   * @throws Exception if an error occurs during configuration
   */
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  /**
   * Defines the role hierarchy where ADMIN has higher privileges than USER.
   *
   * @return a RoleHierarchy instance with ADMIN > USER
   */
  @Bean
  public RoleHierarchy roleHierarchy() {
    return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER");
  }

  /**
   * Configures HTTP security settings, including CSRF protection, authorization
   * rules,
   * and login/logout behavior with custom success and failure handling.
   *
   * @param http the HttpSecurity object to configure
   * @return the configured SecurityFilterChain
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // Define request matcher for endpoints where CSRF is disabled
    RequestMatcher csrfDisabledMatcher = new AntPathRequestMatcher("/api/auctions/**");

    http.csrf(csrf -> csrf
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
        .ignoringRequestMatchers(csrfDisabledMatcher))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
            .requestMatchers("/jewels", "/jewels/**").permitAll()
            .requestMatchers("/auction", "/auction/**").permitAll()
            .requestMatchers("/auctions", "/auctions/**").permitAll()
            .requestMatchers("/ws/**", "/ws/auction/websocket/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/", "/home", "/regis  ter", "/api/users/register",
                "/api/users/login", "/login", "/error")
            .permitAll()
            .requestMatchers("/api/auctions", "/api/auctions/**").permitAll() 
            .anyRequest().authenticated())
        .formLogin(form -> form
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .successHandler(authenticationSuccessHandler())
            .failureHandler(authenticationFailureHandler())
            .permitAll())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout=true")
            .invalidateHttpSession(true) // Force session invalidation
            .deleteCookies("JSESSIONID", "XSRF-TOKEN") // Clear auth cookies
            .clearAuthentication(true) // Explicit auth clearance
            .permitAll());

    return http.build();
  }

  /**
   * Provides an AuthenticationManager for authenticating users.
   *
   * @param authConfig the AuthenticationConfiguration instance
   * @return the AuthenticationManager configured for the application
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  /**
   * Provides a custom AuthenticationSuccessHandler to redirect users to /home
   * after
   * successful login.
   *
   * @return an instance of CustomAuthenticationSuccessHandler
   */
  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new CustomAuthenticationSuccessHandler();
  }

  /**
   * Provides a custom AuthenticationFailureHandler to handle login failures with
   * specific
   * error messages.
   *
   * @return an instance of CustomAuthenticationFailureHandler
   */
  @Bean
  public AuthenticationFailureHandler authenticationFailureHandler() {
    return new CustomAuthenticationFailureHandler();
  }

  /**
   * Custom AuthenticationSuccessHandler to redirect users to /home after
   * successful
   * authentication.
   */
  static class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
      response.sendRedirect("/home");
    }
  }

  /**
   * Custom AuthenticationFailureHandler to handle login failures by setting
   * specific error
   * messages and redirecting to the login page.
   */
  static class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        org.springframework.security.core.AuthenticationException exception)
        throws IOException, ServletException {
      String errorMessage;
      if (exception.getMessage().contains("UserDetailsService returned null")
          || exception.getCause() instanceof UsernameNotFoundException) {
        errorMessage = "Usuario no encontrado";
      } else if (exception.getMessage().contains("Bad credentials")) {
        errorMessage = "Usuario o contraseña incorrectos";
      } else {
        errorMessage = "Error al iniciar sesión, intente de nuevo más tarde";
      }

      request.getSession().setAttribute("error", errorMessage);
      setDefaultFailureUrl("/login?error=true");
      super.onAuthenticationFailure(request, response, exception);
    }
  }
}