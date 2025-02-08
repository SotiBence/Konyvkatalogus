package hu.soter.modell;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BookCatalog {
    //Könyvek listájának létrehozása
    public List<Book> books = new ArrayList<>();


    public void addBook(int id, String title, int publicationYear, double price,Set<String> authors){
        Book newBook = new Book(id, title, publicationYear, price, authors);
        books.add(newBook);
        System.out.println("könyv hozzáadva: " + title);
    }

    // Könyv eltávolitása ID alapján
    public boolean removeBookById(int id){
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
    public void listBooks(){
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

}
