package hu.soter.modell;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BookManager {
    private List<Book> books;
    private final String TEXT_FILE = "books.txt";
    private final String BINARY_FILE = "books.dat";

    public BookManager() {
        this.books = new ArrayList<>();
    }

    public void addBook(int id, String title, int publicationYear, double price, Set<String> authors){
        books.add(new Book(id, title, publicationYear, price, authors));
    }

    public void listBooks(){
        if (books.isEmpty()){
            System.out.println("Nincsenek könyvek a listában.");
            return;
        }
        System.out.println("\n Könyvek listája:");
        for (Book book : books) {
            book.printInfo();
        }
    }

    public void saveToTextFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter((TEXT_FILE)))){
            for (Book book : books) {
                writer.write(book.toString());
                writer.newLine();
            }
        }catch (IOException e){
            System.out.println("Hiba a fájl mentése közben: " + e.getMessage());
        }
    }

    public void loadFromTextFile() {
        books.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEXT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Book book = Book.fromString(line);
                if (book != null) {
                    books.add(book);
                }
            }
        } catch (IOException e) {
            System.err.println("Hiba a fájl betöltése közben: " + e.getMessage());
        }
    }
    public void saveToBinaryFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(BINARY_FILE))) {
            out.writeObject(books);
        } catch (IOException e) {
            System.err.println("Hiba a fájl mentése közben: " + e.getMessage());
        }
    }

    public void loadFromBinaryFile() {
        books.clear();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(BINARY_FILE))) {
            books = (List<Book>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Hiba a fájl betöltése közben: " + e.getMessage());
        }
    }
}
