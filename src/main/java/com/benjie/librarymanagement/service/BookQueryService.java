package com.benjie.librarymanagement.service;

/*
 * Created by OPARA BENJAMIN
 * On 12/15/2020 - 12:27 PM
 */

import com.benjie.librarymanagement.entity.Book;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class BookQueryService {

    @Inject
    private EntityManager entityManager;

    public List<Book> findAllBooks() {
       return entityManager.createNamedQuery(Book.FIND_ALL_BOOKS, Book.class)
               .getResultList();
    }

    public List<Book> findBooksByTitle(String title) {
        return entityManager.createNamedQuery(Book.FIND_BOOKS_BY_TITLE, Book.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }

    public List<Book> findBooksByAuthor(String author) {
        return entityManager.createNamedQuery(Book.FIND_BOOKS_BY_AUTHOR, Book.class)
                .setParameter("author", "%" + author + "%")
                .getResultList();
    }

    public List<Book> findBooksByISBN(String isbn) {
        return entityManager.createNamedQuery(Book.FIND_BOOKS_BY_ISBN, Book.class)
                .setParameter("isbn", isbn)
                .getResultList();
    }
}
