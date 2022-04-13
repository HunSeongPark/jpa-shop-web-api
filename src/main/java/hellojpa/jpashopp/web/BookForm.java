package hellojpa.jpashopp.web;

import hellojpa.jpashopp.domain.item.Book;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Hunseong on 2022/04/13
 */
@Getter
@Setter
public class BookForm {

    private Long id;

    @NotEmpty(message = "상품 이름은 필수임다~")
    private String name;

    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    public static BookForm fromEntity(Book book) {
        BookForm form = new BookForm();
        form.setId(book.getId());
        form.setName(book.getName());
        form.setPrice(book.getPrice());
        form.setStockQuantity(book.getStockQuantity());
        form.setAuthor(book.getAuthor());
        form.setIsbn(book.getIsbn());

        return form;
    }
}
