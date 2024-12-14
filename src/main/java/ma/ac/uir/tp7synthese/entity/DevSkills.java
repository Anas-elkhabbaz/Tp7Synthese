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
}
