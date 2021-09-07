package study.datajpa.entity;

import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 스프링 data에서 제공하는 EntityAuditing 사용하기 위해서
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {

    /**
     * @see org.springframework.data.domain.AuditorAware
     * 위 인터페이스를 return 하는데, 여기에 누가 수정했는지가 들어가면 된다. 여기서이제
     * SecurityContextHolder를 사용하거나 하면 된다.
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(UUID.randomUUID().toString());
    }
}
