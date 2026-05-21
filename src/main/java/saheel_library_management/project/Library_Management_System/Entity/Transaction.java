package saheel_library_management.project.Library_Management_System.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "transaction_date", nullable = false)
    @CreationTimestamp
    private Date transactiondate;

    @Column(name = "due_date")
    private String dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private saheel_library_management.project.Library_Management_System.Enums.Transaction transactionType;

    @JsonBackReference("card-transactions")
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @JsonBackReference("book-transactions")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(Date transactiondate) {
        this.transactiondate = transactiondate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public saheel_library_management.project.Library_Management_System.Enums.Transaction getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(saheel_library_management.project.Library_Management_System.Enums.Transaction transactionType) {
        this.transactionType = transactionType;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }
}
