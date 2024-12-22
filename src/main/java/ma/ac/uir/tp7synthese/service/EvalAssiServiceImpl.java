package ma.ac.uir.tp7synthese.service;

import ma.ac.uir.tp7synthese.DAO.EvalAssiRepository;
import ma.ac.uir.tp7synthese.DAO.ProjectsRepository;
import ma.ac.uir.tp7synthese.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvalAssiServiceImpl implements EvalAssiService {
    private final DevelopersService developersService;
    private EvalAssiRepository evalAssiRepository;
    private ProjectsService projectsService;

    @Autowired
    public EvalAssiServiceImpl(EvalAssiRepository theEvalAssiRepository, DevelopersService developersService,
                               ProjectsService projectsService) {
        this.evalAssiRepository = theEvalAssiRepository;
        this.developersService = developersService;
        this.projectsService = projectsService;
    }

    public void evaluateAssignment(int assignmentId, int rating, String feedback) {
        // Logique de mise à jour dans la base de données
        Optional<EvalAssi> assignmentOptional = evalAssiRepository.findById(assignmentId);
        if (assignmentOptional.isPresent()) {
            EvalAssi assignment = assignmentOptional.get();
            assignment.setRating(rating);
            assignment.setFeedback(feedback);
            evalAssiRepository.save(assignment);
        }
    }


    @Override
    public List<EvalAssi> findAll() {
        return evalAssiRepository.findAll();
    }

    @Override
    public EvalAssi findById(int theId) {
        Optional<EvalAssi> result = evalAssiRepository.findById(theId);

        EvalAssi theEvalAssi = null;

        if (result.isPresent()) {
            theEvalAssi = result.get();
        }
        else {
            throw new RuntimeException("!find evalAssi id - " + theId);
        }
        return theEvalAssi;
    }

    @Override
    public EvalAssi save(EvalAssi theEvalAssi) {
        return evalAssiRepository.save(theEvalAssi);
    }

    @Override
    public EvalAssi update(EvalAssi theEvalAssi) {
        return evalAssiRepository.save(theEvalAssi);
    }

    @Override
    public void deleteById(int theId) {
        evalAssiRepository.deleteById(theId);
    }

    public List<Projects> findProjectsByDeveloper(Developers developer) {
        List<EvalAssi> devProjectsList = evalAssiRepository.findByDevelopers(developer);
        return devProjectsList.stream()
                .map(EvalAssi::getProjects) // Extract skills from each DevSkills
                .collect(Collectors.toList());
    }

    public List<EvalAssi> findAssignedProject(Developers developer) {
        List<EvalAssi> devProjectsListe = evalAssiRepository.findByDevelopers(developer);
        return devProjectsListe;
    }

    public void assignDeveloperToProject(int developerId, int projectId) {
        // Récupérer le développeur et le projet depuis leurs services
        Developers developer = developersService.findById(developerId);
        Projects project = projectsService.findById(projectId);


        // Créer une nouvelle évaluation d'affectation
        EvalAssi evalAssi = new EvalAssi();
        evalAssi.setDevelopers(developer);
        evalAssi.setProjects(project);
        //evalAssi.setRating(rating);
        //evalAssi.setFeedback(feedback);

        // Sauvegarder l'affectation dans la base de données
        evalAssiRepository.save(evalAssi);  // Assurez-vous que evalAssiService.save() est correctement défini dans le service
    }

}
