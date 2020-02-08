package pl.coderslab.model;

import org.mindrot.jbcrypt.BCrypt;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private int userGroupId;


    public User(int id, String name, String email, String password, int userGroupId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.hashPassword(password);
        this.userGroupId = userGroupId;
    }


    public void hashPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }
}
