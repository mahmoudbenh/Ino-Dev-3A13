package models;

public class User {
    private int UserID;
    private String nom,prenom,email,mdp;
    private Role role;

    public static User Current_User;



    public User(){}

    public User(int userID, String nom, String prenom, String mdp,String email, Role role) {
        UserID = userID;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp=mdp;
        this.role=role;
    }

    public User(int userID, String nom, String prenom, String mdp,String email) {
        UserID = userID;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp=mdp;
    }

    public User(int userID, String nom, String prenom,String email) {
        UserID = userID;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        //this.mdp=mdp;
    }


    public User(String nom, String prenom,String mdp, String email, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.mdp= mdp;
        this.email = email;
        this.role = role;

    }

    public User(int UserID,String nom, String prenom) {
        this.UserID=UserID;
        this.nom = nom;
        this.prenom = prenom;

    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static User getCurrent_User() {
        return Current_User;
    }

    public static void setCurrent_User(User Current_User) {
        User.Current_User = Current_User;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserID=" + UserID +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public User(int UserID){
        this.UserID=UserID;
    }
}
