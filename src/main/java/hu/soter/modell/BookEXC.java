package hu.soter.modell;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookEXC {
    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, authors, publication_year, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, String.join(",", book.getAuthors()));
            stmt.setInt(3, book.getPublicationYear());
            stmt.setDouble(4, book.getPrice());

            stmt.executeUpdate();
            System.out.println("Könyv hozzáadva: " + book.getTitle());

        } catch (SQLException e) {
            System.err.println("Hiba a könyv hozzáadásakor: " + e.getMessage());
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Set<String> authors = new HashSet<>(List.of(rs.getString("authors").split(",")));
                int publicationYear = rs.getInt("publication_year");
                double price = rs.getDouble("price");

                books.add(new Book(id, title, publicationYear, price, authors));
            }
        } catch (SQLException e) {
            System.err.println("Hiba a könyvek betöltésekor: " + e.getMessage());
        }
        return books;
    }

    public boolean removeBookById(int id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    conn.commit();
                    System.out.println("Könyv törölve: ID " + id);
                    return true;
                } else {
                    conn.rollback();
                    System.out.println("Nincs ilyen könyv ID: " + id);
                    return false;
                }
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Hiba a törlés közben, visszaállítás történt: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Adatbázis kapcsolat hiba: " + e.getMessage());
        }
        return false;
    }
}
