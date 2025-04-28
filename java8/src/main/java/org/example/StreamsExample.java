package org.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamsExample {

    public static void main(final String[] args) {

        List<Author> authors = Library.getAuthors();
        
        banner("Authors information");
        // SOLVED With functional interfaces declared
        Consumer<Author> authorPrintConsumer = new Consumer<Author>() {
            @Override
            public void accept(Author author) {
                System.out.println(author);
            }
        };
        authors
            .stream()
            .forEach(authorPrintConsumer);

        // SOLVED With functional interfaces used directly
        authors
            .stream()
            .forEach(System.out::println);

        banner("Active authors");
        // TODO With functional interfaces declared
        Predicate<Author> isActiveAuthor = new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.active;
            }
        };
        List<Author> activeAuthors = authors.stream().filter(isActiveAuthor).collect(Collectors.toList());
        activeAuthors.stream().forEach(System.out::println);

        banner("Active authors - lambda");
        // TODO With functional interfaces used directly
        authors.stream()
                .filter(author -> author.active)
                .forEach(System.out::println);

        banner("Active books for all authors");
        // TODO With functional interfaces declared
      Predicate<Book> isActiveBook = new Predicate<Book>() {
          @Override
          public boolean test(Book book) {
              return book.active;
          }
      };

      List<Book> activeBooks = authors.stream().flatMap(author -> author.books.stream()).
              filter(isActiveBook).collect(Collectors.toList());
      activeBooks.forEach(System.out::println);




        banner("Active books for all authors - lambda");
        // TODO With functional interfaces used directly

authors.stream().
        flatMap(author -> author.books.stream()).filter(book -> book.active).
        forEach(System.out::println);





        banner("Average price for all books in the library");
        // TODO With functional interfaces declared


        Consumer<Double> printAveragePrice = new Consumer<Double>() {
            @Override
            public void accept(Double average) {
                System.out.println("Average Price "+ average);
            }
        };

        OptionalDouble averagePrice = authors.stream().flatMap(author -> author.books.stream()).
                mapToInt(book->book.price).average();

        averagePrice.ifPresent(avg->printAveragePrice.accept(avg));


        banner("Average price for all books in the library - lambda");
        // TODO With functional interfaces used directly

        authors.stream().flatMap(author -> author.books.stream()).mapToInt(book ->book.price).
                average().ifPresent(avg-> System.out.println("average price : "+avg));




        banner("Active authors that have at least one published book");
        // TODO With functional interfaces declared
Predicate<Author> hasPublishedBooksAndIsActive = new Predicate<Author>() {
    @Override
    public boolean test(Author author) {
        return author.active && author.books.stream().anyMatch(book->book.published);
    }
};
List<Author> activeAuthorsWithPublishedBooks = authors.stream().
        filter(hasPublishedBooksAndIsActive).collect(Collectors.toList());

activeAuthorsWithPublishedBooks.forEach(System.out::println);



        banner("Active authors that have at least one published book - lambda");
        // TODO With functional interfaces used directly
        authors.stream().
                filter(author -> author.active && author.books.stream().anyMatch(Book->Book.published)).
                forEach(System.out::println);

    }

    private static void banner(final String m) {
        System.out.println("#### " + m + " ####");
    }
}


class Library {
    public static List<Author> getAuthors() {
        return Arrays.asList(
            new Author("Author A", true, Arrays.asList(
                new Book("A1", 100, true,true),
                new Book("A2", 200, true,true),
                new Book("A3", 220, true,false))),
            new Author("Author B", true, Arrays.asList(
                new Book("B1", 80, true,false),
                new Book("B2", 80, false,true),
                new Book("B3", 190, true,false),
                new Book("B4", 210, true,false))),
            new Author("Author C", true, Arrays.asList(
                new Book("C1", 110, true,false),
                new Book("C2", 120, false,true),
                new Book("C3", 130, true,false))),
            new Author("Author D", false, Arrays.asList(
                new Book("D1" , 200, true,false),
                new Book("D2", 300, false,true))),
            new Author("Author X", true, Collections.emptyList()));
    }
}

class Author {
    String name;
    boolean active;
    List<Book> books;

    Author(String name, boolean active, List<Book> books) {
        this.name = name;
        this.active = active;
        this.books = books;
    }

    @Override
    public String toString() {
        return name + "\t| " + (active ? "Active" : "Inactive");
    }
}

class Book {
    String name;
    int price;
    boolean published;
    // i had to add field to this class cuz to do the active books
    boolean active;
    Book(String name, int price, boolean published,boolean active) {
        this.name = name;
        this.price = price;
        this.published = published;
        this.active = active;
    }

    @Override
    public String toString() {
        return name + "\t| " + "\t| $" + price + "\t| " + (published ? "Published" : "Unpublished")
                +(active ? "Active" : "Inactive");
    }
}
