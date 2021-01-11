package com.benjie.librarymanagement.rest;

/*
 * Created by OPARA BENJAMIN
 * On 1/10/2021 - 3:18 PM
 */

import com.benjie.librarymanagement.entity.LibraryAdmin;
import com.benjie.librarymanagement.entity.LibraryMember;
import com.benjie.librarymanagement.service.MemberQueryService;
import com.benjie.librarymanagement.service.SecurityUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;

@Admin
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AdminFilter implements ContainerRequestFilter {

    @Inject
    private SecurityUtil securityUtil;
    @Inject
    private MemberQueryService memberQueryService;
    @Inject
    private EntityManager entityManager;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        String token = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION)
                .substring(SecurityUtil.BEARER.length()).trim();

        try {
            Key key = securityUtil.getSecurityKey();
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            String email = claimsJws.getBody().getSubject();

            System.out.println("**************************************************");
            System.out.println("***********Email: " + email + "******************************");
            System.out.println("**************************************************\n");
//            LibraryMember member = memberQueryService.findMemberByEmail(email);
            LibraryAdmin user = entityManager.createQuery("select l from LibraryAdmin l where l.email = :email", LibraryAdmin.class)
                    .setParameter("email", email)
                    .getResultList()
                    .get(0);
            System.out.println(user.isAdmin());
            if (user == null || !user.isAdmin()) throw new Exception();

        } catch (Exception e) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
