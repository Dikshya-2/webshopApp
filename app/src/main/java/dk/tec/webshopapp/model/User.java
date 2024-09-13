package dk.tec.webshopapp.model;

public class User {
    // @PrimaryKey(autoGenerate = true)
    public int id;
    public String email;
    public String password;
    public String role;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
