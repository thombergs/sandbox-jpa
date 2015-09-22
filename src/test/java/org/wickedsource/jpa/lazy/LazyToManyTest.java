package org.wickedsource.jpa.lazy;

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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({DbUnitTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
public class LazyToManyTest {

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan
    static class ContextConfiguration{

    }

    @Autowired
    private BookService bookService;

    @Test(expected = LazyInitializationException.class)
    @DatabaseSetup("/book_with_authors.xml")
    @DatabaseTearDown(value = "/book_with_authors.xml", type = DatabaseOperation.DELETE_ALL)
    public void testFind(){
        Book book = bookService.loadBook(1l);

        // get a PROXY list because of FetchType LAZY ...
        List<Author> authors = book.getAuthors();

        // ... that executes a database query when first accessed
        Author author1 = authors.get(0);

        // A LazyInitializationException is thrown, because this test class is not within a transaction context
    }

    @Test
    @DatabaseSetup("/book_with_authors.xml")
    @DatabaseTearDown(value = "/book_with_authors.xml", type = DatabaseOperation.DELETE_ALL)
    public void testFindWithAuthors(){
        Book book = bookService.loadBookWithAuthors(1l);

        // get a REAL list of Authors, since the LAZY loading was done in the service (which is in a transaction context).
        List<Author> authors = book.getAuthors();

        // accessing that list does not trigger a database query ...
        Assert.assertEquals(2, authors.size());
        Author author1 = authors.get(0);
        Assert.assertEquals("Kevin J. Anderson", author1.getName());

        // ... thus we have no exception.
    }

}
