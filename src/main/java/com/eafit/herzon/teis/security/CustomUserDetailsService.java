package com.eafit.herzon.teis.security;

import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.repositories.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom implementation of UserDetailsService to load user details for authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  /**
   * Loads user details by username for authentication purposes.
   *
   * @param username the username of the user to load
   * @return UserDetails containing the user's credentials and authorities
   * @throws UsernameNotFoundException if the username is null or the user is not found
   */
  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username == null || username.trim().isEmpty()) {
      throw new UsernameNotFoundException("Username cannot be null or empty");
    }

    CustomUser customUser = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(
            "User not found with username: " + username));

    System.out.println("🔍 Usuario encontrado: " + customUser.getUsername());
    System.out.println("🔍 Contraseña en BD: " + customUser.getPassword());
    System.out.println("🔍 Rol en BD: " + customUser.getRole());

    return new org.springframework.security.core.userdetails.User(
        customUser.getUsername(),
        customUser.getPassword(),
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + customUser.getRole().name())));
  }
}