package hellojpa.jpashopp.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hunseong on 2022/04/12
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    private void setName(String name) {
        this.name = name;
    }

    private void setAddress(Address address) {
        this.address = address;
    }

    // === 생성 메서드 === //
    public static Member createMember(String name, String city, String street, String zipcode) {
        Member member = new Member();
        Address address = new Address(city, street, zipcode);
        member.setName(name);
        member.setAddress(address);

        return member;
    }

    // API 예제용. name만 가지는 Member 객체 생성 메서드
    public static Member createMember(String name) {
        Member member = new Member();
        member.setName(name);

        return member;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
