package ma.ac.uir.tp7synthese.controller;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.tp7synthese.entity.*;
import ma.ac.uir.tp7synthese.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/managers")
public class ManagersController {

    @Autowired
    private DevelopersServiceImpl developersService;
    private ManagersServiceImpl managersService;
    private SkillsServiceImpl skillsService;
    private ProjectsServiceImpl projectService;
    private DevSkillsServiceImpl devSkillsService;
    private EvalAssiServiceImpl evalAssiService;

    @Autowired
    public ManagersController(ManagersService managerService,ProjectsServiceImpl projectService,
                              DevelopersServiceImpl developersService,SkillsServiceImpl skillsService,
                              EvalAssiServiceImpl evalAssiService,DevSkillsServiceImpl devSkillsService) {
        this.managersService = (ManagersServiceImpl) managerService;
        this.projectService = (ProjectsServiceImpl) projectService;
        this.developersService = (DevelopersServiceImpl) developersService;
        this.skillsService = (SkillsServiceImpl) skillsService;
        this.evalAssiService = (EvalAssiServiceImpl) evalAssiService;
        this.devSkillsService = (DevSkillsServiceImpl) devSkillsService;
    }


    @GetMapping("/developers")
    public String manageDevelopers(Model model) {
        System.out.println("Bien passer");
        List<Developers> developers = developersService.findAll();
        List<Skills> skills = skillsService.findAll();
        List<DevSkills> devSkills = devSkillsService.findAll();
        // Add skills and managers to the model
        model.addAttribute("skills", skills);
        model.addAttribute("devskills", devSkills);
        model.addAttribute("developers", developers);
        return "manageDevelopers"; // Template pour gérer les développeurs
    }

    @PostMapping("/developers")
    public String addDeveloper(@ModelAttribute("developer") Developers developer, @RequestParam List<Integer> skillIds
            ,RedirectAttributes redirectAttributes,@RequestParam Boolean disponibility) {
        try {
            boolean isAvailable = (disponibility != null && disponibility);
            developer.setDisponibility(isAvailable);
            // Save the developer (this assumes the developer is being saved correctly)
            developersService.save(developer);

            // Assuming 'skillIds' is a list of skill IDs the developer selected
            for (Integer skillId : skillIds) {
                System.out.println(skillId);
                Skills skill = skillsService.findById(skillId); // Fetch each skill from the database
                DevSkills devSkills = new DevSkills();
                devSkills.setDevelopers(developer);  // Associate the developer with the skill
                devSkills.setSkills(skill);          // Associate the skill with the developer
                devSkillsService.save(devSkills);   // Save the entry to the DEVSkills table
            }

            // Add success message and redirect
            redirectAttributes.addFlashAttribute("successMessage", "Developer and skills saved successfully!");
        } catch (Exception e) {
            // Handle errors
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save developer: " + e.getMessage());
        }

        // Redirect back to the developers page
        return "redirect:/managers/developers";
    }

    @GetMapping("/developers/edit/{id}")
    public String editDeveloper(@PathVariable("id") Integer developerId, Model model) {
        // Récupérer le projet à partir de l'ID
        Developers developers = developersService.findById(developerId);
        if (developers != null) {
            model.addAttribute("developer", developers);
            List<Skills> skills = skillsService.findAll();

            // Add skills and managers to the model
            model.addAttribute("skills", skills);
            return "editDeveloper"; // Template pour éditer un projet
        } else {
            // Gérer le cas où le projet n'existe pas
            return "redirect:/managers/developers"; // Rediriger vers la liste des projets
        }
    }

