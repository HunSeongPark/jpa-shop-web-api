package hellojpa.jpashopp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Hunseong on 2022/04/12
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    @JsonIgnore
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // 배송상태 [READY, COMP]

    public void setOrder(Order order) {
        this.order = order;
    }

    private void setAddress(Address address) {
        this.address = address;
    }

    // === 생성 메서드 === //
    public static Delivery createDelivery(Address address) {
        Delivery delivery = new Delivery();
        delivery.setAddress(address);
        return delivery;
    }
}
