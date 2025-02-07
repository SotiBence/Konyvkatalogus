package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import hu.soter.modell.Book;
import hu.soter.modell.BookCatalog;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class BookCatalogTest {

    private BookCatalog catalog;

    @Before
    public void setUp(){
        catalog = new BookCatalog();
    }

    @Test
    public void testAddBook(){
        catalog.addBook(1, "Harry Potter", 1997, 39.99, new HashSet<>(Set.of("J.K. Rowling")));
        assertEquals(1, catalog.books.size());
    }

    @Test
    public void testRemoveBookByIdSuccess(){
        catalog.addBook(1, "Harry Potter", 1997, 39.99, Set.of("J.K. Rowling"));
        assertTrue(catalog.removeBookById(1));
        assertEquals(0, catalog.books.size());
    }

    @Test
    public void testRemoveBookByIdFail() {
        assertFalse(catalog.removeBookById(99)); // Nem létező ID
    }

    @Test
    public void testFindBookByTitleSuccess() {
        catalog.addBook(1, "Harry Potter", 1997, 39.99, new HashSet<>(Set.of("J.K. Rowling")));
        Book found = catalog.findBookByTitle("Harry Potter");

        assertNotNull(found);
        assertEquals("Harry Potter", found.getTitle());
    }

    @Test
    public void testFindBookByTitleFail() {
        Book found = catalog.findBookByTitle("Unknown Book");
        assertNull(found);
    }

    @Test
    public void testFindBooksByAuthorSuccess() {
        catalog.addBook(1, "Harry Potter", 1997, 39.99, Set.of("J.K. Rowling"));
        catalog.addBook(2, "Fantastic Beasts", 2016, 29.99, Set.of("J.K. Rowling"));

        List<Book> books = catalog.findBooksByAuthor("J.K. Rowling");

        assertEquals(2, books.size());
    }

    @Test
    public void testFindBooksByAuthorFail() {
        List<Book> books = catalog.findBooksByAuthor("Unknown Author");
        assertTrue(books.isEmpty());
    }
}

