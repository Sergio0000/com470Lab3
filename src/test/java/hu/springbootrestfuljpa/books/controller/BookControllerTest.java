/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.springbootrestfuljpa.books.controller;

import hu.springbootrestfuljpa.books.model.Book;
import hu.springbootrestfuljpa.books.model.Review;
import hu.springbootrestfuljpa.books.repository.BookRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Hp
 */
public class BookControllerTest {

    private static int ID = 2;
    private static int RELASE = 22;
    private static String AUTOR = "Homero";
    private static String TITLE = "Odisea";

    private static List<Review> REVIEW_LIST = new ArrayList<>();

    private static Book BOOK = new Book();
    private static final Optional<Book> OPTIONAL_BOOK = Optional.of(BOOK);
    private static final Optional<Book> OPTIONAL_BOOK_EMPTY = Optional.empty();
    //1 mokear de la que depende la clase
    @Mock
    private BookRepository bookRepository;
    //2hacer injecion de lo que hacemos en la clase
    @InjectMocks
    BookController bookController;

    /*
    public BookControllerTest() {
    }
     */
    //Inicializar 
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        BOOK.setAuthor(AUTOR);
        BOOK.setId(ID);
        BOOK.setRelease(RELASE);
        BOOK.setReviews(REVIEW_LIST);
        BOOK.setTitle(TITLE);

    }

    @Test
    public void retrieveAllBooksTest() {
        final Book book = new Book();
        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
        List<Book> response = bookController.retrieveAllBooks();
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(response.size(), 1);
    }

    @Test
    public void retrieveBookTest() {
        Mockito.when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK);
        ResponseEntity<Book> response = bookController.retrieveBook(ID);
        assertEquals(response.getBody().getAuthor(), AUTOR);
        assertEquals(response.getBody().getTitle(), TITLE);

    }

    @Test
    public void retrieveBookNotFoundTest() {
        Mockito.when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK_EMPTY);
        ResponseEntity<Book> HTTPresponse = bookController.retrieveBook(ID);
        assertEquals(HTTPresponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void createBookTest() {
        Mockito.when(bookRepository.existsById(BOOK.getId())).thenReturn(false);
        ResponseEntity<Object> HTTPresponse = bookController.createBook(BOOK);
        assertEquals(HTTPresponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void createBookConflictTest() {
        Mockito.when(bookRepository.existsById(BOOK.getId())).thenReturn(true);
        ResponseEntity<Object> HTTPresponse = bookController.createBook(BOOK);
        assertEquals(HTTPresponse.getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    public void deleteBookTest() {
        Mockito.when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK);
        bookController.deleteBook(ID);
    }

    @Test
    public void deleteBookNotFountTest() {
        Mockito.when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK_EMPTY);
        bookController.deleteBook(ID);
        ResponseEntity<Object> httpResponse = bookController.deleteBook(ID);
        assertEquals(httpResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
