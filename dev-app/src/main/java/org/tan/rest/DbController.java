package org.tan.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tan.data.jpa.models.TestMsg;
import org.tan.db.services.TestMsgService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/db")
public class DbController {

    @Autowired
    public TestMsgService testMsgService;


    @DeleteMapping("/clean")
    public ResponseEntity<Void> cleanDb() {
        log.info("===== Запрос /api/db/clean для удаления данных  =====");
        try {
            testMsgService.clean();
            log.info("===== Запрос /api/db/clean выполнен успешно =====");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("==== Ошибка. Не удалить записи =====");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getAllTestMsg")
    public ResponseEntity<List<TestMsg>> getAllTestMsg() {
        log.info("===== Запрос всех записей в таблице TestMsg =====");
        List<TestMsg> testMsgList = testMsgService.getTestMsgList();
        ResponseEntity<List<TestMsg>> responseEntity;

        if (testMsgList.isEmpty()) {
            log.error("==== Ошибка. Не удалось найти записи в таблице TestMsg =====");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ошибка. Таблица TestMsg пуста");
        } else {
            log.info("==== Найдены записи в таблице TestMsg =====");
            responseEntity = ResponseEntity.ok(testMsgList);
        }
        return responseEntity;
    }

    @GetMapping("/getTestMsgByEventId")
    public ResponseEntity<Optional<TestMsg>> getTestMsgByEventId(@RequestParam String id) {
        log.info("===== Запрос TestMsg с eventId = {} =====", id);
        Optional<TestMsg> testMsg = testMsgService.getTestMsgByEventId(id);
        ResponseEntity<Optional<TestMsg>> responseEntity;

        if (testMsg.isEmpty()) {
            log.error("==== Ошибка. Совпадение не найдено в таблице TestMsg с eventId = {} =====", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Ошибка. TestMsg не найден в таблице с id = %s", id));
        } else {
            log.info("==== Найдено совпадение в таблице TestMsg = {}=====", testMsg.get());
            responseEntity = ResponseEntity.ok(testMsg);
        }
        return responseEntity;
    }
}
