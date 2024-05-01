package org.example.app.controller;

import org.example.app.service.ContactService;
import org.example.app.utils.AppStarter;
import org.example.app.view.*;


public class ContactController {

    ContactService service = new ContactService();

    public void create() {
        ContactCreateView view = new ContactCreateView();
        view.getOutput(service.create(view.getData()));
        AppStarter.startApp();
    }

    public void read() {
        ContactReadView view = new ContactReadView();
        view.getOutput(service.read());
        AppStarter.startApp();
    }

    public void update() {
        ContactUpdateView view = new ContactUpdateView();
        view.getOutput(service.update(view.getData()));
        AppStarter.startApp();
    }

    public void delete() {
        ContactDeleteView view = new ContactDeleteView();
        view.getOutput(service.delete(view.getData()));
        AppStarter.startApp();
    }

    public void readById() {
        ContactReadByIdView view = new ContactReadByIdView();
        view.getOutput(service.readById(view.getData()));
        AppStarter.startApp();
    }
}
