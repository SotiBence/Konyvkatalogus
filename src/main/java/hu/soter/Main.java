package hu.soter;

import hu.soter.modell.BookEXC;

import java.awt.print.Book;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        BookEXC bookEXC = new BookEXC();
        bookEXC.addBook(new hu.soter.modell.Book(0, "1984", 1949, 15.50, Set.of("George Orwell")));
        bookEXC.addBook((new hu.soter.modell.Book(0, "The Hobbit", 1937, 19.99, Set.of("J.R.R. Tolkien"))));

    }
}