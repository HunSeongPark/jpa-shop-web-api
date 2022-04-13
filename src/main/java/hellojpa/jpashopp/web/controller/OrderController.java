package hellojpa.jpashopp.web.controller;

import hellojpa.jpashopp.domain.Member;
import hellojpa.jpashopp.domain.Order;
import hellojpa.jpashopp.domain.OrderSearch;
import hellojpa.jpashopp.domain.item.Item;
import hellojpa.jpashopp.service.ItemService;
import hellojpa.jpashopp.service.MemberService;
import hellojpa.jpashopp.service.OrderService;
import hellojpa.jpashopp.web.OrderForm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Hunseong on 2022/04/13
 */
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(@ModelAttribute OrderForm orderForm, Model model) {
        List<Member> members = memberService.findAll();
        List<Item> items = itemService.findAll();
        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(Model model, @Valid @ModelAttribute OrderForm orderForm, BindingResult result) {
        if (result.hasErrors()) {
            List<Member> members = memberService.findAll();
            List<Item> items = itemService.findAll();
            model.addAttribute("members", members);
            model.addAttribute("items", items);
            return "order/orderForm";
        }

        orderService.order(orderForm.getMemberId(), orderForm.getItemId(), orderForm.getCount());
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }
}
