package org.example.system.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemPropertiesTests {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream("lightning-module-info.properties");
        properties.load(resourceAsStream);

        properties.propertyNames().asIterator().forEachRemaining(System.out::println);
    }
    
}
