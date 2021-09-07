package study.datajpa.repository;

import com.dokitlist.dooyaho.study.entity.Member2;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class JpaOriginalMemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Member2 save(Member2 member2) {
        em.persist(member2);
        return member2;
    }

    public void delete(Member2 member2) {
        em.remove(member2);
    }

    public Optional<Member2> findById(Long id) {
        Member2 member2 = em.find(Member2.class, id);
        return Optional.ofNullable(member2);
    }

    public List<Member2> findAll() {
        return em.createQuery("select m from Member2 m", Member2.class).getResultList();
    }

    public long count() {
        return em.createQuery("select count(m) from Member2 m", Long.class).getSingleResult();
    }

    public List<Member2> findAllByUsernameAndAgeGreaterThan(String username, int age) {
        return em.createQuery("select m from Member2 m where m.username = :username and m.age > :age")
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
    public List<Member2> findByPage(int age, int offset, int limit) {
        //page 1 -> offset = 0, limit = 10
        //page 2 -> offset = 10, limit = 10
        //...

        return em.createQuery("select m from Member2 m where m.age = :age order by m.username desc")
            .setParameter("age", age)
            .setFirstResult(offset)   //어디서부터 가져올거?
            .setMaxResults(limit)   //몇개 가져올거?
            .getResultList();
    }

    //보통 두 개를 같이써줌
    public long totalCount(int age) {
        return em.createQuery("select count(m) from Member2 m where m.age = :age", Long.class)
            .setParameter("age" ,age)
            .getSingleResult();
    }

    /**
     * 순수 JPA의 벌크성 쿼리
     */
    public int bulkAgePlus(int age) {
        int resCount = em.createQuery("update Member2 m set m.age = m.age + 1 where m.age >= :age")
            .setParameter("age", age)
            .executeUpdate();
        return resCount;
    }
}
