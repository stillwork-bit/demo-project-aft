package org.tan.kafka.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class Helper {

    private ClassLoader classLoader = getClass().getClassLoader();

    public String readFile(String file) {
        log.info("Считывание файла в системе.\nНазвание файла = {}", file);
        String message = null;
        try (InputStream in = new FileInputStream(new File(classLoader.getResource(file).getFile()))) {
            message = IOUtils.toString(in, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    public int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
