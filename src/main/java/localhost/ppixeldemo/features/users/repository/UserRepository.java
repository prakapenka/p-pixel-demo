package localhost.ppixeldemo.features.users.repository;

import localhost.ppixeldemo.features.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
