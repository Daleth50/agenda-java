package com.example.agenda;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Agenda {
    private final List<Contact> contacts = new ArrayList<>();

    public Contact addContact(String firstName, String lastName, String email, String phoneNumber) {
        Contact contact = new Contact(
            UUID.randomUUID().toString(),
            clean(firstName),
            clean(lastName),
            clean(email),
            clean(phoneNumber)
        );
        contacts.add(contact);
        return contact;
    }

    public boolean removeContactById(String id) {
        return contacts.removeIf(contact -> contact.getId().equals(id));
    }

    public boolean updateContact(String id, String firstName, String lastName, String email, String phoneNumber) {
        Contact contact = getContactById(id);
        if (contact == null) {
            return false;
        }

        contact.setFirstName(clean(firstName));
        contact.setLastName(clean(lastName));
        contact.setEmail(clean(email));
        contact.setPhoneNumber(clean(phoneNumber));
        return true;
    }

    public List<Contact> searchContacts(String query) {
        String text = clean(query).toLowerCase();
        if (text.isEmpty()) {
            return getAllContacts();
        }

        return contacts.stream()
            .filter(contact -> containsIgnoreCase(contact.getFirstName(), text)
                || containsIgnoreCase(contact.getLastName(), text)
                || containsIgnoreCase(contact.getEmail(), text)
                || containsIgnoreCase(contact.getPhoneNumber(), text))
            .collect(Collectors.toList());
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }

    public Contact getContactById(String id) {
        return contacts.stream()
            .filter(contact -> contact.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    private boolean containsIgnoreCase(String value, String queryLowerCase) {
        return value != null && value.toLowerCase().contains(queryLowerCase);
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
