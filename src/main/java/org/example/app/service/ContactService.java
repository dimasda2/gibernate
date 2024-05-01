package org.example.app.service;

import org.example.app.entity.Contact;
import org.example.app.exceptions.ContactException;
import org.example.app.repository.impl.ContactRepository;
import org.example.app.utils.Constants;
import org.example.app.entity.ContactMapper;
import org.example.app.utils.ContactValidator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
public class ContactService {

    ContactRepository repository = new ContactRepository();

    public String create(Map<String, String> data) {
        Map<String, String> errors =
                new ContactValidator().validateContactData(data);
        if (!errors.isEmpty()) {
            try {
                throw new ContactException("Check inputs", errors);
            } catch (ContactException e) {
                return e.getErrors(errors);
            }
        }
        return repository.create(new ContactMapper().mapData(data));
    }

    public String read() {
        // Отримуємо дані
        Optional<List<Contact>> optional = repository.read();
        // Якщо Optional не містить null, формуємо виведення.
        // Інакше повідомлення про відсутність даних.
        if (optional.isPresent()) {
            // Отримуємо колекцію з Optional
            List<Contact> list = optional.get();
            // Якщо колекція не порожня, формуємо виведення.
            // Інакше повідомлення про відсутність даних.
            if (!list.isEmpty()) {
                AtomicInteger count = new AtomicInteger(0);
                StringBuilder sb = new StringBuilder();
                list.forEach((contact) ->
                        sb.append(count.incrementAndGet())
                                .append(") ")
                                .append(contact.toString())
                );
                return sb.toString();
            } else return Constants.DATA_ABSENT_MSG;
        } else return Constants.DATA_ABSENT_MSG;
    }

    public String update(Map<String, String> data) {
        Map<String, String> errors =
                new ContactValidator().validateContactData(data);
        if (!errors.isEmpty()) {
            try {
                throw new ContactException("Check inputs", errors);
            } catch (ContactException e) {
                return e.getErrors(errors);
            }
        }
        return repository.update(new ContactMapper().mapData(data));
    }

    public String delete(Map<String, String> data) {
        Map<String, String> errors =
                new ContactValidator().validateContactData(data);
        if (!errors.isEmpty()) {
            try {
                throw new ContactException("Check inputs", errors);
            } catch (ContactException e) {
                return e.getErrors(errors);
            }
        }
        return repository.delete(new ContactMapper().mapData(data).getId());
    }

    public String readById(Map<String, String> data) {
        Map<String, String> errors =
                new ContactValidator().validateContactData(data);
        if (!errors.isEmpty()) {
            try {
                throw new ContactException("Check inputs", errors);
            } catch (ContactException e) {
                return e.getErrors(errors);
            }
        }
        // Отримуємо дані
        Optional<Contact> optional =
                repository.readById(Long.parseLong(data.get("id")));
        // Якщо Optional не містить null, формуємо виведення.
        // Інакше повідомлення про відсутність даних.
        if (optional.isPresent()) {
            // Отримуємо об'єкт з Optional
            Contact contact = optional.get();
            return contact.toString();
        } else return Constants.DATA_ABSENT_MSG;
    }
}