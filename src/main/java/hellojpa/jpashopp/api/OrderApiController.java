package hellojpa.jpashopp.api;

import hellojpa.jpashopp.domain.Order;
import hellojpa.jpashopp.domain.OrderItem;
import hellojpa.jpashopp.domain.OrderSearch;
import hellojpa.jpashopp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Hunseong on 2022/04/15
 */
@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName(); // LAZY 강제 초기화(Member)
            order.getDelivery().getStatus(); // LAZY 강제 초기화(Delivery)
            
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.forEach(o -> o.getItem().getName()); // LAZY 강제 초기화(OrderItem, Item)
        }

        return orders;
    }
}
