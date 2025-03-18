package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.repositories.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The CustomUserDetailsService class is a service class that provides methods to
 * manage User operations.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {


  @Autowired
  private UserRepository userRepository;

  /**
   * Loads a user by their username.
   *
   * @param username the username to load
   * @return a UserDetails object representing the user
   * @throws UsernameNotFoundException if the user is not found
   */
  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    CustomUser appUser = userRepository.findByUsername(username).orElse(null);
    if (appUser == null) {
      throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }

    Set<GrantedAuthority> grantList = new HashSet<>();
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
        "ROLE_" + appUser.getRole().name());
    grantList.add(grantedAuthority);

    UserDetails user = new User(username, appUser.getPassword(), grantList);

    return user;
  }
}