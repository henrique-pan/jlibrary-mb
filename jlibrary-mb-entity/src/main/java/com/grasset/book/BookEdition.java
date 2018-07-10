package com.grasset.book;

import lombok.Getter;
import lombok.Setter;

import javax.money.MonetaryAmount;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class BookEdition extends Book {

    private Integer idBookEdition;
    private Set<Author> authors;
    private Publisher publisher;
    private String edition;
    private Integer editionYear;
    private String format;
    private Integer totalPages;
    private MonetaryAmount penaltyPrice;
    private MonetaryAmount bookPrice;
    private String editionLanguage;
    private boolean isRare;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BookEdition that = (BookEdition) o;
        return Objects.equals(idBookEdition, that.idBookEdition) &&
                Objects.equals(edition, that.edition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idBookEdition, edition);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookEdition [");
        sb.append("idBookEdition = ").append(idBookEdition);
        sb.append(", publisher = ").append(publisher);
        sb.append(", edition = ").append(edition);
        sb.append(", editionYear = ").append(editionYear);
        sb.append(", format = ").append(format);
        sb.append(", totalPages = ").append(totalPages);
        sb.append(", penaltyPrice = ").append(penaltyPrice);
        sb.append(", bookPrice = ").append(bookPrice);
        sb.append(", editionLanguage = ").append(editionLanguage);
        sb.append(", isRare = ").append(isRare);
        sb.append(']');
        return sb.toString();
    }
}
