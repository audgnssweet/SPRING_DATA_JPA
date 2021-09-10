package study.datajpa.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

@Repository
public class JpaOriginalMemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public void delete(Member member) {
        em.remove(member);
    }

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class).getSingleResult();
    }

    public List<Member> findAllByUsernameAndAgeGreaterThan(String username, int age) {
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age", Member.class)
            .setParameter("username", username)
            .setParameter("age", age)
            .getResultList();
    }

    /**
     * 검색조건 : 나이 10살, 정렬조건 : 이름 내림차순, 페이징 조건 : 첫 번째 페이지, 페이지당 데이터 3건
     *
     * @Param offset 몇 번째부터 시작해서
     * @Param limit 몇 개를 가져와
     */
    public List<Member> findByPage(int age, int offset, int limit) {
        //page 1 -> offset = 0, limit = 10
        //page 2 -> offset = 10, limit = 10
        //...

        return em.createQuery("select m from Member m where m.age = :age order by m.username desc", Member.class)
            .setParameter("age", age)
            .setFirstResult(offset)   //어디서부터 가져올거?
            .setMaxResults(limit)   //몇개 가져올거?
            .getResultList();
    }

    //보통 두 개를 같이써줌
    public long totalCount(int age) {
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
            .setParameter("age", age)
            .getSingleResult();
    }

    /**
     * 순수 JPA의 벌크성 쿼리
     */
    public int bulkAgePlus(int age) {
        int resCount = em.createQuery("update Member m set m.age = m.age + 1 where m.age >= :age")
            .setParameter("age", age)
            .executeUpdate();
        return resCount;
    }
}
