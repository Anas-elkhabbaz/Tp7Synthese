package ma.ac.uir.tp7synthese.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_Skills")
    private int id;

    @Column(name="name")
    private String name;

    // Constructeurs
    public Skills() {
    }

    public Skills(String name) {
        this.name = name;
    }
    public Skills(int id,String name) {
        this.id = id;
        this.name = name;
    }

    // Getters et Setters
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

    // Méthode toString
    @Override
    public String toString() {
        return "Skills{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
