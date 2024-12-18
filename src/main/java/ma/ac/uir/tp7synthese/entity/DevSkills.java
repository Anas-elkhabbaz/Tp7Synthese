package ma.ac.uir.tp7synthese.entity;

import jakarta.persistence.*;

@Entity
@Table(name="devSkills")
public class DevSkills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_DevSkills")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_Developer", referencedColumnName = "id_Developer")
    private Developers developers;

    @ManyToOne
    @JoinColumn(name = "id_Skills", referencedColumnName = "id_Skills")
    private Skills skills;

    public DevSkills(int id, Skills skills, Developers developers) {
        this.id = id;
        this.skills = skills;
        this.developers = developers;
    }
    public DevSkills(Skills skills, Developers developers) {
        this.skills = skills;
        this.developers = developers;
    }
    public DevSkills() {}

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

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }
}
