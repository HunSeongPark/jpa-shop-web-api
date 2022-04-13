package hellojpa.jpashopp.domain.item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Hunseong on 2022/04/12
 */
@Entity
@Getter
@DiscriminatorValue("B")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item {

    private String author;
    private String isbn;

    private void setAuthor(String author) {
        this.author = author;
    }

    private void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // === 생성 메서드 === //
    public static Book createBook(String name, int price, int count, String author, String isbn) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(count);
        book.setAuthor(author);
        book.setIsbn(isbn);

        return book;
    }
}
