package org.example.app.database;

import org.example.app.view.AppView;

import java.io.IOException;
// З’єднання (сесія) з конкретною базою даних.
// SQL-запити виконуються, а результати повертаються
// в контексті з’єднання.
import java.sql.Connection;
// Диспетчер драйверів, який відповідає за керування
// драйверами в базі даних.
import java.sql.DriverManager;
import java.sql.SQLException;
// Клас Properties представляє постійний набір властивостей.
// Властивості можна зберегти в потік або завантажити з потоку.
// Кожен ключ та відповідне йому значення в списку властивостей
// є рядком (String).
import java.util.Properties;

public class DBConn {

    public static Connection connect() {

        Properties props = new Properties();
        Connection conn = null;

        try {
            // Формуємо потік (Stream) даних з конфігураційного файлу
            // src/main/resources/db/jdbc.properties
            props.load(DBConn.class.getResourceAsStream("/db/jdbc.properties"));
            // Реалізуємо з'єднання з БД по конфігураційному файлу
            conn = DriverManager.getConnection(props.getProperty("dbDriver") +
                            props.getProperty("dbName"),
                    props.getProperty("username"), props.getProperty("password"));
        } catch (SQLException | IOException e) {
            // Виведення повідомлення про помилки роботи
            // з БД або конфігураційним файлом
            new AppView().getOutput(e.getMessage());
        }
        return conn;
    }
}