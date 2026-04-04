import Utilities.Code;

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

    public Code addBook(Book b) {
        return Code.SUCCESS;
    }

    private Code addBookToShelf(Book book, Shelf shelf) {
        return Code.SUCCESS;
    }

    public Code addReader(Reader reader) {
        return Code.SUCCESS;
    }

    public Code addShelf(Shelf shelf) {
        return Code.SUCCESS;
    }

    public Code addShelf(String s) {
        return Code.SUCCESS;
    }

    public Code checkOutBook(Reader reader, Book book) {
        return Code.SUCCESS;
    }

    public static LocalDate convertDate(String s, Code code) {
        return LocalDate.now();
    }

    public static int convertInt(String s, Code code) {
        return 0;
    }

    private Code errorCode(int i) {
        return Code.SUCCESS;
    }

    public Book getBookByISBN (String s) {

    }

    public static int getLibraryCardNumber() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public Reader getReaderByCard(int card) {

    }

    public Shelf getShelf(String s) {

    }

    public Shelf getShelf(Integer i) {

    }

    public Code init(String s) {
        return Code.SUCCESS;
    }

    private Code initBooks(int i, Scanner s1) {
        return Code.SUCCESS;
    }

    private Code initReader(int i, Scanner s1) {
        return Code.SUCCESS;
    }

    private Code initShelves(int i, Scanner s1) {
        return Code.SUCCESS;
    }

    public int listBooks() {
        return 0;
    }

    public int listReaders() {
        return 0;
    }

    public int listReaders(Boolean b) {
        return 0;
    }

    public int listShelves(Boolean b) {
        return 0;
    }

    public int listShelves() {
        return 0;
    }

    public Code removeReader(Reader reader) {
        return Code.SUCCESS;
    }

    public Code returnBook(Reader reader, Book book) {
        return Code.SUCCESS;
    }

    public Code returnBook(Book book) {
        return Code.SUCCESS;
    }



}
