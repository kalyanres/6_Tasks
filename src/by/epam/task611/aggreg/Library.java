package by.epam.task611.aggreg;

import by.epam.task611.data.Book;
import by.epam.task611.data.Reader;

import java.util.ArrayList;
import java.util.Objects;

public class Library {
  private ArrayList<Book> books;
  private ArrayList<Reader> readers;
  private String password = "LibraryBook";

  public Library() {
    books = new ArrayList<>();
    readers = new ArrayList<>();

  }

  public Library(ArrayList<Book> books, ArrayList<Reader> readers) {
    this.books = books;
    this.readers = readers;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Book getBook(int index) {
    return books.get(index);
  }

  public ArrayList<Book> getBooks() {
    return books;
  }

  public void setBooks(ArrayList<Book> books) {
    this.books = books;
  }

  public Reader getReader(int index) {
    return readers.get(index);
  }

  public ArrayList<Reader> getReaders() {
    return readers;
  }

  public void setReaders(ArrayList<Reader> readers) {
    this.readers = readers;
  }

  public boolean addBook(Book book) {
    return books.add(book);
  }

  public boolean removeBook(Book book) {
    return books.remove(book);
  }

  public void removeBook(int index) {
    books.remove(index);
  }

  public boolean addReader(Reader reader) {
    return readers.add(reader);
  }

  public boolean removeReader(Reader reader) {
    return readers.remove(reader);
  }

  public void removeReader(int index) {
    readers.remove(index);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Library)) return false;
    Library library = (Library) o;
    return Objects.equals(getBooks(), library.getBooks()) &&
            Objects.equals(getReaders(), library.getReaders());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getBooks(), getReaders());
  }

  @Override
  public String toString() {
    return "Library{" +
            "books=\n" + books +
            ", readers=\n" + readers +
            '}';
  }
}
