package saheel_library_management.project.Library_Management_System.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import saheel_library_management.project.Library_Management_System.Entity.Card;
import saheel_library_management.project.Library_Management_System.RequestDto.CardRequestDto;
import saheel_library_management.project.Library_Management_System.Service.CardService;

import java.util.List;

@RestController
@RequestMapping("/card/apis")
public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping("/save")
    public String saveCard(@Valid @RequestBody CardRequestDto cardRequestDto){
        return cardService.saveCard(cardRequestDto);
    }

    @GetMapping("/find/{id}")
    public Card findCardById(@PathVariable int id){
        return cardService.getCardById(id);
    }

    @GetMapping("/findAll")
    public List<Card> findAll(){
        return cardService.getAllCard();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCardById(@PathVariable int id){
        return cardService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public String updateCard(@PathVariable int id, @Valid @RequestBody CardRequestDto cardRequestDto){
        return cardService.updateById(id, cardRequestDto);
    }

    @GetMapping("/findAllByPage")
    public List<Card> findAllByPage(
            @RequestParam(name = "pageNo", required = false) Integer pageNo,
            @RequestParam(name = "PageNo", required = false) Integer pageNoLegacy,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
        int resolvedPageNo = pageNo != null ? pageNo : (pageNoLegacy != null ? pageNoLegacy : 0);
        return cardService.getCardByPagination(resolvedPageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/findByStudentId")
    public Card getByStudentId(@RequestParam int studentId){
        return cardService.getByStudentId(studentId);
    }

    @GetMapping("/findByStatus")
    public List<Card> getByStatus(@RequestParam String Status){
        return cardService.getByStatus(Status);
    }
}
