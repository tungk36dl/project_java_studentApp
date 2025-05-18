package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("Không tìm thấy file cấu hình application.properties");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Lỗi khi đọc file cấu hình", ex);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
