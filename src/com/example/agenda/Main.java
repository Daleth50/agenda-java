package com.example.agenda;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== AGENDA ===");
        boolean running = true;
        while (running) {
            printMenu();
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    addContact(agenda, scanner);
                    break;
                case "2":
                    System.out.println("\n=== LISTA DE CONTACTOS ===");
                    printContacts(agenda.getAllContacts());
                    break;
                case "3":
                    searchContacts(agenda, scanner);
                    break;
                case "4":
                    updateContact(agenda, scanner);
                    break;
                case "5":
                    deleteContact(agenda, scanner);
                    break;
                case "6":
                    running = false;
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opcion invalida. Ingresa un numero del 1 al 6.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nElige una opcion:");
        System.out.println("1. Agregar contacto");
        System.out.println("2. Ver todos los contactos");
        System.out.println("3. Buscar contacto");
        System.out.println("4. Actualizar contacto");
        System.out.println("5. Eliminar contacto");
        System.out.println("6. Salir");
        System.out.print("Opcion: ");
    }

    private static void addContact(Agenda agenda, Scanner scanner) {
        System.out.print("Nombre: ");
        String firstName = scanner.nextLine();
        System.out.print("Apellido: ");
        String lastName = scanner.nextLine();
        System.out.print("Correo electronico: ");
        String email = scanner.nextLine();
        System.out.print("Telefono: ");
        String phoneNumber = scanner.nextLine();

        Contact contact = agenda.addContact(firstName, lastName, email, phoneNumber);
        System.out.println("Contacto agregado correctamente. ID: " + contact.getId());
    }

    private static void searchContacts(Agenda agenda, Scanner scanner) {
        System.out.print("Texto a buscar (nombre, apellido, correo o telefono): ");
        String query = scanner.nextLine();

        System.out.println("\n=== RESULTADOS DE BUSQUEDA ===");
        printContacts(agenda.searchContacts(query));
    }

    private static void updateContact(Agenda agenda, Scanner scanner) {
        System.out.print("Ingresa el ID del contacto a actualizar: ");
        String id = scanner.nextLine();

        Contact existing = agenda.getContactById(id);
        if (existing == null) {
            System.out.println("No se encontro ningun contacto con ese ID.");
            return;
        }

        System.out.println("Deja un campo vacio para mantener el valor actual.");
        System.out.print("Nombre [" + existing.getFirstName() + "]: ");
        String firstName = scanner.nextLine();
        System.out.print("Apellido [" + existing.getLastName() + "]: ");
        String lastName = scanner.nextLine();
        System.out.print("Correo electronico [" + existing.getEmail() + "]: ");
        String email = scanner.nextLine();
        System.out.print("Telefono [" + existing.getPhoneNumber() + "]: ");
        String phoneNumber = scanner.nextLine();

        boolean updated = agenda.updateContact(
            id,
            firstName.isBlank() ? existing.getFirstName() : firstName,
            lastName.isBlank() ? existing.getLastName() : lastName,
            email.isBlank() ? existing.getEmail() : email,
            phoneNumber.isBlank() ? existing.getPhoneNumber() : phoneNumber
        );

        if (updated) {
            System.out.println("Contacto actualizado correctamente.");
        } else {
            System.out.println("No se pudo actualizar el contacto.");
        }
    }

    private static void deleteContact(Agenda agenda, Scanner scanner) {
        System.out.print("Ingresa el ID del contacto a eliminar: ");
        String id = scanner.nextLine();

        boolean deleted = agenda.removeContactById(id);
        if (deleted) {
            System.out.println("Contacto eliminado correctamente.");
        } else {
            System.out.println("No se encontro ningun contacto con ese ID.");
        }
    }

    private static void printContacts(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("No hay contactos para mostrar.");
            return;
        }

        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }
}
