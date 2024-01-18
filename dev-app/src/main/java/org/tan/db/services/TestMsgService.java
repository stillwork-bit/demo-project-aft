package org.tan.db.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tan.data.jpa.models.TestMsg;
import org.tan.repositories.TestMsgRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TestMsgService {

    private final TestMsgRepository testMsgRepository;

    public List<TestMsg> getTestMsgList() {
        return testMsgRepository.findAll();
    }

    public Optional<TestMsg> getTestMsgByEventId(String eventId) {
        return testMsgRepository.getTestMsgByEventId(eventId);
    }

    public void clean() {
        testMsgRepository.deleteAll();
    }
}
