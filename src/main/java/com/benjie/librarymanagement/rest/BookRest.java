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
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Path("books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookRest {

    @Inject
    private BookService bookService;
    private int requestCode;

    @POST
    @Path("create")
    public Response createBook(Book book) {
        //TODO: Add security to allow only admin to create books
        if (bookService.exists(book.getIsbn())) {
            return Response.status(/*Response.Status.CONFLICT*/ 409,
                    "A book with same ISBN already exists").build();
        }
        bookService.createBook(book);
        URI location = UriBuilder.fromResource(BookRest.class)
                .queryParam("isbn", book.getIsbn())
                .build();
        return Response.created(location).build();
    }

    @PUT
    @Path("update")
    public Response updateBook(@QueryParam("isbn") String isbn, Book book) {
        //TODO: Add to check if user is admin
        if (bookService.updateBook(isbn, book) != null) {
            return Response.ok(book).build();
        }
        return Response.status(404,
                "Book with the following ISBN: " + isbn + " does not exit.").build();
    }

    @PUT
    @Path("borrow")
    @Produces("text/plain")
    public Response borrowBook(@QueryParam("isbn") String isbn) {
        //TODO: check if user has reached maximum allowable limit
        // or isn't banned or book isn't restricted or book is still
        // available. Reduce book count
        // add the user to borrower's list
        requestCode = bookService.borrowBook(isbn);

        if (requestCode == 2) {
            return Response.ok("Sorry this book is currently restricted").build();
        } else if (requestCode == 0) {
            return Response.status(404,
                    "Sorry the book is currently not available").build();
        } else if (requestCode == -1) {
            return Response.status(404,
                    "Book with the following ISBN: " + isbn + " does not exit.").build();
        }
        return Response.ok("Successfully Borrowed").build();
    }

    @PUT
    @Path("return")
    @Produces("text/plain")
    public Response returnBook(@QueryParam("isbn") String isbn) {
        //TODO: check to ensure caller borrowed the book
        requestCode = bookService.returnBook(isbn);
        if (requestCode == 1) {
            return Response.ok("Successfully Returned").build();
        } else if (requestCode == -1) {
            return Response.status(404,
                    "Book with the following ISBN: " + isbn + " does not exit.").build();
        }
        return Response.noContent().build();
    }

    @DELETE
    @Path("remove")
    @Produces("text/plain")
    public Response removeBook(@QueryParam("isbn") String isbn) {
        //TODO: allow only admin to remove book
        requestCode = bookService.removeBook(isbn);
        if (requestCode == 1) {
            return Response.ok("Successfully removed").build();
        } else if (requestCode == -1) {
            return Response.status(404,
                    "Book with the following ISBN: " + isbn + " does not exit.").build();
        }
        return Response.noContent().build();
    }

    @PUT
    @Path("restrict")
    @Produces("text/plain")
    public Response restrictBook(@QueryParam("isbn") String isbn, @QueryParam("value") boolean state) {

        //TODO: allow only admin to restrict book
        requestCode = bookService.restrictBook(isbn, state);
        if (requestCode == 1) {
            return Response.ok("Successfully restricted").build();
        } else if (requestCode == 0) {
            return Response.ok("Successfully restored").build();
        } else if (requestCode == -1) {
            return Response.status(404,
                    "Book with the following ISBN: " + isbn + " does not exit.").build();
        }
        return Response.noContent().build();
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
