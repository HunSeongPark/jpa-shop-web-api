package hellojpa.jpashopp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hellojpa.jpashopp.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Hunseong on 2022/04/12
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    private int orderPrice; // 주문가격

    private int count; // 주문수량

    public void setOrder(Order order) {
        this.order = order;
    }

    private void setItem(Item item) {
        this.item = item;
    }

    private void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    private void setCount(int count) {
        this.count = count;
    }

    // === 생성 메서드 === //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStockQuantity(count);
        return orderItem;
    }

    // === 비즈니스 로직 === //
    public void cancel() {
        getItem().addStockQuantity(count);
    }

    // === 조회 로직 === //
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
