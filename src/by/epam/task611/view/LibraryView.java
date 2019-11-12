package by.epam.task611.view;

import by.epam.task611.aggreg.Library;

public class LibraryView {

  public void showLibrary(Library library) {
    System.out.println(library);
  }

  public void showBooks(Library library) {
    System.out.println("Books {");
    for (int i = 0; i < library.getBooks().size(); i++) {
      System.out.print((i + 1) + ": " + library.getBook(i).toString());
    }
    System.out.println("};");
  }

  public void showReaders(Library library) {
    System.out.println("Readers {");
    for (int i = 0; i < library.getReaders().size() - 1; i++) {
      System.out.print((i + 1) + ": " + library.getReader(i).toString());
    }
    System.out.println("};");
  }

  public void showReaderBooks(Library library, int index) {
    System.out.println("Ваши книги: ");
    for (int i = 0; i < library.getReader(index).getBooks().size(); i++) {
      System.out.print((i + 1) + ": " + library.getReader(index).getBook(i).toString());
    }
  }
}
