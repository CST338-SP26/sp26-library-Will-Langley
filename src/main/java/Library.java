import Utilities.Code;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Library {
    public int LENDING_LIMIT = 5;
    private HashMap<Book, Integer> books = new HashMap<>();
    private static int libraryCard;
    private String name;
    private ArrayList<Reader> readers = new ArrayList<>();
    private HashMap<String, Shelf> shelves = new HashMap<>();

    public Library(String name) {
        this.name = name;
    }

    public Code addBook(Book book) {
        if (books.containsKey(book)) {
            books.put(book, books.get(book)+1);
            System.out.println(books.get(book) +" copies of " + book.getTitle() + " in the stacks");
        } else{
            books.put(book, 1);
            System.out.println(book.getTitle() + " added to the stacks.");
        }
        if (shelves.containsKey(book.getSubject())) {
            Shelf shelf = shelves.get(book.getSubject());
            shelf.addBook(book);
        } else {
            System.out.println("No shelf for " + book.getSubject() + " books");
            return Code.SHELF_EXISTS_ERROR;
        }
        return Code.SUCCESS;
    }

    public Code addReader(Reader reader) {
        if (readers.contains(reader)) {
            System.out.println(reader.getName() + " already has an account!");
            return Code.READER_ALREADY_EXISTS_ERROR;
        }
        for(Reader r: readers) {
            if (r.getCardNumber() == reader.getCardNumber()) {
                System.out.println(r.getName() + " and " + reader.getName() + " have the same card number!");
                return Code.READER_CARD_NUMBER_ERROR;
            }
        }
        System.out.println(reader.getName() + " added to the library!");
        if (reader.getCardNumber() > libraryCard)
            libraryCard = reader.getCardNumber();
        readers.add(reader);
        return Code.SUCCESS;
    }

    public Code addShelf(Shelf shelf) {
        if (shelves.containsKey(shelf.getSubject())) {
            System.out.println("ERROR: Shelf already exists " + shelf);
            return Code.SHELF_EXISTS_ERROR;
        }
        shelves.put(shelf.getSubject(), shelf);
        return Code.SUCCESS;
    }

    public Code addShelf(String s) {
        Shelf shelf = new Shelf(shelves.size()+1, s);
        return addShelf(shelf);
    }

    public Code checkOutBook(Reader reader, Book book) {
        if (reader == null) {
            System.out.println(reader.getName() + " doesn't have an account here");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        } else if (reader.getBookCount() >= LENDING_LIMIT) {
            System.out.println(reader.getName() + " has reached the lending limit, " + LENDING_LIMIT);
            return Code.BOOK_LIMIT_REACHED_ERROR;
        } else if (!books.containsKey(book)) {
            System.out.println("ERROR: Could not find " + book.getTitle());
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else if (shelves.containsKey(book.getSubject())) {
            System.out.println("no shelf for " + book.getSubject() + " books!");
            return Code.SHELF_EXISTS_ERROR;
        } else if (shelves.get(book.getSubject()).getBookCount(book) < 1) {
            System.out.println("ERROR: no copies of " + book.getTitle() + " remain");
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }
        Code code = reader.addBook(book);
        if (!code.equals(Code.SUCCESS)) {
            System.out.println("Couldn't checkout " + book.getTitle());
            return code;
        }

        Code code1 = shelves.get(book.getSubject()).removeBook(book);
        if (code1.equals(Code.SUCCESS)) {
            System.out.println(book.getTitle() + " checked out successfully");
            return Code.SUCCESS;
        }
        return code1;
    }

    public static LocalDate convertDate(String date, Code errorCode) {
        if (date.equals("0000")) {
            return LocalDate.of(1970, 1, 1);
        }
        String[] dateArray = date.split("-");
        if (dateArray.length != 3) {
            System.out.println("ERROR: date conversion error, could not parse " + date);
            System.out.println("Using default date (01-jan-1970)");
            return LocalDate.of(1970, 1, 1);
        }

        try {
            int year = Integer.parseInt(dateArray[0]);
            int month = Integer.parseInt(dateArray[1]);
            int day = Integer.parseInt(dateArray[2]);
            if (year < 0 || month <0 || day <0) {
                System.out.println("Error converting date: Year " + year);
                System.out.println("Error converting date: Month " + month);
                System.out.println("Error converting date: Day " + day);
                System.out.println("Using default date (01-jan-1970)");
                return LocalDate.of(1970, 1, 1);
            }
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            System.out.println("ERROR: date conversion error, could not parse " + date);
            System.out.println("Using default date (01-jan-1970)");
            return LocalDate.of(1970, 1, 1);
        }
    }

    public static int convertInt(String recordCountString, Code code) {
        try {
            return Integer.parseInt(recordCountString);
        } catch (NumberFormatException e){
            System.out.println("Which value caused the error: " + recordCountString);
            if (code.equals(Code.BOOK_COUNT_ERROR)) {
                System.out.println("Error: Could not read number of books");
            } else if (code.equals(Code.PAGE_COUNT_ERROR)) {
                System.out.println("Error: could not parse page count");
            } else if (code.equals(Code.DATE_CONVERSION_ERROR)) {
                System.out.println("Error: Could not parse date component");
            } else {
                System.out.println("Error: Unknown conversion error");
            }
            return code.getCode();
        }
    }

    private Code errorCode(int codeNumber) {
        for (Code code : Code.values()) {
            if (code.getCode() == codeNumber) {
                return code;
            }
        }
        return Code.UNKNOWN_ERROR;
    }


    public Book getBookByISBN (String s) {
        for(Book b: books.keySet())
            if (b.getISBN().equals(s))
                return b;
        System.out.println("ERROR: Could not find book with isbn: " + s);
        return null;
    }

    public static int getLibraryCardNumber() {
        return libraryCard+1;
    }

    public String getName() {
        return name;
    }

    public Reader getReaderByCard(int card) {
        for (int i = 0; i<readers.size(); i++) {
            if (readers.get(i).getCardNumber() == card)
                return readers.get(i);
        }
        System.out.println("Could not find a reader with card #" + card);
        return null;
    }

    public Shelf getShelf(String subject) {
        if (shelves.containsKey(subject))
            return shelves.get(subject);
        System.out.println("No shelf for " + subject + " books");
        return null;
    }

    public Shelf getShelf(Integer shelfNumber) {
        for(Shelf s: shelves.values()) {
            if (s.getShelfNumber() == shelfNumber)
                return s;
        }
        System.out.println("No shelf number " + shelfNumber + " found");
        return null;
    }

    public Code init(String fileName) {
        FileReader f1;
        try {
            f1 = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner s1 = new Scanner(f1);
        int numBooks = s1.nextInt();
        initBooks(numBooks, s1);
        int numShelves = s1.nextInt();
        initShelves(numShelves, s1);
        int numReader = s1.nextInt();
        initReader(numReader, s1);
        return Code.SUCCESS;
    }

    private Code initBooks(int numBooks, Scanner s1) {
        if (numBooks < 1) {
            return Code.LIBRARY_ERROR;
        }
        for (int i = 0; i< numBooks; i++) {
            if (!s1.hasNextLine())
                return Code.BOOK_RECORD_COUNT_ERROR;
            String[] arr = s1.nextLine().split(",");
            if (arr.length < 6) {
                return Code.BOOK_RECORD_COUNT_ERROR;
            }
            int pageCount = convertInt(arr[3], Code.PAGE_COUNT_ERROR);
            if (pageCount <= 0)
                return Code.PAGE_COUNT_ERROR;
            Book b = new Book(arr[Book.ISBN_], arr[Book.TITLE_], arr[Book.SUBJECT_], convertInt(arr[Book.PAGE_COUNT_], Code.PAGE_COUNT_ERROR), arr[Book.AUTHOR_], convertDate(arr[Book.DUE_DATE_], Code.DATE_CONVERSION_ERROR));
            addBook(b);
        }
        return Code.SUCCESS;
    }

    private Code initShelves(int numShelves, Scanner s1) {
        if (numShelves < 1)
            return Code.SHELF_COUNT_ERROR;
        for (int i =0; i<numShelves; i++) {
            String[] arr = s1.nextLine().split(",");
            Shelf s = new Shelf(convertInt(arr[Shelf.SHELF_NUMBER_], Code.SHELF_NUMBER_PARSE_ERROR), arr[Shelf.SUBJECT_]);
            addShelf(s);
        }
        if (shelves.size() == numShelves) {
            return Code.SUCCESS;
        }
        System.out.println("Number of shelves doesn't match expected");
        return Code.SHELF_NUMBER_PARSE_ERROR;
    }

    private Code initReader(int numReaders, Scanner s1) {
        if (numReaders <= 0){
            return Code.READER_COUNT_ERROR;
        }
        for (int i = 0; i <numReaders; i++) {
            String[] arr = s1.nextLine().split(",");
            Reader reader = new Reader(convertInt(arr[Reader.CARD_NUMBER_], Code.READER_CARD_NUMBER_ERROR), arr[Reader.NAME_], arr[Reader.PHONE_]);
            addReader(reader);
            int numBooks = convertInt(arr[Reader.BOOK_COUNT_], Code.READER_COUNT_ERROR);
            for (int j = Reader.BOOK_START_; j < Reader.BOOK_START_ + (numBooks*2); j+=2) {
                String isbn = arr[j];
                String date = arr[j+1];
                Book tempBook = getBookByISBN(isbn);
                if (tempBook == null) {
                    System.out.println("ERROR");
                }
                LocalDate dueDate = convertDate(date, Code.DATE_CONVERSION_ERROR);
                Book newBook = new Book(tempBook.getISBN(), tempBook.getTitle(), tempBook.getSubject(), tempBook.getPageCount(), tempBook.getAuthor(), dueDate);
                checkOutBook(reader, newBook);
            }
        }
        return Code.SUCCESS;
    }

    public int listBooks() {
        int totalBooks = 0;
        for(Book b: books.keySet()) {
            int count = books.get(b);
            System.out.println(count + " copies of " + b);
            totalBooks += count;
        }
        return totalBooks;
    }

    public int listReaders() {
        int count = 0;
        for (Reader r: readers) {
            System.out.println(r);
            count++;
        }

        return count;
    }

    public int listReaders(Boolean showBooks) {
        int count = 1;
        if (showBooks) {
            for (Reader r : readers) {
                System.out.println(r.getName() + "(#" + count + ") has the following books:\n" + r.getBooks());
                count++;
            }
        } else {
            for (Reader r : readers) {
                System.out.println(r);
                count++;
            }
        }
        return count-1;
    }

    public int listShelves(Boolean showBooks) {
        int count = 0;
        if (showBooks) {
            for (Shelf s: shelves.values()) {
                s.listBooks();
                count++;
            }
        } else {
            for (Shelf s: shelves.values()) {
                System.out.println(s);
                count++;
            }
        }
        return count;
    }

    public int listShelves() {
        return listShelves(false);
    }

    public Code removeReader(Reader reader) {
        if (readers.contains(reader) && (reader.getBookCount() > 0)) {
            System.out.println(reader.getName() + " must return all books!");
            return Code.READER_STILL_HAS_BOOKS_ERROR;
        } else if(!readers.contains(reader)) {
            System.out.println(reader.getName() + " is not part of this Library");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }
        readers.remove(reader);
        return Code.SUCCESS;
    }

    public Code returnBook(Reader reader, Book book) {
        if (!books.containsKey(book)) {
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }
        if (!reader.hasBook(book)) {
            System.out.println(reader.getName() + " does not have " + book.getTitle() + " checked out");
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        } else {
            System.out.println(reader.getName() + " is returning " + book.getTitle());
            Code code = reader.removeBook(book);
            if (code.equals(Code.SUCCESS)) {
                return returnBook(book);
            } else {
                System.out.println("Could not return " + book.getTitle());
                return code;
            }

        }
    }

    public Code returnBook(Book book) {
        if (shelves.containsKey(book.getSubject())) {
            System.out.println("No shelf for " + book.getTitle());
            return Code.SHELF_EXISTS_ERROR;
        }
        return shelves.get(book.getSubject()).addBook(book);
    }

}
