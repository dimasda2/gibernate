package org.example.app.entity;

public class Contact {
    private Long id;
    private String name;
    private String email;

    // Конструктор без параметрів (потрібний для Hibernate)
    public Contact(long id, String name, String email) {

    }

    // Конструктор з параметрами
    public Contact() {
        this.name = name;
        this.email = email;
    }

    // Гетери та сетери для id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Гетери та сетери для імені
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Гетери та сетери для електронної пошти
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}