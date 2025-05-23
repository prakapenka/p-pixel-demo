package localhost.ppixeldemo.features.phone.repository;

import localhost.ppixeldemo.features.phone.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {}
