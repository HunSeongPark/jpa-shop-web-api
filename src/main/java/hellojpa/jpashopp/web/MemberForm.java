package hellojpa.jpashopp.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Hunseong on 2022/04/13
 */
@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;

    // Address (Embedded value type)
    private String city;
    private String street;
    private String zipcode;
}
