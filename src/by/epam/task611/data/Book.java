package by.epam.task611.data;

import java.util.Objects;

public class Book {
  private String author;
  private String title;
  private boolean available;
  private boolean paper;

  public Book() {
  }

  public Book(String author, String title, boolean available, boolean paper) {
    this.author = author;
    this.title = title;
    this.available = available;
    this.paper = paper;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public boolean isPaper() {
    return paper;
  }

  public void setPaper(boolean paper) {
    this.paper = paper;
  }

  public String changeForFile() {
    return author + ";" + title + ";" + available + ";" + paper;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Book)) return false;
    Book book = (Book) o;
    return  Objects.equals(getAuthor(), book.getAuthor()) &&
            Objects.equals(getTitle(), book.getTitle());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAuthor(), getTitle(), isAvailable(), isPaper());
  }

  @Override
  public String toString() {
    String str = "Book {" +
            "author='" + author + '\'' +
            ", title= " + title ;
    if (available)
      str += ", available";
    else
      str += ", does not available";
    if (paper)
      str += ", paper };";
    else
      str += ", electronic };";
    return str + '\n';
  }
}
