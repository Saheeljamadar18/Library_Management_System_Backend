package saheel_library_management.project.Library_Management_System.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import saheel_library_management.project.Library_Management_System.Enums.Category;

import java.util.List;

@Entity
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int book_id;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "publisherName",nullable = false)
    private String publisherName;
    @Column(name = "publishedDate",nullable = false)
    private String publishedDate;
    @Column(name = "pages",nullable = false)
    private int pages;
    @Column(name = "availability",nullable = false)
    private boolean availability;
    @Enumerated(EnumType.STRING)
    @Column(name = "category",nullable = false)
    private Category category;
    @Column(name = "rackNo",nullable = false)
    private int rackNo;
    @JsonBackReference("author-books")
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
    @JsonBackReference("card-books")
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    @JsonManagedReference("book-transactions")
    @OneToMany(mappedBy = "book")
    private List<Transaction> transactionsOfBook;
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getRackNo() {
        return rackNo;
    }

    public void setRackNo(int rackNo) {
        this.rackNo = rackNo;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<Transaction> getTransactionsOfBook() {
        return transactionsOfBook;
    }

    public void setTransactionsOfBook(List<Transaction> transactionsOfBook) {
        this.transactionsOfBook = transactionsOfBook;
    }
}
