package helper;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ReadSystemVariables {

    private static Config config;

    public static String readParam(String nameParam) {
        if (null == config) {
            config = ConfigFactory.load(System.getenv("ENV_CONFIG_FILE"));
        }
        return config.getString(nameParam);
    }
}
