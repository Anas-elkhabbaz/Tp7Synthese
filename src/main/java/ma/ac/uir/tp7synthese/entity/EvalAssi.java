package ma.ac.uir.tp7synthese.entity;


import jakarta.persistence.*;

@Entity
@Table(name="EvalAssi")
public class EvalAssi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_EvalAssi")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_Developer", referencedColumnName = "id_Developer")
    private Developers developers;

    @ManyToOne
    @JoinColumn(name = "id_Project", referencedColumnName = "id_Project")
    private Projects projects;

    @Column(name="Rating")
    private int rating;

    @Column(name="Feedback")
    private String feedback;




    public EvalAssi() {

    }

    public EvalAssi( Developers developers, Projects projects, int rating, String feedback ) {
        this.developers = developers;
        this.projects = projects;
        this.rating = rating;


    }

    public EvalAssi( Developers developers, Projects projects, int rating, String feedback, int id ) {
        this.id = id;
        this.developers = developers;
        this.projects = projects;
        this.rating = rating;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Developers getDevelopers() {
        return developers;
    }

    public void setDevelopers(Developers developers) {
        this.developers = developers;
    }

    public Projects getProjects() {
        return projects;
    }

    public void setProjects(Projects projects) {
        this.projects = projects;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}