package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.config.JwtTokenUtil;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.exception.UserNotFoundException;
import com.winsyo.ccmanager.repository.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private UserRepository userRepository;
  private AuthenticationManager authenticationManager;
  private UserDetailsService userDetailsService;
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager,
      @Qualifier("jwtUserDetailsServiceImpl") UserDetailsService userDetailsService,
      JwtTokenUtil jwtTokenUtil) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  public String login(String username, String password) {
    UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
    Authentication authentication = authenticationManager.authenticate(upToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return jwtTokenUtil.generateToken(userDetails);
  }

  @Transactional
  public void setPassword(String username, String password) {
    User user = userRepository.findByLoginName(username).orElseThrow(() -> {
      return new UserNotFoundException(username);
    });
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(password));
    userRepository.save(user);
  }

  public String refreshToken(String oldToken) throws Exception {
    String token = oldToken.substring("Bearer ".length());
    if (!jwtTokenUtil.isTokenExpired(token)) {
      return jwtTokenUtil.refreshToken(token);
    } else {
      throw new Exception();
    }
  }

}
