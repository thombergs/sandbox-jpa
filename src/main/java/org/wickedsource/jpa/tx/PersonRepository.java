package org.wickedsource.jpa.tx;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

    public Person findByName(String name);

}
