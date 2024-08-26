package com.commerceapp.commerceapp.service;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.commerceapp.commerceapp.DTO.UserDTO;
import com.commerceapp.commerceapp.entity.Role;
import com.commerceapp.commerceapp.entity.User;
import com.commerceapp.commerceapp.exception.EmailAlreadyExistsException;
import com.commerceapp.commerceapp.repository.UserRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService{

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    

    public UserService(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public UserDTO saveNewUser(User user) {
        if (userRepo.findByUserMail(user.getUserMail()).isPresent()) {
            throw new EmailAlreadyExistsException("You cannot register with this mail: " + user.getUserMail());
        }

        validateUser(user);

        User newUser = User.builder()
        .userMail(user.getUserMail())
        .userName(user.getUserName())
        .authorities(Set.of(Role.ROLE_USER))
        .userLastName(user.getUserLastName())
        .userPassword(passwordEncoder.encode(user.getUserPassword()))
        .accountNonExpired(true)
        .credentialsNonExpired(true)
        .isEnabled(true)
        .accountNonLocked(true)
        .build();

        User saveDB = userRepo.save(newUser);
        return convertToUserDTO(saveDB);
    }

    private void validateUser(User user) {
        if (user.getUserMail() == null || user.getUserMail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (!EMAIL_PATTERN.matcher(user.getUserMail()).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        

    }

    private UserDTO convertToUserDTO(User user){
        UserDTO userDTO=new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setUserLastName(user.getUserLastName());
        return userDTO;
    }

    public UserDTO getUserIdById(Long userId) {
       User user=userRepo.findById(userId).orElseThrow(()->new RuntimeException("user cannot find"));
       return convertToUserDTO(user);
    }


    public void deleteUserById(Long userId) {
        userRepo.deleteById(userId);
    }

    public UserDTO updateUserById(User user, Long userId) {
        User userDB=userRepo.findById(userId).orElseThrow(()->new RuntimeException("user cannot find"));
        userDB.setUserId(user.getUserId());
        userDB.setUserName(user.getUserName());
        userDB.setUserLastName(user.getUserLastName());
        userDB.setUserMail(user.getUserMail());
        if (user.getUserPassword() != null && !user.getUserPassword().isEmpty()) {
            userDB.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        }

        User savedUser=userRepo.save(userDB);
        return convertToUserDTO(savedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUserMail(userMail);
        return user.orElseThrow(EntityNotFoundException::new);
    }

    public Long getCurrentUserId() {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            String username = null;

            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }

            
            User user = userRepo.findByUserMail(username).orElseThrow(()->new RuntimeException("user cannot find"));

            return user.getUserId();
        }

        throw new RuntimeException("User is not authenticated");
    }

    
    
}
