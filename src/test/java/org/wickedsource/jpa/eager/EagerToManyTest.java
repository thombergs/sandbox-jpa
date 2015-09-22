package org.wickedsource.jpa.eager;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({DbUnitTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
public class EagerToManyTest {

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan
    static class ContextConfiguration {

    }

    @Autowired
    private BookService bookService;

    @Test(expected = LazyInitializationException.class)
    @DatabaseSetup("/book_with_authors_and_reviews.xml")
    @DatabaseTearDown(value = "/book_with_authors_and_reviews.xml", type = DatabaseOperation.DELETE_ALL)
    public void testFind() {
        Book book = bookService.loadBook(1l);
        Assert.assertNotNull(book);

        // we got the book, but the reviews are not loaded and will produce a LazyInitializationException when accessed
        book.getReviews().get(0);
    }

    @Test
    @DatabaseSetup("/book_with_authors_and_reviews.xml")
    @DatabaseTearDown(value = "/book_with_authors_and_reviews.xml", type = DatabaseOperation.DELETE_ALL)
    public void testFindWithAuthorsAndReviews() {
//        Book book = bookService.loadBook(1l);
//        Assert.assertNotNull(book);
//
//        book.getReviews().get(0);
//        book.getAuthors().get(0);
    }


}
