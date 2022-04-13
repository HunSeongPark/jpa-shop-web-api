package hellojpa.jpashopp.web.controller;

import hellojpa.jpashopp.domain.Member;
import hellojpa.jpashopp.service.MemberService;
import hellojpa.jpashopp.web.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Hunseong on 2022/04/13
 */
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * @ModelAttribute 통해 빈 MemberForm 객체를 생성하여 model에 넘김
     */
    @GetMapping("/members/new")
    public String createForm(@ModelAttribute MemberForm memberForm) {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid @ModelAttribute MemberForm memberForm, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = Member.createMember(
                memberForm.getName(), memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String members(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);

        return "members/memberList";
    }
}
