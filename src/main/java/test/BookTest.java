package test;

import hu.soter.modell.Book;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class BookTest {


    @Test
    public void testBookConstructorsAndGetters(){
        Set<String> authors = Set.of("J.K. Rowling");
        Book book = new Book(1,"Harry Potter", 1997, 39.99, authors);

        assertEquals(1, book.getId());
        assertEquals("Harry Potter", book.getTitle());
        assertEquals(1997, book.getPublicationYear());
        assertEquals(39.99, book.getPrice(), 0.0001);
        assertEquals(authors, book.getAuthors());
    }

    @Test
    public void testToString() {
        Set<String> authors = Set.of("J.K. Rowling");
        Book book = new Book(1, "Harry Potter", 1997, 39.99, authors);
        String expected = "1;Harry Potter;J.K. Rowling;1997;39.99";

        assertEquals(expected, book.toString());
    }

    @Test
    public void testFromStringValid() {
        String bookData = "1;Harry Potter;J.K. Rowling;1997;39.99";
        Book book = Book.fromString(bookData);

        assertNotNull(book);
        assertEquals(1, book.getId());
        assertEquals("Harry Potter", book.getTitle());
        assertEquals(1997, book.getPublicationYear());
        assertEquals(39.99, book.getPrice(), 0.0001);
        assertEquals(Set.of("J.K. Rowling"), book.getAuthors());
    }

    @Test
    public void testFromStringInvalid() {
        String invalidData = "1;Harry Potter;1997;39.99"; // Hiányzik az authors
        Book book = Book.fromString(invalidData);

        assertNull(book);
    }
}

