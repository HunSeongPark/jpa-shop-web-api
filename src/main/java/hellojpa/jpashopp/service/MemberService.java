package hellojpa.jpashopp.service;

import hellojpa.jpashopp.domain.Member;
import hellojpa.jpashopp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Hunseong on 2022/04/13
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 해당 로직은 동시성 문제가 고려되어 있지 않다.
     * 실제로 멀티 쓰레드 환경에서 해당 로직에 동시에 접근하게 되면 중복 검증과 상관없이 중복된 회원명을 가진 회원이 생성 된다.
     * 회원명 컬럼에 유니크 제약을 걸어서 해결 (** 알아보기)
     */
    @Transactional
    public Long join(Member member) {
        // 중복 회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member); // repository에서 em.persist를 통해 member 객체에 id 주입
        return member.getId();
    }

    public Member findMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> result = memberRepository.findByName(member.getName());
        if (result.size() > 0) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public void update(Long id, String name) {
        Member findMember = memberRepository.findOne(id);
        findMember.changeName(name);
    }
}
