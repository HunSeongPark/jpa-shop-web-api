package hellojpa.jpashopp.service;

import hellojpa.jpashopp.domain.Member;
import hellojpa.jpashopp.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Created by Hunseong on 2022/04/13
 */
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {

        // given
        Member member = Member.createMember("hunseong", "1", "2", "3");

        // when
        Long memberId = memberService.join(member);

        // then
        assertThat(member).isSameAs(memberRepository.findOne(memberId));

    }
    
    @Test
    void 중복_회원_예외() {
        
        // given
        Member member = Member.createMember("hunseong", "1", "1", "1");
        Member newMember = Member.createMember("hunseong", "2", "3", "4");

        memberService.join(member);

        // when & then
        assertThrows(IllegalStateException.class, () -> memberService.join(newMember));
        
    }
}