package hu.soter.modell;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookEXC {
    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, publication_year, price, authors) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getPublicationYear());
            stmt.setDouble(3, book.getPrice());
            stmt.setString(4, String.join(",", book.getAuthors()));

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
                int publication_year = rs.getInt("publication_year");
                double price = rs.getDouble("price");
                Set<String> authors = new HashSet<>(List.of(rs.getString("authors").split(",")));

                books.add(new Book(id, title, publication_year, price, authors));
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

    public Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String title = rs.getString("title");
                int publicationYear = rs.getInt("publication_year");
                double price = rs.getDouble("price");
                Set<String> authors = new HashSet<>(List.of(rs.getString("authors").split(",")));

                return new Book(id, title, publicationYear, price, authors);
            }
        } catch (SQLException e) {
            System.err.println("Hiba a könyv lekérésekor: " + e.getMessage());
        }
        return null;
    }

    public List<Book> getBooksByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE authors LIKE ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + author + "%"); // LIKE keresés
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int publicationYear = rs.getInt("publication_year");
                double price = rs.getDouble("price");
                Set<String> authors = new HashSet<>(List.of(rs.getString("authors").split(",")));

                books.add(new Book(id, title, publicationYear, price, authors));
            }
        } catch (SQLException e) {
            System.err.println("Hiba a könyvek lekérésekor: " + e.getMessage());
        }
        return books;
    }

    public List<Book> getBooksByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + title + "%"); // LIKE keresés
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String bookTitle = rs.getString("title");
                int publicationYear = rs.getInt("publication_year");
                double price = rs.getDouble("price");
                Set<String> authors = new HashSet<>(List.of(rs.getString("authors").split(",")));

                books.add(new Book(id, bookTitle, publicationYear, price, authors));
            }
        } catch (SQLException e) {
            System.err.println("Hiba a könyvek lekérésekor: " + e.getMessage());
        }
        return books;
    }

    public List<Book> getBooksSortedByYear(boolean ascending) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY publication_year " + (ascending ? "ASC" : "DESC");

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int publicationYear = rs.getInt("publication_year");
                double price = rs.getDouble("price");
                Set<String> authors = new HashSet<>(List.of(rs.getString("authors").split(",")));

                books.add(new Book(id, title, publicationYear, price, authors));
            }
        } catch (SQLException e) {
            System.err.println("Hiba a könyvek rendezésekor: " + e.getMessage());
        }
        return books;
    }
}
