package spring.boot.learn.protobuf.demo;

import spring.boot.learn.protobuf.demo.protocol.AddressBookProtos.AddressBook;
import spring.boot.learn.protobuf.demo.protocol.AddressBookProtos.Person;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/1/16
 * @since Jdk 1.8
 */
public class ReadMessage {
    public static void main(String[] args) throws IOException {

        args =  new String[1];
        args[0] = "D:\\temp\\person.txt";

        if (args.length != 1) {
            System.err.println("Usage:  ListPeople ADDRESS_BOOK_FILE");
            System.exit(-1);
        }

        // Read the existing address book.
        AddressBook addressBook =
                AddressBook.parseFrom(new FileInputStream(args[0]));

        Print(addressBook);

    }

    // Iterates though all people in the AddressBook and prints info about them.
    static void Print(AddressBook addressBook) {
        for (Person person: addressBook.getPeopleList()) {
            System.out.println("Person ID: " + person.getId());
            System.out.println("  Name: " + person.getName());
            if (person.getEmail() != null) {
                System.out.println("  E-mail address: " + person.getEmail());
            }

            for (Person.PhoneNumber phoneNumber : person.getPhonesList()) {
                switch (phoneNumber.getType()) {
                    case MOBILE:
                        System.out.print("  Mobile phone #: ");
                        break;
                    case HOME:
                        System.out.print("  Home phone #: ");
                        break;
                    case WORK:
                        System.out.print("  Work phone #: ");
                        break;
                }
                System.out.println(phoneNumber.getNumber());
            }
        }
    }
}
