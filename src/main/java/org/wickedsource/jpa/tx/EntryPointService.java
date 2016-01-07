package org.wickedsource.jpa.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EntryPointService {

    @Autowired
    private NonTransactionalService nonTransactionalService;

    @Autowired
    private TransactionalService transactionalService;

    public void callNonTransactionalTwiceCatchException() {
        try {
            nonTransactionalService.savePerson(new Person("Chuck Norris"));
            nonTransactionalService.savePersonThrowException(new Person("Arnold Schwarzenegger"));
        } catch (RuntimeException e) {
            System.out.println();
        }
    }

    public void callTransactionalTwiceCatchException() {
        try {
            transactionalService.savePerson(new Person("Chuck Norris"));
            transactionalService.savePersonThrowException(new Person("Arnold Schwarzenegger"));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void callNonTransactionalServiceBubbleException() {
        nonTransactionalService.savePerson(new Person("Chuck Norris"));
        nonTransactionalService.savePersonThrowException(new Person("Arnold Schwarzenegger"));
    }


}
