package hellojpa.jpashopp.api;

import hellojpa.jpashopp.domain.Address;
import hellojpa.jpashopp.domain.Order;
import hellojpa.jpashopp.domain.OrderSearch;
import hellojpa.jpashopp.domain.OrderStatus;
import hellojpa.jpashopp.repository.OrderRepository;
import hellojpa.jpashopp.repository.order.simplequery.OrderSimpleQueryDto;
import hellojpa.jpashopp.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hunseong on 2022/04/15
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName(); // 내부 property getter를 통해 LAZY 강제 초기화
            order.getDelivery().getStatus(); // 내부 property getter를 통해 LAZY 강제 초기화
        }
        return orders;
    }

    /**
     * V2
     * 쿼리 횟수 : Order 조회 1 + 각 Order 별 Member LAZY 조회 N + 각 Order 별 Delivery LAZY 조회 N
     * 영속성 컨텍스트 캐싱 없는 경우(최악의 경우) Query 1+N+N
     */
    @GetMapping("/api/v2/simple-orders")
    public Result<List<SimpleOrderDto>> ordersV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());

        List<SimpleOrderDto> collect = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());

        return new Result<>(collect);
    }

    /**
     * V3
     * 쿼리 횟수 : fetch join을 통해 Order o + Member o.member + Delivery o.delivery를 쿼리 한번으로 조회
     * Query 1
     */
    @GetMapping("/api/v3/simple-orders")
    public Result<List<SimpleOrderDto>> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        List<SimpleOrderDto> collect = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());

        return new Result<>(collect);
    }

    /**
     * V4
     * 쿼리 횟수 : DTO를 직접 조회함으로써 select 절에 원하는 데이터만 join을 통해 쿼리 한 번으로 조회
     * Query 1
     */
    @GetMapping("/api/v4/simple-orders")
    public Result<List<OrderSimpleQueryDto>> ordersV4() {
        return new Result<>(orderSimpleQueryRepository.findAllOrderDto());
    }


    // ================ DTO ================= //

    @Getter
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Getter
    @AllArgsConstructor
    static class SimpleOrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        // from Entity
        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }
}
