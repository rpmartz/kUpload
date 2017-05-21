package com.ryanpmartz.kupload.auth;

import com.ryanpmartz.kupload.domain.User;
import com.ryanpmartz.kupload.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class KUploadAuthenticationManager {

    private final UserRepository userRepository;

    @Autowired
    public KUploadAuthenticationManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Handler method for processing successful authentication.
     * Separate from default Spring Security so transaction can be short-lived.
     * Resets the number failed login attempts if the user has any.
     *
     * @param user the user who has just successfully logged in
     */
    @Transactional
    public void processSuccessfulAuthentication(User user) {
        if (user.getFailedLoginAttempts() > 0) {
            user.setFailedLoginAttempts(0);

            // entity is detached so we need to merge it back into hibernate context
            userRepository.save(user);
        }
    }
}
