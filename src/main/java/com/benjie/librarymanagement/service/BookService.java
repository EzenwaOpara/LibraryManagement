package com.benjie.librarymanagement.service;

/*
 * Created by OPARA BENJAMIN
 * On 12/14/2020 - 10:20 PM
 */

import com.benjie.librarymanagement.entity.Book;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class BookService {

    @Inject
    private EntityManager entityManager;

    @Inject
    private BookQueryService bookQueryService;

    public Book createBook(Book book) {
        entityManager.persist(book);
        return book;
    }

    public Book updateBook(Book book) {
        entityManager.merge(book);
        return book;
    }

    public Book borrowBook(Book book) {
        //TODO: reduce book count
        entityManager.merge(book);
        return book;
    }

    public Book restrictBook(Book book) {
        entityManager.merge(book);
        return book;
    }

    public Book removeBook(Book book) {
        entityManager.remove(book);
        return book;
    }

    public List<Book> findAllBooks() {
        return bookQueryService.findAllBooks();
    }

    public List<Book> findBooksByTitle(String title) {
        return bookQueryService.findBooksByTitle(title);
    }

    public List<Book> findBooksByAuthor(String author) {
        return bookQueryService.findBooksByAuthor(author);
    }

    public List<Book> findBooksByISBN(String isbn) {
        return bookQueryService.findBooksByISBN(isbn);
    }
}
