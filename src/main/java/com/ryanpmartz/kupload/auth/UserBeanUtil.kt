package com.ryanpmartz.kupload.auth

import com.ryanpmartz.kupload.domain.User
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

/**
 * Utility class for interacting with the security context.
 */
object UserBeanUtil {

    /**
     * Convenience method to retrieve the current logged in user.

     * @return a nullable `Optional` with the currently logged in `User` if present
     * * and an empty `Optional` otherwise.
     */
    fun userFromSecurityContext(): Optional<User> {
        val ctx = SecurityContextHolder.getContext()
        val authentication = ctx.authentication

        if (authentication != null) {
            val principal = authentication.principal

            if (principal is User) {
                return Optional.of(principal)
            }
        }

        return Optional.empty<User>()
    }

}
