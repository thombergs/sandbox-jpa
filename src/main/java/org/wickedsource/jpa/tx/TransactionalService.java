package org.wickedsource.jpa.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionalService {

    @Autowired
    private PersonRepository personRepository;

    public void savePerson(Person person) {
        personRepository.save(person);
    }

    public void savePersonThrowException(Person person) {
        personRepository.save(person);
        throw new RuntimeException("BWAAAAAAAAAAH!");
    }

}
