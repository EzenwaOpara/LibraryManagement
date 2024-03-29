package com.benjie.librarymanagement.rest;

/*
 * Created by OPARA BENJAMIN
 * On 12/14/2020 - 10:05 PM
 */

import com.benjie.librarymanagement.entity.LibraryUser;
import com.benjie.librarymanagement.service.LibraryUserService;
import com.benjie.librarymanagement.service.SecurityUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Path("user")
public class UserRest {

    @Inject
    private SecurityUtil securityUtil;

    @Context
    private UriInfo uriInfo;

    @Inject
    private LibraryUserService libraryUserService;

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@NotNull @FormParam("email") String email,
                          @NotNull @FormParam("password") String password) {

        //Authenticate user
        //Generate token
        //Return token in response header to the client

        boolean authenticated = securityUtil.authenticateUser(email, password);
        if (!authenticated) {
            throw new SecurityException("Email or password is invalid");
        }

        String token = generateToken(email);
        return Response.ok().header(HttpHeaders.AUTHORIZATION, token).build();
    }

    @Path("create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@NotNull LibraryUser libraryUser) {
        int requestCode = libraryUserService.saveUser(libraryUser);
        if (requestCode == 1) {
            return Response.ok(libraryUser).build();
        } else if (requestCode == 0) {
            return Response.status(409,
                    "Sorry user with email: " + libraryUser.getEmail().toLowerCase() + " exits.").build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }


    private String generateToken(String email) {
        Key securityKey = securityUtil.getSecurityKey();
        return Jwts.builder().setSubject(email)
                .setIssuedAt(new Date())
                .setIssuer(uriInfo.getBaseUri().toString())
                .setAudience(uriInfo.getAbsolutePath().toString())
                .setExpiration(securityUtil.toDate(LocalDateTime.now().plusMinutes(60)))
                .signWith(SignatureAlgorithm.HS512, securityKey)
                .compact();
    }
}
