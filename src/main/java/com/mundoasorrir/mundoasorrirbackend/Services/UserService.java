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

    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(systemUser);
    }

    /**
     *
     * @param username
     * @param month
     * @param year
     * @return
     */

    public List<Present> getPresencesInMonth(String username, int month, int year){
        return this.findUserByUsername(username).getPresencesInMonth(month, year);
    }


    /**
     *
     * @param username
     * @param year
     * @return
     */

    public List<Present> getPresencesInYear(String username, int year){
        return this.findUserByUsername(username).getPresencesInYear(year);
    }

    /**
     *
     * @param username
     * @param email
     * @param password
     * @param role
     * @return
     */



    @Transactional
    public SystemUser create(String username,String email, String password, String role){
        return this.save(new SystemUser(username,email,encoder.encode(password)), role);
    }

    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */

    public SystemUser findUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepository.findByUsername(username).isPresent()){
            return userRepository.findByUsername(username).get();
        }else{
            return null;
        }

    }

    /**
     *
     * @return
     */
    public List<SystemUser> findAll()  {

        return   userRepository.findAll();
    }

    public Role[] getSystemRoles(){
        return BaseRoles.systemRoles();
    }

    /**
     *
     * @param user
     * @param role
     * @return
     */
    @Transactional
    public SystemUser save(SystemUser user, String role){
        String strRoles = role;
        Role[] roles = getSystemRoles();
        List<Role> rolesSaved = roleRepository.findAll();
        if(strRoles == null ){
            return null;
        }
        for (Role value : roles) {
            if (strRoles.equals(value.getName())) {
                for (Role item : rolesSaved) {
                    if (item.getName().equals(value.getName())) {
                        user.setRoles(item);
                        return this.save(user);
                    }
                }
                user.setRoles(value);
                roleRepository.save(value);

                return this.save(user);
            }
        }
        return this.save(user);
    }

    /**
     *
     * @param user
     * @return
     */

    public SystemUser save(SystemUser user){
        return this.userRepository.save(user);
    }
    @Transactional
    public void delete(SystemUser user){
        if(existsByUsername(user.getUsername())) {
            SystemUser deletingUser = this.findUserByUsername(user.getUsername());
            deletingUser.removeRelations();
            SystemUser updated = this.save(deletingUser);
            this.deletion(updated);
        }
    }

    /**
     *
     * @param user
     */
    @Transactional
    public void deletion(SystemUser user){
        this.userRepository.delete(findUserByUsername(user.getUsername()));
    }

    /**
     *
     * @param username
     * @return
     */

    public Boolean existsByUsername(String username){
        return this.userRepository.existsByUsername(username);
    }

    /**
     *
     * @param email
     * @return
     */

    public Boolean existsByEmail(String email){
        return this.userRepository.existsByEmail(email);
    }

    /**
     *
     *
     * @param email
     * @return
     */


    public Boolean isUserActiveEmail(String email){
        return this.userRepository.isUserActivateEmail(email);

    }

    /**
     *
     * @param id
     * @param user
     * @return
     */
    public Boolean existsByEmail(Long id,UserDTO user){
        if(!existsByEmail(user.getEmail())){
            return false;
        }else{
            SystemUser userWithEmail = this.userRepository.getReferenceById(id);
            return !userWithEmail.getEmail().equalsIgnoreCase(user.getEmail());
        }
    }

    /**
     *
     * @param id
     * @param user
     * @return
     */

    public Boolean existsByUsername(Long id,UserDTO user){
        if(!existsByUsername(user.getUsername())){
            return false;
        }else{
            SystemUser userWithEmail = this.userRepository.getReferenceById(id);
            return !userWithEmail.getUsername().equalsIgnoreCase(user.getUsername());
        }
    }

    /**
     *
     * @param id
     * @param updatedData
     * @return
     */

    public SystemUser updateUser(Long id, UserDTO updatedData){
        SystemUser user = this.userRepository.getReferenceById(id);
        user.setEmail(updatedData.getEmail());
        user.setUsername(updatedData.getUsername());
        if(user.getCurrentProject() == null || user.getCurrentProject().getId() != updatedData.getCurrentProject().getId()){
            user.setCurrentProject(this.projectRepository.findProjectById(updatedData.getCurrentProject().getId()));
        }
        return this.save(user, updatedData.getRole());

    }

    /**
     *
     * @param username
     * @return
     */

    public Boolean isUserActiveUsername(String username){
        return this.userRepository.isUserActivateUsername(username);
    }

    /**
     *
     * @param username user to be deactivated
     * @return result of the operation
     */

    public SystemUser deactivateUser(String username){
        SystemUser user = findUserByUsername(username);
        user.deactivate();
        return save(user);
    }

    /**
     *
     * @param username user to be activated
     * @return result of the operation
     */
    public SystemUser activateUser(String username){
        SystemUser user = findUserByUsername(username);
        user.activate();
        return save(user);
    }
}

