package hu.soter.modell;

import java.util.Comparator;

public class BookComparators {

    public static class AuthorComparator implements Comparator<Book> {
        @Override
        public int compare(Book a1, Book a2) {
            String author1 = a1.getAuthors().iterator().next();
            String author2 = a2.getAuthors().iterator().next();
            return author1.compareToIgnoreCase(author2);
        }
    }

    public static class priceComparator implements Comparator<Book> {
        @Override
        public int compare(Book a1, Book a2){
            return Double.compare(a1.getPrice(), a2.getPrice());
        }
    }

    public static class YearComparator implements Comparator<Book> {
        @Override
        public int compare(Book a1, Book a2) {
            return Integer.compare(a1.getPublicationYear(), a2.getPublicationYear());
        }
    }
}
