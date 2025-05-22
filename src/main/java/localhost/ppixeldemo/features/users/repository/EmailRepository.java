package localhost.ppixeldemo.features.users.repository;

import localhost.ppixeldemo.features.users.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailData, Long> {
}
