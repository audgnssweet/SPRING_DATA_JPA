package study.datajpa.repository;

import com.dokitlist.dooyaho.study.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
