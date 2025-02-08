package hu.soter.modell;

import hu.soter.dao.DatabaseManager;
import hu.soter.security.AccessCoontrol;
import hu.soter.security.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BookCatalog {
    //Könyvek listájának létrehozása
    public List<Book> books = new ArrayList<>();


    public void addBook(User user, int id, String title, int publicationYear, double price, Set<String> authors){
        if (!AccessCoontrol.canAddBook(user)) {
            System.out.println("Hozzáférés megtagadva: nincs jogosultság könyv hozzáadásához.");
            return;
        }
        Book newBook = new Book(id, title, publicationYear, price, authors);
        books.add(newBook);
        System.out.println("könyv hozzáadva: " + title);
    }

    // Könyv eltávolitása ID alapján
    public boolean removeBookById(User user, int id){
        if (!AccessCoontrol.canRemoveBook(user)) {
            System.out.println("Hozzáférés megtagadva: nincs jogosultság könyv törlésére.");
            return false;
        }
        for (Book book : books){
            if (book.getId() == id){
                books.remove(book);
                System.out.println("könyv törölve: " + book.getTitle());
                return true;
            }
        }
        System.out.println("Nincs ilyen Id-jű könyv: " + id);
        return false;
    }
    // A lista tartalmának kiírása
    public void listBooks(User user){
        if (!AccessCoontrol.canViewBooks(user)) {
            System.out.println("Hozzáférés megtagadva: nincs jogosultság könyvek listázására.");
            return;
        }
        if (books.isEmpty()){
            System.out.println("Nincsenek könyvek a listában.");
            return;
        }
        System.out.println("\nkönyvek listája:");
        for (Book book : books) {
            book.printInfo();
        }
    }

    // Könyv keresése cim alapján
    public Book findBookByTitle(String title){
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)){
                return book;
            }
        }
        return null;
    }

    // Könyv keresése szerző alapján
    public List<Book> findBooksByAuthor(String author){
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthors().contains(author)){
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public void saveToDatabase() {
        String sql = "INSERT INTO books (title, publication_year, price, authors) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Book book : books) {
                stmt.setString(1, book.getTitle());
                stmt.setInt(2, book.getPublicationYear());
                stmt.setDouble(3, book.getPrice());
                stmt.setString(4, String.join(",", book.getAuthors()));

                stmt.addBatch(); // Több beszúrás egyszerre
            }
            stmt.executeBatch(); // Batch végrehajtása

            System.out.println("Könyvek sikeresen mentve az adatbázisba!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Hiba történt a könyvek adatbázisba mentése során.");
        }
    }

}
