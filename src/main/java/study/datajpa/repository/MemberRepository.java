package study.datajpa.repository;

import com.dokitlist.dooyaho.study.dto.MemberDto;
import com.dokitlist.dooyaho.study.entity.Member2;
import java.util.List;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member2, Long>, MemberRepositoryCustom {

    @Query("select m from Member2 m where m.username = :username and m.age = :age")
    List<Member2> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member2 m")
    List<String> findUsernames();

    @Query("select new com.dokitlist.dooyaho.study.dto.MemberDto(m.id, m.username, t.name) from Member2 m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member2 m where m.username in :names")
    List<Member2> findByNames(@Param("names") List<String> names);

    //pageRequest 객체를 넘겨주면 된다.
    //아래처럼 실무에서는 아래처럼 count query를 분리하는 경우가 많음
    @Query(value = "select m from Member2 m left join m.team t",
        countQuery = "select count(m) from Member2 m")
    Page<Member2> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)  //이 어노테이션이 있어야 jpa의 executeUpdate 를 실행한다.
    @Query("update Member2 m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member2 m join m.team t")
    List<Member2> findAllOriginal(); //N+1 생김

    @Query("select m from Member2 m join fetch m.team t")
    List<Member2> findAllFetch();    //fetch join으로 해결

    @Query("select m, m.team from Member2 m join m.team t")
    List<Member2> findAllNonFetchButProjection();    //N+1은 안생기나, team까지 같이 가져오는데 Member만 받는 이상한 점

    @EntityGraph(attributePaths = {"team"})
    List<Member2> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member2 m")
    List<Member2> findAllWithQuery();

    @EntityGraph(attributePaths = {"team"})
    List<Member2> findMemberByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member2 findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)   // 쿼리 마지막에 for update가 붙는다.
    List<Member2> findLockByUsername(String username);

}
