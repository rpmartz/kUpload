package com.ryanpmartz.kupload.auth;

import com.ryanpmartz.kupload.domain.User;
import com.ryanpmartz.kupload.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;

@Component
public class KUploadAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final Logger log = Logger.getLogger(KUploadAuthenticationFailureHandler.class);

    private static final int NUM_ALLOWED_FAILED_LOGINS_BEFORE_LOCKOUT = 5;

    private final UserService userService;

    public KUploadAuthenticationFailureHandler(UserService userService) {
        super();
        this.userService = userService;

        this.setDefaultFailureUrl("/login-failed");
    }

    /**
     * Handles a failed login attempt by a user. Increments the number of failed login attempts for that user
     * and, if the threshold has been exceeded, locks the user's account.
     *
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    @Transactional
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.debug("Authentication failure occurred");

        String username = request.getParameter("username");

        User user = userService.findByEmail(username);
        if (user != null) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

            if (user.getFailedLoginAttempts() >= NUM_ALLOWED_FAILED_LOGINS_BEFORE_LOCKOUT) {
                log.info("User [" + user.getId() + "] has exceeded the allowed number of login attempts and is being disabled");
                user.setEnabled(false);

                response.sendRedirect("/account/locked");

                return;
            }

        } else {
            // get IP address of request and log attempt for brute force login detection
            String originalRequestorIp = request.getHeader("X-Forwarded-For");
            log.warn("Non-user login attempted by [" + originalRequestorIp + "]");
        }

        super.onAuthenticationFailure(request, response, exception);
    }
}
