package hellojpa.jpashopp.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Hunseong on 2022/04/15
 */
@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findAllOrderDto() {
        return em.createQuery(
                "select new " +
                        "hellojpa.jpashopp.repository.order.simplequery.OrderSimpleQueryDto" +
                        "(o.id, m.name, o.orderDate, o.status, d.address)"
                        + " from Order o"
                        + " join o.member m"
                        + " join o.delivery d", OrderSimpleQueryDto.class).getResultList();
    }
}
