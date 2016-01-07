package org.wickedsource.jpa.eager;

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

//    public Book loadBookWithAuthorsAndReviews(Long id){
//       return bookRepository.findByIdFetchAuthorsAndReviews(id);
//    }

}
