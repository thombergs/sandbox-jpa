package org.wickedsource.jpa.lazy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    // LAZY is the default fetch type, but we set it explicitly nevertheless
    @OneToMany(fetch = FetchType.LAZY)
    private List<Author> authors = new ArrayList<Author>();

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
}
