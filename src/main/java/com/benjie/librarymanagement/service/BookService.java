package com.benjie.librarymanagement.service;

/*
 * Created by OPARA BENJAMIN
 * On 12/14/2020 - 10:20 PM
 */

import com.benjie.librarymanagement.entity.Book;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
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

    public Book updateBook(String isbn, Book book) {
        if (exists(isbn)) {
            Book existingBook = bookQueryService.findBooksByISBN(isbn).get(0);

            if (book.getIllustrations() != null)
                existingBook.setIllustrations(book.getIllustrations());
            if (book.getAuthor() != null)
                existingBook.setAuthor(book.getAuthor());
            if (book.getAvailableCopies() != null)
                existingBook.setAvailableCopies(book.getAvailableCopies());
            if (book.getDescription() != null)
                existingBook.setDescription(book.getDescription());
            if (book.getNbOfPage() != null)
                existingBook.setNbOfPage(book.getNbOfPage());
            if (book.getTitle() != null)
                existingBook.setTitle(book.getTitle());
            if (book.getIsbn() != null)
                existingBook.setIsbn(book.getIsbn());
            if (book.getDateUpdated() != null)
                existingBook.setDateUpdated(LocalDate.now());

            entityManager.merge(existingBook);
            return existingBook;
        }
        return null;
    }


    //return 1 if successful, 0 if unavailable, 2 if restricted and -1 if book does not exist
    public int borrowBook(String isbn) {
        if (exists(isbn)) {
            Book book = bookQueryService.findBooksByISBN(isbn).get(0);
            Long availableBooks = book.getAvailableCopies();
            if (book.getLocked()) {
                return 2;
            }
            if (availableBooks > 0) {
                book.setAvailableCopies(availableBooks - 1);
                entityManager.merge(book);
                return 1;
            } else {
                return 0;
            }
        }
        return -1;
    }

    //return 1 if successful and -1 if book does not exist
    public int returnBook(String isbn) {
        //TODO check if the caller actually borrowed a book
        if (exists(isbn)) {
            Book book = bookQueryService.findBooksByISBN(isbn).get(0);
            Long availableBooks = book.getAvailableCopies();
            book.setAvailableCopies(++availableBooks);
            entityManager.merge(book);
            return 1;
        }
        return -1;
    }

    //return 1 if successful and -1 if book does not exist
    public int restrictBook(String isbn, boolean state) {
        if (exists(isbn)) {
            Book book = bookQueryService.findBooksByISBN(isbn).get(0);
            book.setLocked(state);
            entityManager.merge(book);
            if (state) return 1;
            else return 0;
        }
        return -1;
    }

    //return 1 if successful and -1 if book does not exist
    public int removeBook(String isbn) {
        if (exists(isbn)) {
            Book book = bookQueryService.findBooksByISBN(isbn).get(0);
            entityManager.remove(book);
            return 1;
        }
        return -1;
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

    public boolean exists(String isbn) {
        if (bookQueryService.findBooksByISBN(isbn).isEmpty() || bookQueryService.findBooksByISBN(isbn) == null) {
            return false;
        } else {
            return true;
        }
    }
}
