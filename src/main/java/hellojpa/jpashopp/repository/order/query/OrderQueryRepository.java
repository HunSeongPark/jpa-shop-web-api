package hellojpa.jpashopp.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Hunseong on 2022/04/15
 */
@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos() {

        // XToOne join으로 한번에 조회 (쿼리 1)
        List<OrderQueryDto> orders = findOrders();

        // Order 마다 하나씩 OrderItem 추가 (추가 쿼리 Order 수 N)
        orders.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });

        return orders;
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                        "select new " +
                                "hellojpa.jpashopp.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                                " from Order o" +
                                " join o.member m" +
                                " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                        "select new " +
                                "hellojpa.jpashopp.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public List<OrderQueryDto> findOrderQueryDtosOpt() {

        // XToOne join으로 한번에 조회 (쿼리 1)
        List<OrderQueryDto> orders = findOrders();

        // IN을 통해 orders Id List 범위로 모든 order의 OrderItem 쿼리 (쿼리 1)
        // 이후 Collectors.groupingBy를 통해 Map<Long id, List<OrderItemDTO>> 로 그룹핑
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(orders));

        // 각 order를 forEach 하며 map에서 해당 orderId에 해당하는 OrderItem List 저장
        orders.forEach(o -> {
            o.setOrderItems(orderItemMap.get(o.getOrderId()));
        });

        return orders;
    }

    private List<Long> toOrderIds(List<OrderQueryDto> orders) {
        return orders.stream()
                .map(OrderQueryDto::getOrderId)
                .collect(Collectors.toList());
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {

        List<OrderItemQueryDto> orderItems =
                em.createQuery(
                                "select new " +
                                        "hellojpa.jpashopp.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                        " from OrderItem oi" +
                                        " join oi.item i" +
                                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                        .setParameter("orderIds", orderIds)
                        .getResultList();

        return orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    }
}
