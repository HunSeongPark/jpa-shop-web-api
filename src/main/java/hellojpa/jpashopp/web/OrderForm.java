package hellojpa.jpashopp.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Hunseong on 2022/04/13
 */
@Getter
@Setter
public class OrderForm {

    @NotNull(message = "주문 회원을 선택해주세요.")
    private Long memberId;

    @NotNull(message = "주문 상품을 선택해주세요.")
    private Long itemId;

    @Min(value = 1, message = "주문 수량은 최소 1개입니다.")
    private int count;
}
