package lv.javaguru.chatbot.core.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import lv.javaguru.chatbot.core.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Override
	<S extends User> S save(S entity);

	@Override
	void delete(User entity);

	Optional<User> findByTelegramId(String telegramId);

}
