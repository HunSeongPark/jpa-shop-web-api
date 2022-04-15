package hellojpa.jpashopp.api;

import hellojpa.jpashopp.domain.Order;
import hellojpa.jpashopp.domain.OrderSearch;
import hellojpa.jpashopp.repository.OrderRepository;
import hellojpa.jpashopp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Hunseong on 2022/04/15
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName(); // 내부 property getter를 통해 LAZY 강제 초기화
            order.getDelivery().getStatus(); // 내부 property getter를 통해 LAZY 강제 초기화
        }
        return orders;
    }
}
