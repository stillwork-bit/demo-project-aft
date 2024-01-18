package api.rest.variant1.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UtilStep<T> {

    private ClassLoader classLoader = getClass().getClassLoader();

    @Step("Сериализация объекта")
    public <T> T toObjectMapper(Class<T> clazz, String text) {
        ObjectMapper mapper = new ObjectMapper();
        T obj = null;
        try {
            obj = mapper.readValue(text, clazz);
        } catch (JsonProcessingException e) {
            log.error("===== ОШИБКА. Не удалось собрать объект =====");
            e.printStackTrace();
        }
        return obj;
    }

    @Step("Сериализация List объектов")
    public List<T> listObjectMapper(Class<T> clazz, String text) {
        ObjectMapper mapper = new ObjectMapper();
        List<T> listObject = new ArrayList<>();
        try {
            listObject = mapper.readValue(text, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            log.error("===== ОШИБКА. Не удалось собрать List объектов =====");
            e.printStackTrace();
        }
        return listObject;
    }

    @Step("Считывание файла")
    public <T> T readFileAndSetObject(Class<T> clazz) {
        log.info("===== Старт чтения файла =====");
        String message = null;
        String fileName = null;
        File file = null;

        try {
            file = new File(classLoader.getResource("json/pet.json").getFile());
        } catch (Exception e) {
            log.error("===== ОШИБКА. Файл не найден =====");
            e.printStackTrace();
        }

        try (InputStream in = new FileInputStream(file)) {
            message = IOUtils.toString(in, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("===== ОШИБКА. Не удалось прочитать файл =====");
            e.printStackTrace();
        }
        return toObjectMapper(clazz, message);
    }
}
