package hellojpa.jpashopp;

import hellojpa.jpashopp.domain.Delivery;
import hellojpa.jpashopp.domain.Member;
import hellojpa.jpashopp.domain.Order;
import hellojpa.jpashopp.domain.OrderItem;
import hellojpa.jpashopp.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * Created by Hunseong on 2022/04/15
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = Member.createMember("userA", "서울", "1", "1111");
            em.persist(member);

            Book book1 = Book.createBook("JPA1 BOOK", 10000, 100, null, null);
            em.persist(book1);

            Book book2 = Book.createBook("JPA2 BOOK", 20000, 200, null, null);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 2);

            Order order = Order.createOrder(member, Delivery.createDelivery(member.getAddress()), orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = Member.createMember("userB", "진주", "2", "2222");
            em.persist(member);

            Book book1 = Book.createBook("SPRING1 BOOK", 30000, 300, null, null);
            em.persist(book1);

            Book book2 = Book.createBook("SPRING2 BOOK", 40000, 400, null, null);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 4);

            Order order = Order.createOrder(member, Delivery.createDelivery(member.getAddress()), orderItem1, orderItem2);
            em.persist(order);
        }
    }
}
