package com.benjie.librarymanagement.entity;

/*
 * Created by OPARA BENJAMIN
 * On 12/13/2020 - 9:57 PM
 */

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@Transactional
@NamedQuery(name = Book.FIND_ALL_BOOKS, query = "select b from Book b")
@NamedQuery(name = Book.FIND_BOOKS_BY_TITLE, query = "select b from Book b where b.title like :title")
@NamedQuery(name = Book.FIND_BOOKS_BY_AUTHOR, query = "select b from Book b where b.author like :author")
@NamedQuery(name = Book.FIND_BOOKS_BY_ISBN, query = "select b from Book b where b.isbn = :isbn")
public class Book implements Serializable {

    public static final String FIND_ALL_BOOKS = "Book.findAllBooks";
    public static final String FIND_BOOKS_BY_TITLE = "Book.findBooksByTitle";
    public static final String FIND_BOOKS_BY_AUTHOR = "Book.findBooksByAuthor";
    public static final String FIND_BOOKS_BY_ISBN = "Book.findBooksByISBN";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Author cannot be empty")
    private String author;

    @NotNull(message = "Description cannot be empty")
    @Size(min = 10, message = "Minimum of 10 characters for description")
    private String description;

    @NotNull(message = "ISBN cannot be empty")
    private String isbn;

    //    @Pattern(regexp = "^[0-9]", message = "Page should be a valid integer number")
    @NotNull(message = "Title cannot be blank")
    //@Size//(min = 1, message = "A book must have at least 1 page")
    private Long nbOfPage;

    @NotNull(message = "Illustrations cannot be empty")
    private Boolean illustrations;

    private Boolean lockStatus;

    @FutureOrPresent
    private LocalDate dateCreated;

    @FutureOrPresent
    private LocalDate dateBorrowed;

    @FutureOrPresent
    private LocalDate dueDate;

    @FutureOrPresent
    private LocalDate dateReturned;

    @FutureOrPresent
    private LocalDate dateUpdated;

    @NotNull
    private Long availableCopies;

    @ManyToMany(mappedBy = "currentHolder", fetch = FetchType.EAGER)
    private Collection<LibraryUser> libraryMember = new ArrayList<>();

    public Book() {
    }

    public Book(@NotNull(message = "Title cannot be empty") String title,
                @NotNull(message = "Author cannot be empty") String author,
                @NotNull(message = "Description cannot be empty")
                @Size(min = 10, message = "Minimum of 10 characters for description") String description,
                @NotNull(message = "ISBN cannot be empty") String isbn,
                @NotNull(message = "Number of pages cannot be blank") /*@Size(min = 1, message = "A book must have at least 1 page")*/
                /*@Pattern(regexp = "^[0-9]", message = "Page should be a valid integer number")*/ Long nbOfPage,
                @NotNull(message = "Illustrations cannot be empty") Boolean illustrations,
                @NotNull Long availableCopies) {

        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.nbOfPage = nbOfPage;
        this.illustrations = illustrations;
        this.availableCopies = availableCopies;
    }

    @PrePersist
    private void init() {
        setLockStatus(false);
        setDateCreated(LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getNbOfPage() {
        return nbOfPage;
    }

    public void setNbOfPage(Long nbOfPage) {
        this.nbOfPage = nbOfPage;
    }

    public Boolean getIllustrations() {
        return illustrations;
    }

    public void setIllustrations(Boolean illustrations) {
        this.illustrations = illustrations;
    }

    public Boolean getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Boolean locked) {
        this.lockStatus = locked;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(LocalDate dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(LocalDate dateReturned) {
        this.dateReturned = dateReturned;
    }

    public Long getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Long availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Collection<LibraryUser> getLibraryUser() {
        return libraryMember;
    }

    public void setLibraryUser(Collection<LibraryUser> libraryUser) {
        this.libraryMember = libraryUser;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
