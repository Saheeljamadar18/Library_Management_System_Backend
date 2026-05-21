package saheel_library_management.project.Library_Management_System.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import saheel_library_management.project.Library_Management_System.Converter.BookConverter;
import saheel_library_management.project.Library_Management_System.Entity.Author;
import saheel_library_management.project.Library_Management_System.Entity.Books;
import saheel_library_management.project.Library_Management_System.Entity.Card;
import saheel_library_management.project.Library_Management_System.Repository.AuthorRepository;
import saheel_library_management.project.Library_Management_System.Repository.BookRepository;
import saheel_library_management.project.Library_Management_System.Repository.CardRepository;
import saheel_library_management.project.Library_Management_System.RequestDto.BookRequestDto;
import saheel_library_management.project.Library_Management_System.exception.BadRequestException;
import saheel_library_management.project.Library_Management_System.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Set;

@Service
public class BookService {

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("book_id", "title", "publisherName", "publishedDate", "pages", "availability", "category", "rackNo");

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    CardRepository cardRepository;

    public String saveBook(BookRequestDto bookRequestDto){
        Books books = BookConverter.convertBookRequestDtoToBookModel(bookRequestDto);

        if (bookRequestDto.getAuthorId() <= 0) {
            throw new BadRequestException("Author id is required. Save an author first, then use its id in authorId.");
        }

        Author author = authorRepository.findById(bookRequestDto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + bookRequestDto.getAuthorId()));
        books.setAuthor(author);

        if (bookRequestDto.getCardId() > 0) {
            Card card = cardRepository.findById(bookRequestDto.getCardId())
                    .orElseThrow(() -> new ResourceNotFoundException("Card not found with id: " + bookRequestDto.getCardId()));
            books.setCard(card);
        }

        bookRepository.save(books);
        return "book saved successfully !";
    }

    public Books getBookById(int id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("book not found with id " + id));
    }

    public List<Books> getAllBook() {
        return bookRepository.findAll();
    }

    public String deleteById(int id){
        bookRepository.deleteById(id);
        return "book with id:"+id+"is got deleted";
    }

    public String updateById(int id, BookRequestDto newBookRequestDto){
        Books existingBook = getBookById(id);
        existingBook.setTitle(newBookRequestDto.getTitle());
        existingBook.setPublisherName(newBookRequestDto.getPublisherName());
        existingBook.setPublishedDate(newBookRequestDto.getPublishedDate());
        existingBook.setPages(newBookRequestDto.getPages());
        existingBook.setAvailability(newBookRequestDto.isAvailability());
        existingBook.setCategory(newBookRequestDto.getCategory());
        existingBook.setRackNo(newBookRequestDto.getRackNo());

        if (newBookRequestDto.getAuthorId() > 0) {
            Author author = authorRepository.findById(newBookRequestDto.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + newBookRequestDto.getAuthorId()));
            existingBook.setAuthor(author);
        }

        if (newBookRequestDto.getCardId() > 0) {
            Card card = cardRepository.findById(newBookRequestDto.getCardId())
                    .orElseThrow(() -> new ResourceNotFoundException("Card not found with id: " + newBookRequestDto.getCardId()));
            existingBook.setCard(card);
        }

        bookRepository.save(existingBook);
        return "book updated successfully!";
    }

    public List<Books> getBookByPagination(int pageNo, int pageSize, String sortBy, String sortDir) {
        String field = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "book_id";
        Sort sort = "desc".equalsIgnoreCase(sortDir)
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();
        return bookRepository.findAll(PageRequest.of(pageNo, pageSize, sort)).getContent();
    }

    public Books getByTitle(String title){
        return bookRepository.getBookByTitle(title);
    }

    public List<Books> getByCategory(String inputCategory){
        return bookRepository.getBookByCategory(inputCategory);
    }

    public List<Books> searchBooks(String query) {
        if (query == null || query.isBlank()) {
            throw new BadRequestException("Search query is required.");
        }
        return bookRepository.findByTitleContainingIgnoreCaseOrPublisherNameContainingIgnoreCase(query, query);
    }
}
