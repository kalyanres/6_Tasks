package by.epam.task611.data;

public class Mail {
  private String reader;
  private Book book;

  public Mail() {
  }

  public Mail(String reader, Book book) {
    this.reader = reader;
    this.book = book;
  }

  public String getReader() {
    return reader;
  }

  public void setReader(String reader) {
    this.reader = reader;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  @Override
  public String toString() {
    return "from: " + reader +
            ", book: " + book.toString();
  }
}
