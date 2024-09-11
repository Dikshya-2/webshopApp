package dk.tec.webshopapp.model;

public class User {
   // @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String email;
    public String password;
    public boolean isAdmin;
    public boolean isMember;
}
