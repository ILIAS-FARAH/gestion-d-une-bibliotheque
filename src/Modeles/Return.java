package Modeles;

import java.util.Date;

public class Return {
    private int id;
    private int idClient;
    private String clientName;
    private int idBook;
    private String bookName;
    private Date date1; // Start date (e.g., borrowing date)
    private Date date2; // End date (e.g., due date)
    private Date date3; // Return date
    private float prix; // Price or fine

    // Constructor
    public Return(int id, int idClient, String clientName, int idBook, String bookName, Date date1, Date date2, Date date3, float prix) {
        this.id = id;
        this.idClient = idClient;
        this.clientName = clientName;
        this.idBook = idBook;
        this.bookName = bookName;
        this.date1 = date1;
        this.date2 = date2;
        this.date3 = date3;
        this.prix = prix;
    }

    // Default Constructor
    public Return() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Date getDate3() {
        return date3;
    }

    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    // toString Method
    @Override
    public String toString() {
        return "Return{" +
                "id=" + id +
                ", idClient=" + idClient +
                ", clientName='" + clientName + '\'' +
                ", idBook=" + idBook +
                ", bookName='" + bookName + '\'' +
                ", date1=" + date1 +
                ", date2=" + date2 +
                ", date3=" + date3 +
                ", prix=" + prix +
                '}';
    }
}
