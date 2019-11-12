package by.epam.task611.data;

import java.util.ArrayList;
import java.util.Objects;

public class Reader {
  private String name;
  private boolean administrator;
  private ArrayList<Book> books;
  private ArrayList<Mail> mails;

  public Reader() {
    books = new ArrayList<>();
    mails = new ArrayList<>();
  }

  public Reader(String name, boolean administrator) {
    this.name = name;
    this.administrator = administrator;
    mails = new ArrayList<>();
  }

  public Reader(String name, boolean administrator, ArrayList<Book> books) {
    this.name = name;
    this.administrator = administrator;
    this.books = books;
    mails = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isAdministrator() {
    return administrator;
  }

  public void setAdministrator(boolean administrator) {
    this.administrator = administrator;
  }

  public Book getBook(int index) {
    return books.get(index);
  }

  public ArrayList<Book> getBooks() {
    return books;
  }

  public void setBook(Book book) {
    books.add(book);
  }

  public void setBooks(ArrayList<Book> books) {
    this.books = books;
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

  public Mail getMail(int index) {
    return mails.get(index);
  }

  public ArrayList<Mail> getMails() {
    return mails;
  }

  public String showMail() {
    String string;
    if (mails.size() == 0) {
      string = "Ваша почта пуста";
    } else {
      string = "У вас есть почта:\n";
      for (int i = 0; i < mails.size(); i++) {
        string += (i + 1) + ": " + mails.get(i).toString();
      }
    }
    return string;
  }

  public void setMails(ArrayList<Mail> mail) {
    this.mails = mail;
  }

  public void addMail(Mail mail) {
    this.mails.add(mail);
  }

  public void clearMail() {
   mails.clear();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Reader)) return false;
    Reader reader = (Reader) o;
    return isAdministrator() == reader.isAdministrator() &&
            Objects.equals(getName(), reader.getName()) &&
            Objects.equals(getBooks(), reader.getBooks());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), isAdministrator(), getBooks());
  }

  @Override
  public String toString() {
    String str = "Читатель{" +
            "имя='" + name + '\'';
    if (administrator)
      str += ", администратор";
    else
      str += ", читатель";
    if (books.size() == 0)
      str += '\n';
    for (int i = 0; i < books.size(); i++) {
      str += books.get(i).toString();
    }
    return str;
  }
}
