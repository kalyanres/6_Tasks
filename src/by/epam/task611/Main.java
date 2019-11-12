package by.epam.task611;

/*
 *
 * Задание 1: создать консольное приложение “Учет книг в домашней библиотеке”.
 *
    Общие требования к заданию:
    • Система учитывает книги как в электронном, так и в бумажном варианте.
    • Существующие роли: пользователь, администратор.
    • Пользователь может просматривать книги в каталоге книг, осуществлять поиск
    книг в каталоге.
    • Администратор может модифицировать каталог.
    • *При добавлении описания книги в каталог оповещение о ней рассылается на
    e-mail всем пользователям
    • **При просмотре каталога желательно реализовать постраничный просмотр
    • ***Пользователь может предложить добавить книгу в библиотеку, переслав её
    администратору на e-mail.
    • Каталог книг хранится в текстовом файле.
    • Данные аутентификации пользователей хранятся в текстовом файле. Пароль
    не хранится в открытом виде
 */

import by.epam.task611.aggreg.Library;
import by.epam.task611.data.Book;
import by.epam.task611.data.Mail;
import by.epam.task611.data.Reader;
import by.epam.task611.logic.LibraryLogic;
import by.epam.task611.view.LibraryView;

import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

  public static void main(String[] args) throws Exception {

    LibraryLogic libraryLogic = new LibraryLogic();
    Library library = new Library();
    LibraryView libraryView = new LibraryView();

    // чтение файла и заполнение библиотеки книгами
    if (libraryLogic.readFile("library.txt")) {
      libraryLogic.fillDataBooks(library);
    }

    libraryLogic = new LibraryLogic();
    // чтение файла и заполнение библиотеки читателями
    if (libraryLogic.readFile("readers.txt")) {
      libraryLogic.fillDataReaders(library);
    }

    // создание администратора
    library.addReader(new Reader("Administrator", true));


    Scanner in = new Scanner(System.in);
    String scan;
    // проверка ввода с помощью регулярных выражений

    // реализация интерфейса
    System.out.print("Добрый день! Хотите воспользоваться услугами нашей библиотеки? (y/n): ");
    scan = enterYN();

    while (scan.equals("y")) {
      libraryView.showReaders(library);
      System.out.print("Пожалуйста, найдите себя в списке читателей. (введите номер): ");
      scan = in.nextLine();
      int reader = 0;
      if (enterAdmin(scan, library.getPassword())) {
        // изменение библиотеки
        boolean change = false;
        // реализация функций администратора
        while (true) {
          int adminNumber = library.getReaders().size() - 1;
          System.out.println(library.getReader(adminNumber).showMail());
          System.out.println("Хотите добавить в список книгу от читателя? (нажмите 'a')");
          System.out.println("Хотите добавить книгу в список самостоятельно? (нажмите 's')");
          System.out.println("Хотите очистить почту? (нажмите 'c')");
          System.out.println("Выйти (нажмите q)");
          System.out.print("ввод: ");

          scan = enterAdminMenu();


          if (scan.equals("q")) {
            if (change) {
              String string;
              string = new String();
              for (Book book : library.getBooks()) {
                string += book.changeForFile() + '\n';
              }
              FileWriter nFile = new FileWriter("library.txt", false);
              nFile.write(string);
              nFile.close();
            }
            break;
          }
          else if (scan.equals("a")) {
          // если нет книг
            if (library.getReader(adminNumber).getMails().size() == 0)
              System.out.println("У вас нет предложенных книг для добавления");
          // если книга одна
            else if (library.getReader(adminNumber).getMails().size() == 1) {
              if (library.addBook(library.getReader(adminNumber).getMail(0).getBook())) {
                change = true;
                System.out.println("Книга успешно добавлена");
              }
            // отправка сообщения каждому читателю
              for (int i = 0; i < library.getReaders().size() - 1; i++) {
                library.getReader(i).addMail(new Mail(library.getReader(adminNumber).getName(),
                      library.getReader(adminNumber).getMail(0).getBook()));
              }
            // если книг больше чем одна
            } else {
              change = true;
              System.out.print("Введите номер сообщения: ");
              scan = in.nextLine();
              int number = enterNumber(scan, library.getReader(adminNumber).getMails().size());
              if (library.addBook(library.getReader(adminNumber).getMail(number).getBook()))
                System.out.println("Книга успешно добавлена");
              // отправка сообщения каждому читателю
              for (int i = 0; i < library.getReaders().size() - 1; i++) {
                library.getReader(i).addMail(new Mail(library.getReader(adminNumber).getName(),
                      library.getReader(adminNumber).getMail(number).getBook()));
              }
            }

          } else if (scan.equals("s")) {
            System.out.print("Введите автора: ");
            String author = in.nextLine();
            System.out.print("Введите название: ");
            scan = in.nextLine();
            String title = "\"" + scan + "\"";
            System.out.print("Это бумажная книга? (y/n): ");
            scan = enterYN();
            Book newBook = new Book(author, title, true, (scan.equals("y")));
            System.out.println("Вы ввели книгу: " + newBook.toString());
            System.out.print("Хотите ее добавить в библиотеку? (y/n): ");
            scan = enterYN();
            if (scan.equals("y")) {
              // добавление книги в библиотеку
              if (library.addBook(newBook)) {
                change = true;
                System.out.println("Книга успешно добавлена");
              }
              // отправка сообщения каждому читателю
              for (int i = 0; i < library.getReaders().size() - 1; i++) {
                library.getReader(i).addMail(new Mail(library.getReader(adminNumber).getName(),
                        newBook));
              }
            }
          } else if (scan.equals("c")) library.getReader(adminNumber).clearMail();
        }
      } else {
        reader = enterNumber(scan, library.getReaders().size() - 1);
        System.out.println("Приветствуем вас в нашей библиотеке, уважаемый " +
                library.getReader(reader).getName() +
                "!");
        while (true) {
          // вывод сообщений почты
          System.out.println("Ваша почта: ");
          System.out.print(library.getReader(reader).showMail());
          boolean print = true;       // для распечатки списка книг один раз
          System.out.println("\nХотите вернуть книгу? (введите 'r'): ");
          System.out.println("Хотите взять книгу? (введите 't'): ");
          System.out.println("Хотите предложить книгу для библиотеки? (введите 'o'): ");
          System.out.println("Хотите очистить почту? (нажмите 'c')");
          System.out.println("Хотите выйти? (введите 'q'): ");
          System.out.print("ввод: ");
          scan = enterMenu();

          System.out.println();

          if (scan.equals("q")) {
            break;

          } else if (scan.equals("c")) library.getReader(reader).clearMail();
            // реализация возврата книги
          else if (scan.equals("r")) {
            while (true) {

              if (scan.equals("n")) {
                break;
              }
              if (library.getReader(reader).getBooks().size() == 0) {
                System.out.println("У вас нет книг.");
                break;
                // если в списке только одна книга, то возврат производится автоматически
              } else if (library.getReader(reader).getBooks().size() == 1) {
                library.getReader(reader).removeBook(0);
                System.out.println("Ваша последняя книга успешно возвращена.");
                break;
              } else if (library.getReader(reader).getBooks().size() > 1) {
                // вывод на экран списка взятых книг
                libraryView.showReaderBooks(library, reader);
                System.out.print("Введите номер книги, которую вы хотели бы вернуть: ");
                scan = in.nextLine();

                int numberBook = enterNumber(scan, library.getReader(reader).getBooks().size()) - 1;

                // возврат книги
                if (libraryLogic.returnBook(library, reader, numberBook))
                  System.out.println("Книга успешно возвращена.");

                // выход из цикла, если книг не осталось
                if (library.getBooks().size() == 0)
                  break;
              }
              System.out.print("Хотите вернуть еще одну книгу? (y/n): ");
              scan = enterYN();
            }

            // реализация взятия книги в библиотеке
          } else if (scan.equals("t")) {
            while (true) {

              if (scan.equals("n")) {
                break;
              } else if (scan.equals("y") || scan.equals("t")) {
                if (print) {    // предотвращение повторного выведения списка
                  libraryView.showBooks(library);
                  print = false;
                }
                System.out.print("Пожалуйста, найдите нужную книгу (введите номер): ");
                scan = in.nextLine();

                int numberBook = enterNumber(scan, library.getBooks().size()) - 1;

                // если книга занята
                if (!library.getBook(numberBook).isAvailable()) {
                  System.out.print("К сожалению данная книга находится на руках. Хотите выбрать что-то еще? (y/n): ");
                  scan = enterYN();
                  continue;
                } else {
                  System.out.println("Вы выбрали книгу " + library.getBook(numberBook).getAuthor() + " " +
                          library.getBook(numberBook).getTitle() + ". Она сейчас доступна.");
                }
                System.out.print("Будем оформлять? (y/n): ");
                scan = enterYN();

                if (scan.equals("n")) {
                  System.out.print("Хотите выбрать что-то другое? (y/n): ");
                  scan = enterYN();
                  continue;
                } else {
                  if (libraryLogic.takeBook(library, reader, numberBook))
                    System.out.println("Книга успешно добавлена");
                }
                System.out.print("Хотите взять еще одну книгу? (y/n): ");
                scan = enterYN();

              }

            }
          } else if (scan.equals("o")) {
            while (scan.equals("y") || scan.equals("o")) {
              System.out.print("Введите автора: ");
              String author = in.nextLine();
              System.out.print("Введите название: ");
              scan = in.nextLine();
              String title = "\"" + scan + "\"";
              System.out.print("Это бумажная книга? (y/n): ");
              scan = enterYN();
              Book newBook = new Book(author, title, true, (scan.equals("y")));
              System.out.println("Вы ввели книгу: " + newBook.toString());
              System.out.print("Отправить предложение администратору? (y/n): ");
              scan = enterYN();
              if (scan.equals("y")) {
                library.getReader(library.getReaders().size() - 1).addMail(new Mail(library.getReader(reader).getName(), newBook));
              }
              System.out.print("Хотите предложить еще одну книгу? (y/n): ");
              scan = enterYN();
            }
          }
        }
      }
      System.out.print("Добрый день! Хотите воспользоваться услугами нашей библиотеки? (y/n): ");
      scan = enterYN();
      if (scan.equals("n"))
        break;

    }

    System.out.println("Хорошего дня!");


  }

  // функция ввода 'y' и 'n' с проверкой
  public static String enterYN() {
    Scanner in = new Scanner(System.in);
    String scan = in.nextLine();
    String index = "[yn]";
    Pattern pattern = Pattern.compile(index);
    Matcher matcher = pattern.matcher(scan);
    // проверка буквенного ввода
    while (!matcher.find()) {
      System.out.print("Пожалуйста, вводите только буквы 'y' или 'n': ");
      scan = in.nextLine();
      matcher = pattern.matcher(scan);
    }
    return scan;
  }

  // функция ввода цифр с проверкой
  public static int enterNumber(String string, int size) {
    int number = 0;
    Scanner in = new Scanner(System.in);
    String scan = string;
    String index = "\\d+";
    Pattern pattern = Pattern.compile(index);
    Matcher matcher = pattern.matcher(scan);
    // проверка цифрового ввода
    while (!matcher.find() || Integer.parseInt(scan) > size
            || Integer.parseInt(scan) <= 0) {
      System.out.print("Пожалуйста, вводите только цифры от 1 до " + size + ": ");
      scan = in.nextLine();
      matcher = pattern.matcher(scan);
    }
    number = Integer.parseInt(scan);
    return number;
  }

  // функция входа в систему администратора c проверкой
  public static boolean enterAdmin(String string, String  password) {
    boolean success = false;
    Scanner in = new Scanner(System.in);
    String scan = string;
    String index = "sudo";
    Pattern pattern = Pattern.compile(index);
    Matcher matcher = pattern.matcher(scan);
    if (matcher.find()) {
      System.out.println("Введите пароль администратора: ");
      scan = in.nextLine();
      if (scan.equals(password)) {
        System.out.println("Добро пожаловать, администратор!");
        success = true;
      } else {
        System.out.println("Пароль не верен");
      }
    }
    return success;
  }

  // функция выбора главного меню с проверкой
  public static String enterMenu() {
    Scanner in = new Scanner(System.in);
    String scan = in.nextLine();
    String index = "[rtocq]";
    Pattern pattern = Pattern.compile(index);
    Matcher matcher = pattern.matcher(scan);

    // проверка буквенного ввода
    while (!matcher.find()) {
      System.out.print("Пожалуйста, вводите корректные буквы: ");
      scan = in.nextLine();
      matcher = pattern.matcher(scan);
    }
    return scan;
  }

  // функция выбора меню администратора с проверкой
  public static String enterAdminMenu() {
    Scanner in = new Scanner(System.in);
    String scan = in.nextLine();
    String index = "[ascq]";
    Pattern pattern = Pattern.compile(index);
    Matcher matcher = pattern.matcher(scan);

    // проверка буквенного ввода
    while (!matcher.find()) {
      System.out.print("Пожалуйста, вводите корректные буквы: ");
      scan = in.nextLine();
      matcher = pattern.matcher(scan);
    }
    return scan;
  }

}

