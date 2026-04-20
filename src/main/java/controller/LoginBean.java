package controller;

import dao.UserDAO;
import models.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private User loggedInUser;
    private UserDAO userDAO = new UserDAO();

    public String login() {
        System.out.println("Attempting login for: " + username);
        loggedInUser = userDAO.validate(username, password);

        if (loggedInUser != null) {
            System.out.println("Login Success!");
            return "index?faces-redirect=true";
        } else {
            System.out.println("Login Failed - User not found in DB");
            return "login?faces-redirect=true";
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public User getLoggedInUser() { return loggedInUser; }
}