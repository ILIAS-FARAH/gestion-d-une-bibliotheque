package Modeles;

public class Book {

    private int id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String type;

    // Getters and Setters
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Book() {
        super();
    }

    public Book(int id, String title, String author, String publisher, String isbn, String type) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.type = type;
    }

    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", publisher=" + publisher
                + ", isbn=" + isbn + ", type=" + type + "]";
    }
}


