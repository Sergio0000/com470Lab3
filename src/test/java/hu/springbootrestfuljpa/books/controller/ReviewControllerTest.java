/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.springbootrestfuljpa.books.controller;

import hu.springbootrestfuljpa.books.model.Book;
import hu.springbootrestfuljpa.books.model.Review;
import hu.springbootrestfuljpa.books.repository.BookRepository;
import hu.springbootrestfuljpa.books.repository.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Hp
 */
public class ReviewControllerTest {

    private static int ID = 2;
    private static int RELASE = 22;
    private static String AUTOR = "Homero";
    private static String TITLE = "Odisea";

    private static List<Review> REVIEW_LIST = new ArrayList<>();

    private static Book BOOK = new Book();
    private static Review REVIEW = new Review();
    private static Optional<Book> OPTIONAL_BOOK = Optional.of(BOOK);
    private static Optional<Book> OPTIONAL_BOK_EMPTY = Optional.empty();

    @Mock
    private BookRepository bookRepository;
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    ReviewController reviewCotroller;

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
    public void RetrieveAllReviewsTest() {
        Mockito.when(bookRepository.findById(BOOK.getId())).thenReturn(OPTIONAL_BOOK);
        ResponseEntity<List<Review>> httpResponse = reviewCotroller.retrieveAllReviews(BOOK.getId());
        assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void RetrieveAllReviewsNotFoundTest() {
        Mockito.when(bookRepository.findById(BOOK.getId())).thenReturn(OPTIONAL_BOK_EMPTY);
        ResponseEntity<List<Review>> httpResponse = reviewCotroller.retrieveAllReviews(BOOK.getId());
        assertEquals(httpResponse.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void CreateReviewTest() {
        Mockito.when(bookRepository.findById(BOOK.getId())).thenReturn(OPTIONAL_BOOK);
        Mockito.when(reviewRepository.save(REVIEW)).thenReturn(REVIEW);
        ResponseEntity<Object> httpResponse = reviewCotroller.createReview(BOOK.getId(), REVIEW);
        assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void CreateReviewNotFoundTest() {
        Mockito.when(bookRepository.findById(BOOK.getId())).thenReturn(OPTIONAL_BOK_EMPTY);
        ResponseEntity<Object> httpResponse = reviewCotroller.createReview(BOOK.getId(), REVIEW);
        assertEquals(httpResponse.getStatusCode(), HttpStatus.NOT_FOUND);

    }

}
