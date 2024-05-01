package org.example.app.repository.impl;

import org.example.app.database.DBConn;
import org.example.app.entity.Contact;
import org.example.app.repository.AppRepository;
import org.example.app.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class ContactRepository implements AppRepository<Contact> {

    private final static String TABLE_CONTACTS = "contacts";

    @Override
    public String create(Contact contact) {
        // SQL-запит.
        // ? - заповнювач (placeholder) для параметра. Навіщо?
        // Захист від SQL-ін'єкцій.
        // Ефективність. Коли використовуємо підготовлені оператори (PreparedStatement),
        // базі даних не потрібно щоразу аналізувати/компілювати SQL-запит.
        // Використовується шаблон та просто підставляються в нього значення.
        String sql = "INSERT INTO " + TABLE_CONTACTS +
                " (firstName, lastName, phone, email) VALUES(?, ?, ?, ?)";
        // PreparedStatement - підготовлений вираз (оператор), щоб уникнути SQL-ін'єкцій
        try (PreparedStatement pstmt = DBConn.connect().prepareStatement(sql)) {
            // Формування конкретних значень для певного заповнювача
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getEmail());
            // Виконання SQL-запиту
            pstmt.executeUpdate();
            // Повернення повідомлення при безпомилковому
            // виконанні SQL-запиту
            return Constants.DATA_INSERT_MSG;
        } catch (SQLException e) {
            // Повернення повідомлення про помилку роботи з БД
            return e.getMessage();
        }
    }

    @Override
    public Optional<List<Contact>> read() {
        try (Statement stmt = DBConn.connect().createStatement()) {
            // Колекція-контейнер для даних, які читаються з БД
            List<Contact> list = new ArrayList<>();
            // SQL-запит
            String sql = "SELECT id, Name, email FROM "
                    + TABLE_CONTACTS;
            // Отримання набору даних з БД через виконання SQL-запиту
            ResultSet rs = stmt.executeQuery(sql);
            // Наповнення колекції-контейнера об'єктами з БД
            while (rs.next()) {
                list.add(new Contact(
                                rs.getLong("id"),
                                rs.getString("Name"),
                                rs.getString("email")
                        )
                );
            }
            // Повертаємо Optional-контейнер з колецією даних
            return Optional.of(list);
        } catch (SQLException e) {
            // Якщо помилка повертаємо порожній Optional-контейнер
            return Optional.empty();
        }
    }

    @Override
    public String update(Contact contact) {
        // Спершу перевіряємо наявність об'єкта в БД за таким id.
        // Якщо ні, повертаємо повідомлення про відсутність таких даних,
        // інакше оновлюємо відповідний об'єкт в БД
        if (readById(contact.getId()).isEmpty()) {
            return Constants.DATA_ABSENT_MSG;
        } else {
            // SQL-запит.
            // ? - заповнювач (placeholder) для параметра. Навіщо?
            // Захист від SQL-ін'єкцій.
            // Ефективність. Коли використовуємо підготовлені оператори
            // (PreparedStatement),
            // базі даних не потрібно щоразу аналізувати/компілювати SQL-запит.
            // Використовується шаблон та просто підставляються в нього значення.
            String sql = "UPDATE " + TABLE_CONTACTS +
                    " SET firstName = ?, lastName = ?, phone = ?, email = ?" +
                    " WHERE id = ?";
            // PreparedStatement - підготовлений вираз, щоб уникнути SQL-ін'єкцій
            try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
                // Формування конкретних значень для певного заповнювача
                pst.setString(1, contact.getName());
                pst.setString(2, contact.getEmail());
                pst.setLong(3, contact.getId());
                // Виконання SQL-запиту
                pst.executeUpdate();
                // Повернення повідомлення при безпомилковому
                // виконанні SQL-запиту
                return Constants.DATA_UPDATE_MSG;
            } catch (SQLException e) {
                // Повернення повідомлення про помилку роботи з БД
                return e.getMessage();
            }
        }
    }

    @Override
    public String delete(Long id) {
        // Спершу перевіряємо наявність такого id в БД.
        // Якщо ні, повертаємо повідомлення про відсутність
        // таких даних в БД, інакше видаляємо відповідний об'єкт
        // із БД.
        if (!isIdExists(id)) {
            return Constants.DATA_ABSENT_MSG;
        } else {
            // SQL-запит.
            // ? - заповнювач (placeholder) для параметра. Навіщо?
            // Захист від SQL-ін'єкцій.
            // Ефективність. Коли використовуємо підготовлені оператори
            // (PreparedStatement), базі даних не потрібно щоразу
            // аналізувати/компілювати SQL-запит.
            // Використовується шаблон та просто підставляються в нього
            // значення.
            String sql = "DELETE FROM " + TABLE_CONTACTS +
                    " WHERE id = ?";
            // PreparedStatement - підготовлений вираз (оператор),
            // щоб уникнути SQL-ін'єкцій
            try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
                // Формування конкретних значень для певного заповнювача
                pst.setLong(1, id);
                // Виконання SQL-запиту
                pst.executeUpdate();
                // Повернення повідомлення при безпомилковому
                // виконанні SQL-запиту
                return Constants.DATA_DELETE_MSG;
            } catch (SQLException e) {
                // Повернення повідомлення про помилку роботи з БД
                return e.getMessage();
            }
        }
    }

    @Override
    public Optional<Contact> readById(Long id) {
        // SQL-запит.
        // ? - заповнювач (placeholder) для параметра. Навіщо?
        // Захист від SQL-ін'єкцій.
        // Ефективність. Коли використовуємо підготовлені оператори (PreparedStatement),
        // базі даних не потрібно щоразу аналізувати/компілювати SQL-запит.
        // Використовується шаблон та просто підставляються в нього значення.
        String sql = "SELECT id, Name, Name, email FROM "
                + TABLE_CONTACTS + " WHERE id = ?";
        try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            Contact contact = new Contact(
                    rs.getLong("id"),
                    rs.getString("Name"),
                    rs.getString("email")
            );
            // Повертаємо Optional-контейнер з об'єктом
            return Optional.of(contact);
        } catch (SQLException e) {
            // Якщо помилка або такого об'єкту немає в БД,
            // повертаємо порожній Optional-контейнер
            return Optional.empty();
        }
    }

    // Перевірка наявності певного id у БД
    private boolean isIdExists(Long id) {
        String sql = "SELECT COUNT(id) FROM " + TABLE_CONTACTS +
                " WHERE id = ?";
        try {
            PreparedStatement pst = DBConn.connect().prepareStatement(sql);
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                // Очікуємо лише один результат
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}
