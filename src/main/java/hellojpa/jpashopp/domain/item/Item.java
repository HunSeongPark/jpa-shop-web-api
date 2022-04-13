package hellojpa.jpashopp.domain.item;

import hellojpa.jpashopp.domain.Category;
import hellojpa.jpashopp.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hunseong on 2022/04/12
 */
@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    protected String name;

    protected int price;

    protected int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    protected void setName(String name) {
        this.name = name;
    }

    protected void setPrice(int price) {
        this.price = price;
    }

    protected void setStockQuantity(int quantity) {
        this.stockQuantity = quantity;
    }

    // === 비즈니스 로직 === //

    public void addStockQuantity(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStockQuantity(int quantity) {
        int totalQuantity = this.stockQuantity - quantity;
        if (totalQuantity < 0) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.stockQuantity = totalQuantity;
    }
}
