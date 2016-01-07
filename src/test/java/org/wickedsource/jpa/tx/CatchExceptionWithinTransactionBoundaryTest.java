package org.wickedsource.jpa.tx;

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
public class CatchExceptionWithinTransactionBoundaryTest {

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan
    static class ContextConfiguration {

    }

    @Autowired
    private EntryPointService entryPointService;

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DatabaseSetup("/empty.xml")
    @DatabaseTearDown(value = "/empty.xml", type = DatabaseOperation.DELETE_ALL)
    public void test() {
        entryPointService.callNonTransactionalTwiceCatchException();
        Person chuck = personRepository.findByName("Chuck Norris");
        Person arnie = personRepository.findByName("Arnold Schwarzenegger");
        Assert.assertNotNull(chuck);
        Assert.assertNotNull(arnie);
    }


}
