package hu.soter.modell;

import java.io.Serializable;
import java.util.Set;

public class Book extends Item implements Serializable {
    private static final long sealVersionUID = 1L;
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

    public void printInfo() {
        System.out.println("ID: " + getId() +
                ", Title: " + getTitle() +
                ", Authors: " + (authors != null ? authors : "No authors") +
                ", Year: " + publicationYear +
                ", Price: $" + price);
    }

    public Set<String> getAuthors() {
        return authors;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return getId() +
                ";" + getTitle() +
                ";" + String.join(",", authors) +
                ";" + publicationYear +
                ";" + price;
    }

    public static Book fromString(String line) {
        String[] parts = line.split(";");
        if (parts.length != 5) return null;
        int id = Integer.parseInt(parts[0]);
        String title = parts[1];
        Set<String> authors = Set.of(parts[2].split(","));
        int publicationYear = Integer.parseInt(parts[3]);
        double price = Double.parseDouble(parts[4]);
        return new Book(id, title, publicationYear, price, authors);
    }
}
