package org.wickedsource.jpa.lazy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface BookRepository extends CrudRepository<Book, Long>{

    @Query("select b from Book b join fetch b.authors where b.id = :id")
    public Book findByIdFetchAuthors(@Param("id") Long id);

}
