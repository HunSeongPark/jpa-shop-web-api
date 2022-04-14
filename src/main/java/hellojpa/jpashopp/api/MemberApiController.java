package hellojpa.jpashopp.api;

import hellojpa.jpashopp.domain.Member;
import hellojpa.jpashopp.service.MemberService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable Long id, @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member updatedMember = memberService.findMember(id);

        return new UpdateMemberResponse(updatedMember.getId(), updatedMember.getName());
    }

    // ================= DTO =================== //
    @Getter
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Getter
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Getter
    static class UpdateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Getter
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

}
