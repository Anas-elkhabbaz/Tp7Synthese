package ma.ac.uir.tp7synthese.service;

import ma.ac.uir.tp7synthese.entity.Projects;

import java.util.List;

public interface ProjectsService {
    List<Projects> findAll();

    Projects findById(int theId);

    Projects save(Projects theProject);

    Projects update(Projects theProject);

    void deleteById(int theId);
}
