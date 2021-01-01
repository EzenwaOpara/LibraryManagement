package com.benjie.librarymanagement.rest;

/*
 * Created by OPARA BENJAMIN
 * On 12/14/2020 - 10:19 PM
 */

import com.benjie.librarymanagement.entity.Book;
import com.benjie.librarymanagement.service.BookService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookRest {

    @Inject
    private BookService bookService;

    @POST
    @Path("create")
    public Response createBook(Book book) {
        //TODO: Add security to allow only admin to create books
        bookService.createBook(book);
        return Response.ok(book).build();
    }

    @PUT
    @Path("update")
    public Response updateBook(Book book) {
        //TODO: Add to check if user is admin
        bookService.updateBook(book);
        return Response.ok(book).build();
    }

    @POST
    @Path("borrow")
    public Response borrowBook(Book book) {
        //TODO: check if user has reached maximum allowable limit
        // or isn't banned or book isn't restricted or book is still
        // available. Reduce book count
        bookService.borrowBook(book);
        return Response.ok(book).build();
    }

    @DELETE
    @Path("remove")
    public Response removeBook(Book book) {
        //TODO: allow only admin to remove book
        bookService.removeBook(book);
        return Response.ok(book).build();
    }

    @PUT
    @Path("restrict")
    public Response restrictBook(Book book) {

        //TODO: allow only admin to restrict book
        return Response.accepted(bookService.restrictBook(book)).build();
    }

    @GET
    @Path("list")
    public Response findAllBooks() {
        return Response.ok(bookService.findAllBooks()).build();
    }

    @GET
    public Response findBooksByQuery(@QueryParam("author") String author,
                                     @QueryParam("isbn") String isbn,
                                     @QueryParam("title") String title) {

        if (author != null && !author.isEmpty()) {
            return Response.ok(bookService.findBooksByAuthor(author)).build();
        } else if (isbn != null && !isbn.isEmpty()) {
            return Response.ok(bookService.findBooksByISBN(isbn)).build();
        } else if (title != null && !title.isEmpty()) {
            return Response.ok(bookService.findBooksByTitle(title)).build();
        }
        return Response.status(400, "Invalid Query Param").build();
    }
}
