package hellojpa.jpashopp.repository.order.simplequery;

import hellojpa.jpashopp.domain.Address;
import hellojpa.jpashopp.domain.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Created by Hunseong on 2022/04/15
 */
@Getter
public class OrderSimpleQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(
            Long orderId, String name, LocalDateTime orderDate,
            OrderStatus orderStatus, Address address
    ) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
