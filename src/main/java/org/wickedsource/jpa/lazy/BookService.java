package org.wickedsource.jpa.lazy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book loadBook(Long id) {
        return bookRepository.findOne(id);
    }

    public Book loadBookWithAuthors(Long id) {
        Book book = bookRepository.findOne(id);

        // trigger the lazy loading ...

        // 1) either with Hibernate's initialize method
        // Hibernate.initialize(book.getAuthors());

        // 2) or by just accessing an element of the list
        // book.getAuthors().get(0);

        // 3) or by loading it via JOIN FETCH query
        book = bookRepository.findByIdFetchAuthors(id);

        return book;
    }

}
