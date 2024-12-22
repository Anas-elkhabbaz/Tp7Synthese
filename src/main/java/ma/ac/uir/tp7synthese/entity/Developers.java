package ma.ac.uir.tp7synthese.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Developers")
public class Developers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Developer")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "developers")
    private List<DevSkills> devSkills;

    @Column(name = "experience")
    private int experience;

    @Column(name = "disponibility")
    private Boolean disponibility;

    // Constructors
    public Developers() {
    }

    public Developers(String name, String login, String password, int experience, Boolean disponibility, String email,List<DevSkills> devSkills) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.experience = experience;
        this.disponibility = disponibility;
        this.email = email;
        this.devSkills = devSkills;
    }

    public Developers(int id, String name, String login, String password, int experience, Boolean disponibility, String email,List<DevSkills> devSkills) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.experience = experience;
        this.disponibility = disponibility;
        this.email = email;
        this.devSkills = devSkills;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<DevSkills> getDevSkills() {
        return devSkills;
    }

    public void setDevSkills(List<DevSkills> devSkills) {
        this.devSkills = devSkills;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Boolean getDisponibility() {
        return disponibility;
    }

    public void setDisponibility(Boolean disponibility) {
        this.disponibility = disponibility;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
