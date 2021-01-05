package com.benjie.librarymanagement.rest;

/*
 * Created by OPARA BENJAMIN
 * On 1/5/2021 - 11:40 AM
 */

import com.benjie.librarymanagement.service.SecurityUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.security.Key;
import java.security.Principal;

@Authz
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    @Inject
    SecurityUtil securityUtil;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        //Grab token from the header of the request using AUTHENTICATION constant
        //Throw an exception with a message if there's no token
        //Parse the token
        //If parsing succeeds, proceed
        //Otherwise we throw an exception with a message

        String authString = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authString == null || authString.isEmpty() || !authString.startsWith("Bearer")) {
            throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
        }

        String token = authString.substring("Bearer".length()).trim();

        try {
            Key key = securityUtil.getSecurityKey();
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);

            SecurityContext originalSecContext = containerRequestContext.getSecurityContext();
            containerRequestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return new UserPrincipal() {
                        @Override
                        public String getName() {
                            return claimsJws.getBody().getSubject();
                        }
                    };
                }

                @Override
                public boolean isUserInRole(String role) {
                    return originalSecContext.isUserInRole(role);
                }

                @Override
                public boolean isSecure() {
                    return originalSecContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return originalSecContext.getAuthenticationScheme();
                }
            });
        } catch (Exception e) {
            throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

}