    @PostMapping("/developers/edit/{id}")
    public String updateDeveloper(
            @ModelAttribute("developer") Developers developers,
            @PathVariable("id") Integer id,
            RedirectAttributes redirectAttributes,
            Model model,
            @RequestParam(value = "skills", required = false) List<Skills> skills
    ) {
        try {
            // Trouver le développeur existant par ID
            Developers existingDeveloper = developersService.findById(id);
            if (existingDeveloper == null) {
                System.out.println("Developer not found");
                redirectAttributes.addFlashAttribute("errorMessage", "Developer not found!");
                return "redirect:/managers/developers";
            }
            System.out.println(existingDeveloper);

            // Mise à jour des informations du développeur
            existingDeveloper.setName(developers.getName());
            existingDeveloper.setLogin(developers.getLogin());
            existingDeveloper.setPassword(developers.getPassword());
            existingDeveloper.setExperience(developers.getExperience());
            existingDeveloper.setDisponibility(developers.getDisponibility());
            existingDeveloper.setEmail(developers.getEmail());

            // Mettre à jour les compétences du développeur
            if (skills != null && !skills.isEmpty()) {
                List<DevSkills> updatedDevSkills = new ArrayList<>();
                for (Skills skill : skills) {
                    if (skill != null) {
                        DevSkills devSkill = new DevSkills();
                        devSkill.setDevelopers(existingDeveloper);
                        devSkill.setSkills(skill);
                        updatedDevSkills.add(devSkill);
                    }
                }
                // Supprimer les anciennes compétences associées
                devSkillsService.deleteDevSkillsByDeveloperId(id);

                // Ajouter les nouvelles compétences
                existingDeveloper.setDevSkills(updatedDevSkills);
            } else {
                // Si aucune compétence n'est sélectionnée, supprimer toutes les compétences existantes
                devSkillsService.deleteDevSkillsByDeveloperId(id);
                existingDeveloper.setDevSkills(new ArrayList<>()); // Vide la liste des compétences
            }

            // Sauvegarder les modifications du développeur
            developersService.save(existingDeveloper);
            redirectAttributes.addFlashAttribute("successMessage", "Developer updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();  // Log l'exception pour plus de détails
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update developer: " + e.getMessage());
        }
        // Redirection vers la liste des développeurs
        return "redirect:/managers/developers";
    }




    @PostMapping("/developers/delete")
    public String deleteDeveloper(@RequestParam("developerId") int developerId, RedirectAttributes redirectAttributes) {
        try {

            System.out.println("Attempting to delete Developer ID: " + developerId);
            developersService.deleteById(developerId);

            redirectAttributes.addFlashAttribute("successMessage", "Developer deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete developer: " + e.getMessage());
        }
        return "redirect:/managers/developers"; // Redirect back to the developers page
    }




    @PostMapping("/projects")
    public String addProject(
            @ModelAttribute("project") Projects project,
            RedirectAttributes redirectAttributes,
            Model model,
            @RequestParam(value = "managerId", required = false) Integer managerId,
            @RequestParam(value = "skills", required = false) List<String> skills
    ) {
        try {
            if (managerId != null) {
                Managers manager = managersService.findById(managerId);
                if (manager != null) {
                    project.setManagers(manager);
                }
            }

            if (skills != null && !skills.isEmpty()) {
                String skillsString = String.join(",", skills.stream().map(String::valueOf).toArray(String[]::new));
                project.setRequired_skills(skillsString);
            }

            projectService.save(project); // Ajouter le projet
            redirectAttributes.addFlashAttribute("successMessage", "Project added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add project: " + e.getMessage());
        }
        return "redirect:/managers/projects"; // Redirection vers la liste des projets
    }
    @GetMapping("/projects")
    public String manageProjects(Model model,@RequestParam(value = "id", required = false) Integer projectId,HttpSession session) {
        Managers manager = managersService.getLoggedInManager(session);
        if (manager == null) {
            return "redirect:/developers/login";  // Redirige si aucun développeur n'est connecté
        }
        List<Projects> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        Projects projectForm = (projectId != null) ? projectService.findById(projectId) : new Projects();
        List<Skills> skills = skillsService.findAll();
        List<Managers> managers = managersService.findAll();

        model.addAttribute("projectForm", projectForm);
        model.addAttribute("skills", skills);
        model.addAttribute("managers", managers);
        return "manageProjects"; // Template pour gérer les projets
    }

    @GetMapping("/projects/edit/{id}")
    public String editProject(@PathVariable("id") Integer projectId, Model model) {
        // Récupérer le projet à partir de l'ID
        Projects project = projectService.findById(projectId);
        if (project != null) {
            model.addAttribute("project", project);
            List<Skills> skills = skillsService.findAll();
            List<Managers> managers = managersService.findAll();

            // Add skills and managers to the model
            model.addAttribute("skills", skills);
            model.addAttribute("managers", managers);
            return "editProject"; // Template pour éditer un projet
        } else {
            // Gérer le cas où le projet n'existe pas
            return "redirect:/managers/projects"; // Rediriger vers la liste des projets
        }
    }


    @PostMapping("/projects/edit/{id}")
    public String updateProject(
            @ModelAttribute("project") Projects project,
            @PathVariable("id") Integer id,
            RedirectAttributes redirectAttributes,
            Model model,
            @RequestParam(value = "managerId", required = false) Integer managerId,
            @RequestParam(value = "skills", required = false) List<String> skills
    ) {
        try {
            Projects existingProject = projectService.findById(id);
            if (existingProject == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Project not found!");
                return "redirect:/managers/projects";
            }

            // Mise à jour des informations du projet
            existingProject.setTitle(project.getTitle());
            existingProject.setDescription(project.getDescription());
            existingProject.setEstimated_duration((project.getEstimated_duration()));

            if (managerId != null) {
                Managers manager = managersService.findById(managerId);
                if (manager != null) {
                    existingProject.setManagers(manager);
                }
            }

            if (skills != null && !skills.isEmpty()) {
                String skillsString = String.join(",", skills.stream().map(String::valueOf).toArray(String[]::new));
                existingProject.setRequired_skills(skillsString);
            }

            projectService.save(existingProject); // Mettre à jour le projet
            redirectAttributes.addFlashAttribute("successMessage", "Project updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update project: " + e.getMessage());
        }
        return "redirect:/managers/projects"; // Redirection vers la liste des projets
    }


    @PostMapping("/projects/delete")
    public String deleteProject(@RequestParam("projectId") int projectId, RedirectAttributes redirectAttributes) {
        try {
            projectService.deleteById(projectId); // Delete the project by its ID
            redirectAttributes.addFlashAttribute("successMessage", "Project deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete project: " + e.getMessage());
        }
        return "redirect:/managers/projects"; // Redirect back to the projects page
    }


    @GetMapping("/skills")
    public String manageSkills(Model model,@RequestParam(value = "id", required = false) Integer skillId) {
        List<Skills> skills = skillsService.findAll();
        model.addAttribute("skills", skills);
        Skills skillForm = (skillId != null) ? skillsService.findById(skillId) : new Skills();


        model.addAttribute("skillForm", skillForm);
        return "manageSkills"; // Template pour gérer les compétences
    }

    @PostMapping("/skills")
    public String addSkill(@ModelAttribute("skill") Skills skill, RedirectAttributes redirectAttributes) {
        try {
            // Vérifier si la compétence existe déjà (facultatif)
            if (skillsService.findByName(skill.getName()) != null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Skill already exists!");
                return "redirect:/managers/skills";
            }

            skillsService.save(skill); // Sauvegarde la nouvelle compétence
            redirectAttributes.addFlashAttribute("successMessage", "Skill added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add skill: " + e.getMessage());
        }
        return "redirect:/managers/skills"; // Redirige vers la page des compétences
    }

    @PostMapping("skills/edit/{id}")
    public String updateSkill(
            @PathVariable("id") Integer id,
            @ModelAttribute("skill") Skills updatedSkill,
            RedirectAttributes redirectAttributes) {
        try {
            Skills existingSkill = skillsService.findById(id); // Trouver la compétence par ID
            if (existingSkill == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Skill not found!");
                return "redirect:/managers/skills";
            }

            // Mise à jour des propriétés
            existingSkill.setName(updatedSkill.getName());

            skillsService.save(existingSkill); // Enregistrer la compétence mise à jour
            redirectAttributes.addFlashAttribute("successMessage", "Skill updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update skill: " + e.getMessage());
        }
        return "redirect:/managers/skills"; // Redirige vers la page des compétences
    }

    @GetMapping("/skills/edit/{id}")
    public String editSkills(@PathVariable("id") Integer skillId, Model model) {
        // Récupérer le skill à partir de l'ID
        Skills skill = skillsService.findById(skillId);
        if (skill != null) {
            model.addAttribute("skill", skill);
            List<Skills> skills = skillsService.findAll();

            // Add skills and managers to the model
            model.addAttribute("skills", skills);
            return "editSkills"; // Template pour éditer un projet
        } else {
            // Gérer le cas où le projet n'existe pas
            return "redirect:/managers/skills"; // Rediriger vers la liste des projets
        }
    }



    @PostMapping("/skills/delete")
    public String deleteSkill(@RequestParam("skillId") int skillId, RedirectAttributes redirectAttributes) {
        try {
            skillsService.deleteById(skillId); // Delete the skill by its ID
            redirectAttributes.addFlashAttribute("successMessage", "Skill deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete skill: " + e.getMessage());
        }
        return "redirect:/managers/skills"; // Redirect back to the skills page
    }

    @GetMapping("/assignments")
    public String manageAssignments(Model model, HttpSession session) {
        Managers managers = managersService.getLoggedInManager(session); // Exemple de méthode pour récupérer le développeur connecté
        //List<Projects> projects = projectService.findProjectsByManager(managers);
        List<Projects> projects = projectService.findAll();
        List<Developers> developers = developersService.findAll();
        model.addAttribute("projects", projects);
        model.addAttribute("developers", developers);
        return "manageAssignments"; // Template pour gérer les assignations
    }

    @PostMapping("/assignments")
    public String assignDeveloperToProject(@RequestParam int projectId,
                                           @RequestParam int developerId,
                                           RedirectAttributes redirectAttributes) {
        try {
            evalAssiService.assignDeveloperToProject(developerId, projectId);
            redirectAttributes.addFlashAttribute("successMessage", "Assignment successful!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Assignment failed: " + e.getMessage());
        }
        return "redirect:/managers/assignments";
    }

    @GetMapping("/evaluations")
    public String manageEvaluations(Model model) {
        List<EvalAssi> evaluations = evalAssiService.findAll();

        model.addAttribute("evaluations", evaluations);

        return "manageEvaluations"; // Template pour gérer les évaluations
    }

    @PostMapping("/evaluations")
    public String evaluateDeveloper(@RequestParam int assignmentId,
                                    @RequestParam int rating,
                                    @RequestParam String feedback,
                                    RedirectAttributes redirectAttributes) {
        try {
            evalAssiService.evaluateAssignment(assignmentId, rating, feedback);

            redirectAttributes.addFlashAttribute("successMessage", "Evaluation successful!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Evaluation failed: " + e.getMessage());
        }
        return "redirect:/managers/evaluations";
    }




}
