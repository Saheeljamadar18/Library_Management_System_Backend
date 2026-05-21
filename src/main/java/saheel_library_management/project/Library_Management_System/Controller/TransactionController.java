package saheel_library_management.project.Library_Management_System.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saheel_library_management.project.Library_Management_System.Entity.Transaction;
import saheel_library_management.project.Library_Management_System.RequestDto.IssueBookRequestDto;
import saheel_library_management.project.Library_Management_System.RequestDto.ReturnBookRequestDto;
import saheel_library_management.project.Library_Management_System.RequestDto.TransactionRequestDto;
import saheel_library_management.project.Library_Management_System.Service.TransactionService;
import saheel_library_management.project.Library_Management_System.response.TransactionResponseDto;

import java.util.List;

@RestController
@RequestMapping("/transaction/apis")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/issue")
    public ResponseEntity<TransactionResponseDto> issueBook(@Valid @RequestBody IssueBookRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.issueBook(request));
    }

    @PostMapping("/return")
    public ResponseEntity<TransactionResponseDto> returnBook(@Valid @RequestBody ReturnBookRequestDto request) {
        return ResponseEntity.ok(transactionService.returnBook(request));
    }

    @PostMapping("/save")
    public String saveTransaction(@Valid @RequestBody TransactionRequestDto transactionRequestDto) {
        return transactionService.saveTransaction(transactionRequestDto);
    }

    @GetMapping("/find/{id}")
    public Transaction findTransactionById(@PathVariable int id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/findAll")
    public List<Transaction> findAll() {
        return transactionService.getAllTransaction();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTransactionById(@PathVariable int id) {
        return transactionService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public String updateTransaction(@PathVariable int id, @Valid @RequestBody TransactionRequestDto transactionRequestDto) {
        return transactionService.updateById(id, transactionRequestDto);
    }

    @GetMapping("/findAllByPage")
    public List<Transaction> findAllByPage(
            @RequestParam(name = "pageNo", required = false) Integer pageNo,
            @RequestParam(name = "PageNo", required = false) Integer pageNoLegacy,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
        int resolvedPageNo = pageNo != null ? pageNo : (pageNoLegacy != null ? pageNoLegacy : 0);
        return transactionService.getTransactionByPagination(resolvedPageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/findByBookId")
    public List<Transaction> getByBookId(@RequestParam int bookId) {
        return transactionService.getByBookId(bookId);
    }

    @GetMapping("/findByCardId")
    public List<Transaction> getByCardId(@RequestParam int cardId) {
        return transactionService.getByCardId(cardId);
    }
}
