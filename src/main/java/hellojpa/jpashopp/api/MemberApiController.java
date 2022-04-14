package hellojpa.jpashopp.api;

import hellojpa.jpashopp.domain.Member;
import hellojpa.jpashopp.service.MemberService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Hunseong on 2022/04/14
 */
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = Member.createMember(request.getName());
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    // ================= DTO =================== //
    @Getter
    static class CreateMemberResponse {

        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Getter
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

}
