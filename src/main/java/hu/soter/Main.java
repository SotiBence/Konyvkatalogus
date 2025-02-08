package hu.soter;

import hu.soter.dao.UserDAO;
import hu.soter.modell.*;
import hu.soter.security.AccessCoontrol;
import hu.soter.security.Role;
import hu.soter.security.User;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        BookCatalog catalog = new BookCatalog();
        BookManager manager = new BookManager();
        BookEXC bookEXC = new BookEXC();

        Scanner scanner = new Scanner(System.in);

        User admin = new User("AdminUser", Role.ADMIN);
        User user = new User("RegularUser", Role.USER);
        User guest = new User("GuestUser", Role.GUEST);

        // ADMIN hozzáad egy könyvet
        catalog.addBook(admin, 1, "Harry Potter", 1997, 39.99, Set.of("J.K. Rowling"));

        // USER próbál könyvet hozzáadni (NEM engedélyezett)
        catalog.addBook(user, 2, "The Hobbit", 1937, 25.99, Set.of("J.R.R. Tolkien"));

        // GUEST próbál könyvet törölni (NEM engedélyezett)
        catalog.removeBookById(guest, 1);

        // USER listázza a könyveket (ENGEDÉLYEZETT)
        catalog.listBooks(user);

        // ADMIN töröl egy könyvet
        catalog.removeBookById(admin, 1);


        // Könyvek hozzáadása
        manager.addBook(1, "Harry Potter", 1997, 25.99, Set.of("J.K. Rowling"));
        manager.addBook(2, "The Hobbit", 1937, 19.99, Set.of("J.R.R. Tolkien"));
        manager.addBook(3, "1984", 1949, 15.50, Set.of("George Orwell"));

        // Könyvek mentése fájlokba
        manager.saveToTextFile();
        manager.saveToBinaryFile();

        // Könyvek betöltése szöveges fájlból
        System.out.println("\n Betöltés szöveges fájlból:");
        manager.loadFromTextFile();
        manager.listBooks();

        // Könyvek betöltése bináris fájlból
        System.out.println("\n Betöltés bináris fájlból:");
        manager.loadFromBinaryFile();
        manager.listBooks();

        //Könyvek hozzáadása az adatbázishoz
        bookEXC.addBook(new hu.soter.modell.Book(0, "Harry Potter", 1997, 25.99, Set.of("J.K Rowling")));
        bookEXC.addBook((new hu.soter.modell.Book(0, "The Hobbit", 1937, 19.99, Set.of("J.R.R. Tolkien"))));

        System.out.println("\n Könyvek az adatbázisban:");
        List<Book> books = bookEXC.getAllBooks();
        for (Book book : books) {
            book.printInfo();
        }

        // Újra listázás a törlés után
        System.out.println("\n Frissített könyvlista:");
        books = bookEXC.getAllBooks();
        for (Book book : books) {
            book.printInfo();
        }


        System.out.println("\nKönyv törlése ID alapján (ID: 3)");
        boolean deleted = bookEXC.removeBookById(4);
        if (deleted){
            System.out.println("A könyv sikeresen törölve.");
        }else {
            System.out.println("Nem található könyv ezzel az ID-val.");
        }

        System.out.println("\nKönyv keresése ID alapján :");
        hu.soter.modell.Book book = bookEXC.getBookById(4);
        if (book != null) {
            book.printInfo();
        } else {
            System.out.println("Nem található könyv ezzel az ID-val.");
        }

        System.out.println("\nKönyvek keresése szerző alapján :");
        List<hu.soter.modell.Book > booksByAuthor = bookEXC.getBooksByAuthor("J.K Rowling");
        for (hu.soter.modell.Book b : booksByAuthor) {
            b.printInfo();
        }

        System.out.println("\nKönyvek keresése cím alapján :");
        List<hu.soter.modell.Book> booksByTitle = bookEXC.getBooksByTitle("The Hobbit");
        for (hu.soter.modell.Book b : booksByTitle) {
            b.printInfo();
        }

        System.out.println("\nKönyvek rendezése kiadási év szerint (növekvő)");
        List<hu.soter.modell.Book> sortedBooks = bookEXC.getBooksSortedByYear(true);
        for (hu.soter.modell.Book b : sortedBooks) {
            b.printInfo();
        }

        System.out.print("Felhasználónév: ");
        String username = scanner.nextLine();
        System.out.print("Jelszó: ");
        String password = scanner.nextLine();

        user = UserDAO.authenticate(username, password);
        if (user == null) {
            System.out.println("Hibás bejelentkezés!");
            return;
        }

        System.out.println("Bejelentkezés sikeres! Szerepkör: " + user.getRole());

        boolean running = true;
        while (running) {
            System.out.println("\nMenü:");
            System.out.println("1 - Könyv hozzáadása");
            System.out.println("2 - Könyv törlése");
            System.out.println("3 - Könyvek listázása");
            System.out.println("4 - Könyv keresése");
            System.out.println("5 - Mentés fájlba");
            System.out.println("6 - Betöltés fájlból");
            System.out.println("7 - Mentés adatbázisba");
            System.out.println("8 - Kilépés");
            System.out.print("Választás: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    if (AccessCoontrol.hasPermission(user, "ADD_BOOK")) {
                        System.out.print("Cím: ");
                        String title = scanner.nextLine();
                        System.out.print("Év: ");
                        int year = scanner.nextInt();
                        System.out.print("Ár: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Szerzők (vesszővel elválasztva): ");
                        Set<String> authors = Set.of(scanner.nextLine().split(","));
                        catalog.addBook(user, catalog.books.size() + 1, title, year, price, authors);
                    } else {
                        System.out.println("Nincs jogosultságod hozzáadni könyvet.");
                    }
                }
                case 2 -> {
                    if (AccessCoontrol.hasPermission(user, "DELETE_BOOK")) {
                        System.out.print("Könyv ID: ");
                        int id = scanner.nextInt();
                        catalog.removeBookById(user, id);
                    } else {
                        System.out.println("Nincs jogosultságod törölni könyvet.");
                    }
                }
                case 3 -> catalog.listBooks(user);
                case 4 -> {
                    System.out.print("Keresett cím: ");
                    String searchTitle = scanner.nextLine();
                    catalog.findBookByTitle(searchTitle);
                }
                case 5 -> manager.saveToTextFile();
                case 6 -> manager.loadFromTextFile();
                case 7 -> catalog.saveToDatabase();
                case 8 -> {
                    System.out.println("Kilépés...");
                    running = false;
                }
                default -> System.out.println("Érvénytelen választás.");
            }
        }



    }
}