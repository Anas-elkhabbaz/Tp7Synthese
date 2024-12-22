package ma.ac.uir.tp7synthese.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Managers")
public class Managers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_manager")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="login")
    private String login;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;


    public Managers() {

    }

    public Managers(String name, String login, String password, String email) {
        this.name = name;
        this.login = login;
        this.password = password;

    }

    public Managers(int id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
