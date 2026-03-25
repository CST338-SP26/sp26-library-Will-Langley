import Utilities.Code;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reader {

    public final static int CARD_NUMBER_ = 0;
    public final static int NAME_ = 1;
    public final static int PHONE_ = 2;
    public final static int BOOK_COUNT_ = 3;
    public final static int BOOK_START_ = 4;

    private int cardNumber;
    private String name;
    private String phone;
    private ArrayList<Book> books;

    public Reader(int cardNumber, String name, String phone) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.phone = phone;
        books = new ArrayList<Book>();
    }

    public Code addBook(Book book){
        if(books.contains(book))
            return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;
        books.add(book);
        return Code.SUCCESS;
    }

    public Code removeBook(Book book) {
        if(!books.contains(book))
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        else {
            books.remove(book);
            return Code.SUCCESS;
        }
    }

    public boolean hasBook(Book book) {
        return books.contains(book);
    }

    public int getBookCount(){
        return books.size();
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books1) {
        books = (ArrayList<Book>) books1;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return getCardNumber() == reader.getCardNumber() && Objects.equals(getName(), reader.getName()) && Objects.equals(getPhone(), reader.getPhone()) && Objects.equals(getBooks(), reader.getBooks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCardNumber(), getName(), getPhone(), getBooks());
    }

    @Override
    public String toString() {
        return name + "(#" + cardNumber + ") has checked out " + books;
    }
}
