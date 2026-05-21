-- Full schema for empty production databases (e.g. Render sql8827716)

CREATE TABLE IF NOT EXISTS student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    dept VARCHAR(255) NOT NULL,
    sem VARCHAR(255) NOT NULL,
    gender VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    dob VARCHAR(255) NOT NULL,
    mobile VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS author (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    gender VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    rating VARCHAR(255) NOT NULL,
    CONSTRAINT uk_author_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS card (
    id INT AUTO_INCREMENT PRIMARY KEY,
    card_status ENUM('Active', 'InActive', 'Blocked') NOT NULL,
    created_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    expiry_date VARCHAR(255) NOT NULL,
    student_id INT UNIQUE,
    CONSTRAINT fk_card_student FOREIGN KEY (student_id) REFERENCES student (id)
);

CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publisher_name VARCHAR(255) NOT NULL,
    published_date VARCHAR(255) NOT NULL,
    pages INT NOT NULL,
    availability BIT(1) NOT NULL,
    category ENUM('IT_TECHNICAL', 'NON_IT', 'COMEDY', 'DRAMA', 'HORROR', 'NOVELS', 'FICTION') NOT NULL,
    rack_no INT NOT NULL,
    author_id INT,
    card_id INT,
    CONSTRAINT fk_books_author FOREIGN KEY (author_id) REFERENCES author (id),
    CONSTRAINT fk_books_card FOREIGN KEY (card_id) REFERENCES card (id)
);

CREATE TABLE IF NOT EXISTS transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    due_date VARCHAR(255),
    transaction_type ENUM('BORROW', 'RETURN') NOT NULL,
    card_id INT,
    book_id INT,
    CONSTRAINT fk_tx_card FOREIGN KEY (card_id) REFERENCES card (id),
    CONSTRAINT fk_tx_book FOREIGN KEY (book_id) REFERENCES books (id)
);
