package test;

import hu.soter.security.Role;
import hu.soter.security.User;
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
    private User admin;
    private User user;
    private User guest;

    @Before
    public void setUp(){
        catalog = new BookCatalog();
        admin = new User("AdminUser", Role.ADMIN);
        user = new User("RegularUser", Role.USER);
        guest = new User("GuestUser", Role.GUEST);
    }

    @Test
    public void testAdminCanAddBook(){
        catalog.addBook(admin,1, "Harry Potter", 1997, 39.99, new HashSet<>(Set.of("J.K. Rowling")));
        assertEquals(1, catalog.books.size());
    }

    @Test
    public void testUserCannotAddBook() {
        catalog.addBook(user, 2, "The Hobbit", 1937, 25.99, Set.of("J.R.R. Tolkien"));
        assertEquals(0, catalog.books.size());
    }

    @Test
    public void testGuestCannotAddBook() {
        catalog.addBook(guest, 3, "1984", 1949, 19.99, Set.of("George Orwell"));
        assertEquals(0, catalog.books.size());
    }

    @Test
    public void testAdminCanRemoveBook() {
        catalog.addBook(admin, 4, "Brave New World", 1932, 15.99, Set.of("Aldous Huxley"));
        assertTrue(catalog.removeBookById(admin, 4));
        assertEquals(0, catalog.books.size());
    }

    @Test
    public void testRemoveBookByIdSuccess(){
        catalog.addBook(admin,1, "Harry Potter", 1997, 39.99, Set.of("J.K. Rowling"));
        assertTrue(catalog.removeBookById(admin,1));
        assertEquals(0, catalog.books.size());
    }

    @Test
    public void testRemoveBookByIdFail() {
        assertFalse(catalog.removeBookById(admin,99)); // Nem létező ID
    }

    @Test
    public void testUserCanListBooks() {
        catalog.addBook(admin, 7, "The Great Gatsby", 1925, 10.99, Set.of("F. Scott Fitzgerald"));
        catalog.listBooks(user);
    }

    // ✅ GUEST tud könyveket listázni
    @Test
    public void testGuestCanListBooks() {
        catalog.addBook(admin, 8, "Crime and Punishment", 1866, 12.99, Set.of("Fyodor Dostoevsky"));
        catalog.listBooks(guest);
    }

    @Test
    public void testFindBookByTitleSuccess() {
        catalog.addBook(admin,1, "Harry Potter", 1997, 39.99, new HashSet<>(Set.of("J.K. Rowling")));
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
        catalog.addBook(admin,1, "Harry Potter", 1997, 39.99, Set.of("J.K. Rowling"));
        catalog.addBook(admin,2, "Fantastic Beasts", 2016, 29.99, Set.of("J.K. Rowling"));

        List<Book> books = catalog.findBooksByAuthor("J.K. Rowling");

        assertEquals(2, books.size());
    }

    @Test
    public void testFindBooksByAuthorFail() {
        List<Book> books = catalog.findBooksByAuthor("Unknown Author");
        assertTrue(books.isEmpty());
    }
}

