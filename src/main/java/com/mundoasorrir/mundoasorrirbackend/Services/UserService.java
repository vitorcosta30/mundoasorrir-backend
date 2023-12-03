package com.mundoasorrir.mundoasorrirbackend.Services;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserDTO;
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
public class UserService implements UserDetailsService{
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
    public SystemUser save(SystemUser user){
        return this.userRepository.save(user);
    }

    public Boolean existsByUsername(String username){
        return this.userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email){
        return this.userRepository.existsByEmail(email);
    }


    public Boolean isUserActiveEmail(String email){
        return this.userRepository.isUserActivateEmail(email);

    }
    public Boolean existsByEmail(Long id,UserDTO user){
        if(!existsByEmail(user.getEmail())){
            return false;
        }else{
            SystemUser userWithEmail = this.userRepository.getReferenceById(id);
            return !userWithEmail.getEmail().equalsIgnoreCase(user.getEmail());
        }
    }

    public Boolean existsByUsername(Long id,UserDTO user){
        if(!existsByUsername(user.getUsername())){
            return false;
        }else{
            SystemUser userWithEmail = this.userRepository.getReferenceById(id);
            return !userWithEmail.getUsername().equalsIgnoreCase(user.getUsername());
        }
    }

    public SystemUser updateUser(Long id, UserDTO updatedData){

        SystemUser user = this.userRepository.getReferenceById(id);
        user.setEmail(updatedData.getEmail());
        user.setUsername(updatedData.getUsername());
        return this.save(user);

    }

    public Boolean isUserActiveUsername(String username){
        return this.userRepository.isUserActivateUsername(username);
    }

    public SystemUser deactivateUser(String username){
        SystemUser user = findUserByUsername(username);
        user.deactivate();
        return save(user);
    }
    public SystemUser activateUser(String username){
        SystemUser user = findUserByUsername(username);
        user.activate();
        return save(user);
    }
}

