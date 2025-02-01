package hu.soter.modell;

public abstract class Item {
    private int id;
    private String title;

    public interface CatalogItem{
        String getItemInfo();
        boolean matches(String keyword);
    }

    public Item(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
