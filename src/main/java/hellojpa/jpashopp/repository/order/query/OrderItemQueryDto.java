package hellojpa.jpashopp.repository.order.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

/**
 * Created by Hunseong on 2022/04/15
 */
@Getter
public class OrderItemQueryDto {

    @JsonIgnore
    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemQueryDto(Long orderId, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
