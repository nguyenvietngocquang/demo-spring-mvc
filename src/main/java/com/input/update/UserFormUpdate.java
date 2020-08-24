package com.input.update;

import com.model.User;
import com.validator.Password;
import org.hibernate.validator.constraints.NotBlank;

public class UserFormUpdate {

    private Long id;
    private String userName;
    private String email;
    private String name;
    @Password
    private String password;

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserFormUpdate(){
    }

    public UserFormUpdate(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
