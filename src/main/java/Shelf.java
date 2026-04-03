import Utilities.Code;
import java.util.HashMap;
import java.util.Objects;

public class Shelf {
    public int SHELF_NUMBER_;
    public int SUBJECT_;

    private HashMap<Book, Integer> books = new HashMap<>();
    private int shelfNumber;
    private String subject;

    public Shelf() {
    }

    public Shelf(int shelfNumber, String subject) {
        this.shelfNumber = shelfNumber;
        this.subject = subject;
    }

    public Code addBook(Book book) {
        if (!subject.equals(book.getSubject())) {
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }else if (books.containsKey(book)) {
            Integer temp = books.get(book);
            temp++;
            books.put(book, temp);
            return Code.SUCCESS;
        } else {
            books.put(book, 1);
            System.out.println(book + " added to shelf");
            return Code.SUCCESS;
        }
    }

    public int getBookCount(Book book) {
        if (!books.containsKey(book))
            return -1;
        return books.get(book);
    }

    public Code removeBook(Book book) {
        if (!books.containsKey(book)) {
            System.out.println(book.getTitle() + " is not on shelf " + subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else if (books.containsKey(book) && (books.get(book) == 0)) {
            System.out.println("No copies of " + book.getTitle() + " remains on shelf " + subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else {
            Integer temp = books.get(book);
            temp--;
            books.put(book, temp);
            System.out.println(book.getTitle() + " successfully removed from shelf " + subject);
            return Code.SUCCESS;
        }
    }

    public String listBooks() {
        if (books == null || books.isEmpty())
            return "0 books on shelf: " + shelfNumber + " : " + subject;
        int totalBookCount = 0;
        String bookInfo = "";
        for(Book b: books.keySet()) {
            int count = books.get(b);
            totalBookCount += count;
            bookInfo += "\n" + b.toString() + " " + count;
        }
        return totalBookCount + " books on shelf : " + shelfNumber+ " : " + subject + bookInfo;
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
