package saheel_library_management.project.Library_Management_System.Converter;

import saheel_library_management.project.Library_Management_System.Entity.Author;
import saheel_library_management.project.Library_Management_System.Entity.Books;
import saheel_library_management.project.Library_Management_System.Entity.Card;
import saheel_library_management.project.Library_Management_System.RequestDto.BookRequestDto;

public class BookConverter {
    // converter acts as the model mapper; used to convert dto to model before saving to the database
    public static Books convertBookRequestDtoToBookModel(BookRequestDto bookRequestDto) {
        Books books = new Books();
        books.setTitle(bookRequestDto.getTitle());
        books.setPublisherName(bookRequestDto.getPublisherName());
        books.setPublishedDate(bookRequestDto.getPublishedDate());
        books.setPages(bookRequestDto.getPages());
        books.setAvailability(bookRequestDto.isAvailability());
        books.setCategory(bookRequestDto.getCategory());
        books.setRackNo(bookRequestDto.getRackNo());



        return books;
    }
}
