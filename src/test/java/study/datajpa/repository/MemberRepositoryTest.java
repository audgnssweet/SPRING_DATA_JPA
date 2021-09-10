package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
@SpringBootTest
public class MemberRepositoryTest {

    @Test
    void test() {

    }

}
