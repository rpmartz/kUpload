package com.ryanpmartz.kupload.auth;

import com.ryanpmartz.kupload.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class KUploadAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final KUploadAuthenticationManager KUploadAuthenticationManager;

    @Autowired
    public KUploadAuthenticationSuccessHandler(KUploadAuthenticationManager authenticationManager) {
        super();
        this.KUploadAuthenticationManager = authenticationManager;
    }

    /**
     * Handles successful authentication attempt. Resets the failed number of login attempts for the user logging in
     * back to 0.
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            User user = ((User) principal);
            KUploadAuthenticationManager.processSuccessfulAuthentication(user);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
