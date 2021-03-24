package kolokviumski2code.Imenik;

import java.util.*;
import java.util.stream.Collectors;

//class DuplicateNumberException extends Exception {
//    String number;
//
//    public DuplicateNumberException(String number) {
//        super(String.format("Duplicate number: %s", number));
//    }
//
//}
//
//class Contact {
//    String name;
//    String number;
//
//    public Contact(String name, String number) {
//        this.name = name;
//        this.number = number;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getNumber() {
//        return number;
//    }
//
//    public void setNumber(String number) {
//        this.number = number;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Contact contact = (Contact) o;
//        return Objects.equals(number, contact.number);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(number);
//    }
//
//    public List<String> getSubNumbers() {
//        List<String> results = new ArrayList<>();
//        for (int i = 3; i < this.number.length(); i++) {
//            for (int j = 0; j < this.number.length() - i; j++) {
//                results.add(this.number.substring(j, j + i));
//            }
//        }
//        return results;
//    }
//}
//
//class PhoneBook {
//    Map<String, String> namesByPhoneNumber; //broj iminja
//    Map<String, Set<Contact>> contactsBySubnumber;
//    Map<String, Set<Contact>> contactsByName;
//
//    public PhoneBook() {
//        namesByPhoneNumber = new HashMap<>();
//        contactsBySubnumber = new HashMap<>();
//        contactsByName = new HashMap<>();
//    }
//
//    public void addContact(String name, String number) throws DuplicateNumberException {
//        Contact c = new Contact(name, number);
//
//        if (namesByPhoneNumber.containsKey(number)) {
//            throw new DuplicateNumberException(number);
//        }
//        namesByPhoneNumber.put(number, name);
//
//        contactsByName.putIfAbsent(number, new TreeSet<>());
//        contactsByName.get(number).add(c);
//
//        for (String subnumber : c.getSubNumbers()) {
//            contactsBySubnumber.putIfAbsent(number, new TreeSet<>());
//            contactsBySubnumber.get(number).add(c);
//        }
//
//
//    }
//
//    public void contactsByNumber(String subnum) {
//        contactsBySubnumber.get(subnum)
//                .stream()
//                .forEach(contact -> System.out.println(contact));
//    }
//
//    public void contactsByName(String number) {
//        contactsByName.get(number).stream()
//                .forEach(contact -> System.out.println(contact));
//    }
//}
class DuplicateNumberException extends Exception {
    String number;

    public DuplicateNumberException(String number) {
        super(String.format("Duplicate number: %s", number));
    }

}

class Contact implements Comparable<Contact> {

    private String name;
    private String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int compareTo(Contact o) {
        return this.name.compareTo(o.number);
    }

    @Override
    public String toString() {
        return String.format("%s %s",name,number);
    }
}

class PhoneBook {
    Set<Contact> contacts;
    Map<String, Set<Contact>> broeviByName;

    public PhoneBook() {
        contacts = new TreeSet<>(Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber));
        broeviByName = new HashMap<>();
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        Contact c = new Contact(name, number);
        if (contacts.contains(c)) {
            throw new DuplicateNumberException(number);
        }
        contacts.add(c);
        broeviByName.putIfAbsent(name, new TreeSet<>(Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber)));
        broeviByName.get(name).add(c);
    }

    public void contactsByNumber(String subnumber) {
        List<Contact> sodrzateli = new ArrayList<>();
        sodrzateli = contacts.stream()
                .filter(contact -> contact.getNumber().contains(subnumber))
                .collect(Collectors.toList());
        if (sodrzateli.size()==0) {
            System.out.println("NOT FOUND");
        } else {
            sodrzateli.forEach(System.out::println);
        }
    }

    public void contactsByName(String name) {
        if(!broeviByName.containsKey(name)){
            System.out.println("NOT FOUND");
        }
        else {
            broeviByName.get(name).forEach(contact -> System.out.println(contact));
        }
    }
}

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}
