package job.data.domain.user.domain.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByPassword(String password);

    Optional<User> findByLoginId(String password);
}
