package localhost.ppixeldemo.features.users.repository;

import localhost.ppixeldemo.features.users.entity.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneData, Long> {
}
