package study.datajpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void saveOne() {
        for (int i = 0; i < 30; i++) {
            memberRepository.save(
                new Member("정명훈" + i, 25, null)
            );
            memberRepository.save(
                new Member("배서현" + i, 24, null)
            );
        }
    }

}
