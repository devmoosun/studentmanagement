package com.devmoosun.student_management_system.student;


import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private StudentRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Student foundUser = userRepository.findByEmail(usernameOrEmail);

        if (foundUser == null) {
            throw  new UsernameNotFoundException("Not found");
        }
        return new UserPrincipal(foundUser);

    }
}
