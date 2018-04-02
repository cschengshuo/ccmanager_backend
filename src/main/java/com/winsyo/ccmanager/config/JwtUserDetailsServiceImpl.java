package com.winsyo.ccmanager.config;

import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.repository.UserRepository;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

  private UserRepository userRepository;

  @Autowired
  public JwtUserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByLoginName(username);
    if (user == null) {
      throw new UsernameNotFoundException("未找到用户" + username);
    } else {
      return new JwtUser(user.getLoginName(), user.getPassword(),
          user.getRoles().stream().map((role) -> role.getRole()).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
  }

}