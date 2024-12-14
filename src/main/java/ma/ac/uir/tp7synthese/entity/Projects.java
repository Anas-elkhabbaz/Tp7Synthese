package ma.ac.uir.tp7synthese.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Projects")
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_Project")
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="Description")
    private String description;

    @Column(name="estimated_duration")
    private String estimated_duration;

    @Column(name="required_skills")
    private String required_skills;

    @ManyToOne
    @JoinColumn(name = "id_Manager", referencedColumnName = "id_manager")
    private Managers managers;


    public Projects() {

    }

    public Projects(int id, String title, String description, String estimated_duration, String required_skills ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.estimated_duration = estimated_duration;
        this.required_skills = required_skills;

    }

    public Projects(String title, String description, String estimated_duration, String required_skills ) {
        this.title = title;
        this.description = description;
        this.estimated_duration = estimated_duration;
        this.required_skills = required_skills;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstimated_duration() {
        return estimated_duration;
    }

    public void setEstimated_duration(String estimated_duration) {
        this.estimated_duration = estimated_duration;
    }

    public String getRequired_skills() {
        return required_skills;
    }

    public void setRequired_skills(String required_skills) {
        this.required_skills = required_skills;
    }
}
