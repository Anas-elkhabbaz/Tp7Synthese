package ma.ac.uir.tp7synthese.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Developers")
public class Developers {
    // define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_Developer")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "skills_id", referencedColumnName = "id_Skills")
    private Skills skills;

    @Column(name="experience")
    private int experience;

    @Column(name="disponibility")
    private Boolean disponibility;

    public Developers() {

    }

    public Developers(String name, String login, String password, Skills skills, int experience, Boolean disponibility, String email) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.skills = skills;
        this.experience = experience;
        this.disponibility = disponibility;
        this.email = email;
    }

    public Developers(int id, String name, String login, String password, Skills skills, int experience, Boolean disponibility, String email) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.skills = skills;
        this.experience = experience;
        this.disponibility = disponibility;
        this.email = email;
    }

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

    public void setName(String firstName) {
        this.name = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
    public Boolean getDisposability() {
        return disponibility;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void setDisposability(Boolean disposability) {
        this.disponibility = disposability;
    }
}
