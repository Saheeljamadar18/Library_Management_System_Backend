package saheel_library_management.project.Library_Management_System.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import saheel_library_management.project.Library_Management_System.Entity.Books;
import saheel_library_management.project.Library_Management_System.RequestDto.BookRequestDto;
import saheel_library_management.project.Library_Management_System.Service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books/apis")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("/save")
    public String saveBook(@Valid @RequestBody BookRequestDto bookRequestDto){
        return bookService.saveBook(bookRequestDto);
    }

    @GetMapping("/find/{id}")
    public Books findBookById(@PathVariable int id){
        return bookService.getBookById(id);
    }

    @GetMapping("/findAll")
    public List<Books> findAll(){
        return bookService.getAllBook();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBookById(@PathVariable int id){
        return bookService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public String updateBook(@PathVariable int id, @Valid @RequestBody BookRequestDto bookRequestDto){
        return bookService.updateById(id, bookRequestDto);
    }

    @GetMapping("/findAllByPage")
    public List<Books> findAllByPage(
            @RequestParam(name = "pageNo", required = false) Integer pageNo,
            @RequestParam(name = "PageNo", required = false) Integer pageNoLegacy,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "book_id") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
        int resolvedPageNo = pageNo != null ? pageNo : (pageNoLegacy != null ? pageNoLegacy : 0);
        return bookService.getBookByPagination(resolvedPageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/findByTitle")
    public Books getByTitle(@RequestParam String title){
        return bookService.getByTitle(title);
    }

    @GetMapping("/findByCategory")
    public List<Books> getByCategory(@RequestParam String Category){
        return bookService.getByCategory(Category);
    }

    @GetMapping("/search")
    public List<Books> searchBooks(@RequestParam String q){
        return bookService.searchBooks(q);
    }
}
