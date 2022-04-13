package hellojpa.jpashopp.service;

import hellojpa.jpashopp.domain.Delivery;
import hellojpa.jpashopp.domain.Member;
import hellojpa.jpashopp.domain.Order;
import hellojpa.jpashopp.domain.OrderItem;
import hellojpa.jpashopp.domain.item.Item;
import hellojpa.jpashopp.repository.ItemRepository;
import hellojpa.jpashopp.repository.MemberRepository;
import hellojpa.jpashopp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Hunseong on 2022/04/13
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // Entity 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = Delivery.createDelivery(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 DB 저장
        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        // 주문 Entity 조회
        Order order = orderRepository.findOne(orderId);

        // cancel
        // 위 repository 로직에서 em.find로 영속성 컨텍스트가 관리하는 Order 객체가 반환되므로 더티 체킹 가능
        order.cancel();
    }

//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }
}
