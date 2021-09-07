package study.datajpa.repository;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member2;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    // 여기에 사용하고싶은 것은 뭐든 추가하면 된다.
    private final EntityManager em;

    @Override
    public List<Member2> findMemberCustom() {
        return em.createQuery("select m from Member2 m")
            .getResultList();
    }
}
