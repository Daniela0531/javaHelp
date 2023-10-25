package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Person person = new Person(1L, "Qwert", "Qwerty");
        System.out.println(person.getId());
        System.out.println(person.getName());
        System.out.println(person.getLastName());

        Factory factory = new Factory();
        System.out.println(factory.toJson(person).json);
    }
}