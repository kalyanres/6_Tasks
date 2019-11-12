package by.epam.task611.logic;

import by.epam.task611.aggreg.Library;
import by.epam.task611.data.Book;
import by.epam.task611.data.Reader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryLogic {
  private ArrayList<String> elements;

  public LibraryLogic() {
    elements = new ArrayList<String>();
  }

  public ArrayList<String> getElements() {
    return elements;
  }

  // создание массива String из файла
  public boolean readFile(String fileName) throws Exception {
    boolean success = true;
    FileReader fr = new FileReader(fileName);
    try {
      Scanner scan = new Scanner(fr);

      char[] ch = new char[100];
      while (scan.hasNextLine()) {
        elements.add(scan.nextLine());
      }
      fr.close();
    } catch (IOException e) {
      success = false;
    }
    return success;
  }

  // заполнение данными Book
  public void fillDataBooks(Library library) {
    int element = 0;
    int count = 0;
    char[] buff;
    String s;
    for (int i = 0; i < elements.size(); i++) {
      library.addBook(new Book());
      for (int j = 0; j < elements.get(i).length(); j++) {
        char[] buf = new char[100];

        // поэлементно копируем в буфер
        for (int k = j; k < elements.get(i).length(); k++) {
          if (elements.get(i).charAt(k) == ';')
            break;
          buf[k - j] = elements.get(i).charAt(k);
          count = k + 1;
        }
        // избавляемся от лишних элементов буфера
        buff = new char[count - j];
        if (count - j >= 0)
          System.arraycopy(buf, 0, buff, 0, count - j);
        s = new String(buff);
        if (element == 0) {
          library.getBook(i).setAuthor(s);
          element++;
        } else if (element == 1) {
          library.getBook(i).setTitle(s);
          element++;
        } else if (element == 2) {
          library.getBook(i).setAvailable(Boolean.parseBoolean(s));
          element++;
        } else if (element == 3) {
          library.getBook(i).setPaper(Boolean.parseBoolean(s));
          element = 0;
        }
        j = count;
      }
    }
  }

  // заполнение данными Reader
  public void fillDataReaders(Library library) {
    int element = 0;
    int count = 0;
    char[] buff;
    String s;
    for (int i = 0; i < elements.size(); i++) {
      library.addReader(new Reader());
      for (int j = 0; j < elements.get(i).length(); j++) {
        char[] buf = new char[100];

        // поэлементно копируем в буфер
        for (int k = j; k < elements.get(i).length(); k++) {
          if (elements.get(i).charAt(k) == ';')
            break;
          buf[k - j] = elements.get(i).charAt(k);
          count = k + 1;
        }
        // избавляемся от лишних элементов буфера
        buff = new char[count - j];
        if (count - j >= 0)
          System.arraycopy(buf, 0, buff, 0, count - j);
        s = new String(buff);
        if (element == 0) {
          library.getReader(i).setName(s);
          element++;
        } else if (element == 1) {
          library.getReader(i).setAdministrator(Boolean.parseBoolean(s));
          element = 0;
        }
        j = count;
      }
    }
  }

  // взятие книги из библиотеки
  public boolean takeBook(Library library, int reader, int bookNumber) {
    // делаем книгу недоступной в библиотеке
    library.getBook(bookNumber).setAvailable(false);
    // добавляем книгу в список читателя
    return (library.getReader(reader).addBook(library.getBook(bookNumber)));
  }

  // возврат книги в библиотеку
  public boolean returnBook(Library library, int reader, int bookNumber) {
    int size = library.getReader(reader).getBooks().size();
    // делаем доступной в библиотеке
    library.getReader(reader).getBook(bookNumber).setAvailable(true);
    // удаляем книгу из списка читателя
    library.getReader(reader).removeBook(bookNumber);
    return size - library.getReader(reader).getBooks().size() == 1;
  }


}
