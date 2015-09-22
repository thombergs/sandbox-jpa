package org.wickedsource.jpa.eager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Author> authors = new ArrayList<Author>();

    // Only ONE *ToMany relationship can be marked as EAGER, otherwise Hibernate will throw an MultipleBagFetchException
    // when initializing the persistence context. Thus, the second *ToMany will be marked as LAZY.
    @OneToMany(fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<Review>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
