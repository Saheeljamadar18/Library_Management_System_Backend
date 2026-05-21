package saheel_library_management.project.Library_Management_System.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import saheel_library_management.project.Library_Management_System.Entity.Author;
import saheel_library_management.project.Library_Management_System.RequestDto.AuthorRequestDto;
import saheel_library_management.project.Library_Management_System.Service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/author/apis")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @PostMapping("/save")
    public String saveAuthor(@Valid @RequestBody AuthorRequestDto authorRequestDto){
        return authorService.saveAuthor(authorRequestDto);
    }

    @GetMapping("/find/{id}")
    public Author findAuthorById(@PathVariable int id){
        return authorService.getAuthorById(id);
    }

    @GetMapping("/findAll")
    public List<Author> findAll(){
        return authorService.getAllAuthor();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAuthorById(@PathVariable int id){
        return authorService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public String updateAuthor(@PathVariable int id, @Valid @RequestBody AuthorRequestDto authorRequestDto){
        return authorService.updateById(id, authorRequestDto);
    }

    @GetMapping("/findAllByPage")
    public List<Author> findAllByPage(
            @RequestParam(name = "pageNo", required = false) Integer pageNo,
            @RequestParam(name = "PageNo", required = false) Integer pageNoLegacy,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
        int resolvedPageNo = pageNo != null ? pageNo : (pageNoLegacy != null ? pageNoLegacy : 0);
        return authorService.getAuthorByPagination(resolvedPageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/findByEmail")
    public Author getByEmail(@RequestParam String email){
        return authorService.getByEmail(email);
    }

    @GetMapping("/findByCountry")
    public List<Author> getByCountry(@RequestParam String Country){
        return authorService.getByCountry(Country);
    }
}
