package hellojpa.jpashopp.service;

import hellojpa.jpashopp.domain.Member;
import hellojpa.jpashopp.domain.Order;
import hellojpa.jpashopp.domain.OrderItem;
import hellojpa.jpashopp.domain.item.Book;
import hellojpa.jpashopp.domain.item.Item;
import hellojpa.jpashopp.exception.NotEnoughStockException;
import hellojpa.jpashopp.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static hellojpa.jpashopp.domain.OrderStatus.CANCEL;
import static hellojpa.jpashopp.domain.OrderStatus.ORDER;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Hunseong on 2022/04/13
 */
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EntityManager em;


    @Test
    void 주문() {
        
        // given
        Member member = createMember("3", "2", "1", "memberA");
        em.persist(member);
        Book book = createBook("Book", 10000, 10, "a", "b");
        em.persist(book);

        int count = 3;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), count);

        // then
        Order order = orderRepository.findOne(orderId);

        // status == ORDER
        assertThat(order.getStatus()).isEqualTo(ORDER);
        // 주문자명 일치
        assertThat(order.getMember().getName()).isEqualTo(member.getName());
        // 총 주문 금액 일치
        assertThat(order.getTotalPrice()).isEqualTo(book.getPrice() * count);
    }

    @Test
    void 주문수량초과() {

        // given
        Member member = createMember("3", "2", "1", "memberA");
        em.persist(member);
        Book book = createBook("Book", 10000, 10, "a", "b");
        em.persist(book);
        int count = 11;

        // when & then
        assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), book.getId(), count));
    }

    @Test
    void 주문취소() {

        // given
        Member member = createMember("3", "2", "1", "memberA");
        em.persist(member);
        Book book = createBook("Book", 10000, 10, "a", "b");
        em.persist(book);
        int count = 3;

        Long orderId = orderService.order(member.getId(), book.getId(), count);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order order = orderRepository.findOne(orderId);
        assertThat(order.getStatus()).isEqualTo(CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }

    private Book createBook(String name, int price, int count, String author, String isbn) {
        return Book.createBook(name, price, count, author, isbn);
    }

    private Member createMember(String name, String city, String street, String zipcode) {
        return Member.createMember(name, city, street, zipcode);
    }
}