package hellojpa.jpashopp.api;

import hellojpa.jpashopp.domain.*;
import hellojpa.jpashopp.repository.OrderRepository;
import hellojpa.jpashopp.repository.order.query.OrderQueryDto;
import hellojpa.jpashopp.repository.order.query.OrderQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hunseong on 2022/04/15
 */
@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

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

    /**
     * V2
     * 쿼리 횟수 : Order 조회 1 + member 2 + delivery 2 + orderItems 2 + item 4 (각 order마다 2개씩) = 11
     */
    @GetMapping("/api/v2/orders")
    public Result<List<OrderDto>> ordersV2() {

        List<Order> orders = orderRepository.findAll(new OrderSearch());
        List<OrderDto> collect = orders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return new Result<>(collect);
    }

    /**
     * V3
     * 쿼리 횟수 : fetch join으로 Query 1번
     * 페이징 불가능, select절에 많은 데이터
     */
    @GetMapping("/api/v3/orders")
    public Result<List<OrderDto>> ordersV3() {

        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> collect = orders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return new Result<>(collect);
    }

    /**
     * V3.1
     * Paging 불가능한 V3 보완
     * batch fetch size 지정
     * 페이징 가능, 쿼리 횟수 Member,Delivery 1 + OrderItems 1 + Item 1 = 3
     */
    @GetMapping("/api/v3.1/orders")
    public Result<List<OrderDto>> ordersV3Paging(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        List<OrderDto> collect = orders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return new Result<>(collect);
    }

    /**
     * V4
     * XToMany에 대한 DTO 직접 조회 방식
     * XToOne은 row 수에 영향을 주지 않으므로 fetch join을 통해 한번에 조회 (Query 1)
     * XToMany는 row 수에 영향을 주므로 각 Order에 대한 orderId를 기반으로 OrderItems 조회 (Query Order 수 N)
     * 쿼리 횟수 1 + N
     */
    @GetMapping("/api/v4/orders")
    public Result<List<OrderQueryDto>> ordersV4() {

        // 쿼리 1 (Order) + N (Order 수만큼 루프 돌면서 OrderItems 저장)
        List<OrderQueryDto> orders = orderQueryRepository.findOrderQueryDtos();

        return new Result<>(orders);
    }

    /**
     * V5
     * V4에서 각 Order마다 컬렉션을 조회하는 +N 단점 보완
     * XToOne은 row 수에 영향을 주지 않으므로 fetch join을 통해 한번에 조회 (Query 1)
     * XToMany는 Order의 Id List를 IN절에 조건으로 주어 OrderItem List 한번에 조회
     * 해당 List는 orderId로 분류하여 Map<Long id, List<OrderItemDTO>> 형태로 저장하여 map.get(order.getId))로 저장
     * 쿼리 횟수 1 + 1
     */
    @GetMapping("/api/v5/orders")
    public Result<List<OrderQueryDto>> ordersV5() {

        List<OrderQueryDto> orders = orderQueryRepository.findOrderQueryDtosOpt();

        return new Result<>(orders);
    }


    // ============== DTO ============== //

    @Getter
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Getter
    static class OrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    static class OrderItemDto {

        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
