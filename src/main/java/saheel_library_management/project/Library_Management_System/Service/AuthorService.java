package saheel_library_management.project.Library_Management_System.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import saheel_library_management.project.Library_Management_System.Converter.AuthorConverter;
import saheel_library_management.project.Library_Management_System.Entity.Author;
import saheel_library_management.project.Library_Management_System.Repository.AuthorRepository;
import saheel_library_management.project.Library_Management_System.RequestDto.AuthorRequestDto;
import saheel_library_management.project.Library_Management_System.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Set;

@Service
public class AuthorService {

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "name", "email", "gender", "country", "rating");

    @Autowired
    AuthorRepository authorRepository;

    public String saveAuthor(AuthorRequestDto authorRequestDto){
        Author author= AuthorConverter.convertAuthorRequestDtoToAuthorModel(authorRequestDto);
        authorRepository.save(author);
        return "author saved successfully !";
    }

    public Author getAuthorById(int id){
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("author not found with id " + id));
    }

    public List<Author> getAllAuthor() {
        List<Author> authorList = authorRepository.findAll();
        return authorList;
    }

    public String deleteById(int id){
        authorRepository.deleteById(id);
        return "author with id:"+id+"is got deleted";
    }

    public String updateById(int id, AuthorRequestDto newAuthorRequestDto){
        Author existingAuthor = getAuthorById(id);
        existingAuthor.setName(newAuthorRequestDto.getName());
        existingAuthor.setEmail(newAuthorRequestDto.getEmail());
        existingAuthor.setGender(newAuthorRequestDto.getGender());
        existingAuthor.setCountry(newAuthorRequestDto.getCountry());
        existingAuthor.setRating(newAuthorRequestDto.getRating());
        authorRepository.save(existingAuthor);
        return "author updated successfully!";
    }

    public List<Author> getAuthorByPagination(int pageNo, int pageSize, String sortBy, String sortDir) {
        String field = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "id";
        Sort sort = "desc".equalsIgnoreCase(sortDir)
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();
        return authorRepository.findAll(PageRequest.of(pageNo, pageSize, sort)).getContent();
    }

    public Author getByEmail(String email){
        Author author=authorRepository.getAuthorByEmail(email);
        return author;
    }

    public List<Author> getByCountry(String inputCountry){
        List<Author> author=authorRepository.getAuthorByCountry(inputCountry);
        return author;
    }
}
