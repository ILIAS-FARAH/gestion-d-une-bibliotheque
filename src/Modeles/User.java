package Modeles;

public class User {
    
    private int id;
    private String name;
    private String cin;
    private String email;
    private String password;
    private float salary;
    private String job;
    private int admin;

    // Getters and Setters
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCin() {
        return cin;
    }
    public void setCin(String cin) {
        this.cin = cin;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public float getSalary() {
        return salary;
    }
    public void setSalary(float salary) {
        this.salary = salary;
    }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public int getAdmin() {
        return admin;
    }
    public void setAdmin(int admin) {
        this.admin = admin;
    }

    
    // Constructors
    public User() {
        super();
    }

    public User(int id, String name, String cin, String email, String password, float salary, String job, int admin) {
        super();
        this.id = id;
        this.name = name;
        this.cin = cin;
        this.email = email;
        this.password = password;
        this.salary = salary;
        this.job = job;
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", cin=" + cin + ", email=" + email + ", password=" + password
                + ", salary=" + salary + ", job=" + job + ", admin=" + admin +"]";
    }
}
