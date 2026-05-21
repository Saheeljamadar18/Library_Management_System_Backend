package saheel_library_management.project.Library_Management_System.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import saheel_library_management.project.Library_Management_System.Enums.CardStatus;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "card")
@JsonPropertyOrder({"id", "cardStatus", "expiryDate", "createdDate", "updatedDate", "bookOfCard", "transaction"})
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name = "card_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;
    @Column(name = "expiry_date",nullable = false)
    private String expiryDate;
    @Column(name = "created_Date",nullable = false)
    @CreationTimestamp
    private Date createdDate;
    @Column(name = "updated_Date",nullable = false)
    @UpdateTimestamp
    private Date  updatedDate;
    @JsonBackReference("student-card")
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @JsonManagedReference("card-books")
    @OneToMany(mappedBy = "card")
    private List<Books> bookOfCard;
    @JsonManagedReference("card-transactions")
    @OneToMany(mappedBy = "card")
    private List<Transaction> transaction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Books> getBookOfCard() {
        return bookOfCard;
    }

    public void setBookOfCard(List<Books> bookOfCard) {
        this.bookOfCard = bookOfCard;
    }

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }
}
