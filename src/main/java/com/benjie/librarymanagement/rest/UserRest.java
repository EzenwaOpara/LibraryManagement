package com.benjie.librarymanagement.rest;

/*
 * Created by OPARA BENJAMIN
 * On 12/14/2020 - 10:05 PM
 */

import com.benjie.librarymanagement.entity.LibraryAdmin;
import com.benjie.librarymanagement.entity.LibraryUser;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("user")
public class UserRest {

    @Path("login")
    @POST
    public Response login(String email, String password) {

        //TODO: implement login
        return null;
    }

    @Path("create/{user_type}")
    @POST
    public Response createUser(@PathParam("user") LibraryUser libraryUser, @PathParam("admin") LibraryAdmin libraryAdmin) {
        //TODO: implement this
        return null;
    }
}
