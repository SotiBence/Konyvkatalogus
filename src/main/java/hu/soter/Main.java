package hu.soter;

import hu.soter.modell.Book;
import hu.soter.modell.BookEXC;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        BookEXC bookEXC = new BookEXC();
//        bookEXC.addBook(new hu.soter.modell.Book(0, "Harry Potter", 1997, 25.99, Set.of("J.K Rowling")));
//        bookEXC.addBook((new hu.soter.modell.Book(0, "The Hobbit", 1937, 19.99, Set.of("J.R.R. Tolkien"))));

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
    }
}