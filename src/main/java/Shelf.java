import java.util.HashMap;
import java.util.Objects;

public class Shelf {
    public int SHELF_NUMBER_;
    public int SUBJECT_;

    private HashMap<Book, Integer> books;
    private int shelfNumber;
    private String subject;

    public Shelf() {

    }

    public Shelf(int shelfNumber, String subject) {
        this.shelfNumber = shelfNumber;
        this.subject = subject;
    }

















    public int getSHELF_NUMBER_() {
        return SHELF_NUMBER_;
    }

    public void setSHELF_NUMBER_(int SHELF_NUMBER_) {
        this.SHELF_NUMBER_ = SHELF_NUMBER_;
    }

    public int getSUBJECT_() {
        return SUBJECT_;
    }

    public void setSUBJECT_(int SUBJECT_) {
        this.SUBJECT_ = SUBJECT_;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return getShelfNumber() == shelf.getShelfNumber() && Objects.equals(getSubject(), shelf.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShelfNumber(), getSubject());
    }

    public String toString() {
        return shelfNumber + " : " + subject;
    }

}
