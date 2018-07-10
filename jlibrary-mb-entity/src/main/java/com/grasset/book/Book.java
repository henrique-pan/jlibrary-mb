package com.grasset.book;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Book {

    private Integer idBook;
    private String title;
    private Integer bookYear;
    private String ISBN;
    private String originalLanguage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(ISBN, book.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book [");
        sb.append("idBook = ").append(idBook);
        sb.append(", title = ").append(title);
        sb.append(", bookYear = ").append(bookYear);
        sb.append(", ISBN = ").append(ISBN);
        sb.append(", originalLanguage = ").append(originalLanguage);
        sb.append(']');
        return sb.toString();
    }
}
