package hu.soter.modell;


import java.util.Set;

public class Book extends Item{
    private Set<String> authors;
    private int publicationYear;
    private double price;

    public Book() {
        super(0, "Unknown Title");
        this.publicationYear = 0;
        this.price = 0.0;
        this.authors = null;
    }

    public Book(int id, String title, int publicationYear, double price, Set<String> authors) {
        super(id, title);
        this.publicationYear = publicationYear;
        this.price = price;
        this.authors = authors;
    }

    public Book(int id, String title) {
        super(id, title);
    }
}
