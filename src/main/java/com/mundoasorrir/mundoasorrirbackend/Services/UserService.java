package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserDTO;
import com.mundoasorrir.mundoasorrirbackend.Domain.Attendance.Present;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.BaseRoles;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.Role;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Repositories.ProjectRepository;
import com.mundoasorrir.mundoasorrirbackend.Repositories.RoleRepository;
import com.mundoasorrir.mundoasorrirbackend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    UserRepository userRepository;
    @Lazy
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectService projectService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(systemUser);
    }

    public List<Present> getPresencesInMonth(String username, int month, int year){
        return this.findUserByUsername(username).getPresencesInMonth(month, year);
    }

    public List<Present> getPresencesInYear(String username, int year){
        return this.findUserByUsername(username).getPresencesInYear(year);
    }



    @Transactional
    public SystemUser create(String username,String email, String password, String role){
        return this.save(new SystemUser(username,email,encoder.encode(password)), role);
    }

    public SystemUser findUserByUsername(String username) throws UsernameNotFoundException {

        return   userRepository.findByUsername(username).get();
    }
    public List<SystemUser> findAll()  {

        return   userRepository.findAll();
    }
    @Transactional
    public SystemUser save(SystemUser user, String role){
        String strRoles = role;
        Role[] roles = BaseRoles.systemRoles();
        List<Role> rolesSaved = roleRepository.findAll();
        if(strRoles == null ){
            return null;
        }
        for(int i = 0 ; i < roles.length ; i++){
            if( strRoles.equals(roles[i].getName())){

                for(int x = 0 ; x < rolesSaved.size();x++){
                    if(rolesSaved.get(x).getName().equals(roles[i].getName())){

                        user.setRoles(rolesSaved.get(x));
                        return this.save(user);
                    }
                }
                user.setRoles(roles[i]);
                roleRepository.save(roles[i]);

                return this.save(user);
            }
        }
        return this.save(user);
    }

    public SystemUser save(SystemUser user){
        return this.userRepository.save(user);
    }
    @Transactional
    public void delete(SystemUser user){
        if(existsByUsername(user.getUsername())) {
            this.userRepository.delete(findUserByUsername(user.getUsername()));
        }
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
        if(user.getCurrentProject() == null || user.getCurrentProject().getId() != updatedData.getCurrentProject().getId()){
            user.setCurrentProject(this.projectRepository.findProjectById(updatedData.getCurrentProject().getId()));
        }
        return this.save(user, updatedData.getRole());

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

