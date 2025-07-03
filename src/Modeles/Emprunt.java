package Modeles;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Emprunt {

    private int id;
    private int idClient; 
    private String clientName;  
    private int idBook;
    private String bookName;
    private Date date1;
    private Date date2;

    // Constructor
    public Emprunt(int id, int idClient, String clientName, int idBook, String bookName, Date date1, Date date2) {
        super();
        this.id = id;
        this.idClient = idClient;
        this.clientName = clientName;  
        this.idBook = idBook;
        this.bookName = bookName; 
        this.date1 = date1;
        this.date2 = date2;
    }

    public Emprunt() {
        super();
    }

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
    
    public long calculateDaysBetweenDates() {
        if (date1 == null || date2 == null) {
            throw new IllegalStateException("Both dates must be set to calculate the difference.");
        }

        long diffInMillis = date2.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
    
    public long calculateDaysFromToday() {
        if (date1 == null) {
            throw new IllegalStateException("date1 must be set to calculate the difference.");
        }

        Date today = new Date(); 
        long diffInMillis = today.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
    public long calculatePrix() {
        long diffInMillis = calculateDaysBetweenDates()*5 ;
        if(calculateDaysFromToday()>=0) {
        	diffInMillis=diffInMillis+((calculateDaysFromToday()-calculateDaysBetweenDates())*10);
        }
        return diffInMillis;
    }
    
    public boolean isDate2PassedToday() {
        if (date2 == null) {
            throw new IllegalStateException("date2 must be set to determine if it has passed today.");
        }

        // Get today's date without the time component
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Parse today's date to remove the time part
            today = sdf.parse(sdf.format(today));
        } catch (Exception e) {
            // Should never happen
            e.printStackTrace();
            return false;
        }

        return date2.before(today);
    }
    

public String formatDate(Date date) {
    if (date == null) {
        return "N/A";
    }
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    return formatter.format(date);
}

    @Override
    public String toString() {
        return "Emprunt{" +
               "id=" + id +
               ", idClient=" + idClient +
               ", clientName='" + clientName + '\'' +
               ", idBook=" + idBook +
               ", bookName='" + bookName + '\'' +
               ", date1=" + formatDate(date1) +
               ", date2=" + formatDate(date2) +
               '}';
    }

}
