package ma.ac.uir.tp7synthese.service;

import ma.ac.uir.tp7synthese.DAO.ProjectsRepository;
import ma.ac.uir.tp7synthese.entity.Projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectsServiceImpl implements ProjectsService {

    private ProjectsRepository projectsRepository;

    @Autowired
    public ProjectsServiceImpl(ProjectsRepository theProjectRepository) {
        this.projectsRepository = theProjectRepository;
    }

    @Override
    public List<Projects> findAll() {
        return projectsRepository.findAll();
    }

    @Override
    public Projects findById(int theId) {
        Optional<Projects> result = projectsRepository.findById(theId);

        Projects theProject = null;

        if (result.isPresent()) {
            theProject = result.get();
        }
        else {
            throw new RuntimeException("!find project id - " + theId);
        }
        return theProject;    }

    @Override
    public Projects save(Projects theProject) {
        return projectsRepository.save(theProject);
    }

    @Override
    public Projects update(Projects theProject) {
        return projectsRepository.save(theProject);
    }

    @Override
    public void deleteById(int theId) {
       projectsRepository.deleteById(theId);
    }
}
