package com.mundoasorrir.mundoasorrirbackend.Services;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.mundoasorrir.mundoasorrirbackend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService{
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(systemUser);
    }

    public SystemUser findUserByUsername(String username) throws UsernameNotFoundException {

        return   userRepository.findByUsername(username).get();
    }
    public List<SystemUser> findAll()  {

        return   userRepository.findAll();
    }
}
