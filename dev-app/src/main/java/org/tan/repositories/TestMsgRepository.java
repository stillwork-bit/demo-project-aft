package org.tan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tan.data.jpa.models.TestMsg;

import java.util.Optional;

@Repository
public interface TestMsgRepository extends JpaRepository<TestMsg, Long> {
    Optional<TestMsg> getTestMsgByEventId(String eventId);
}
