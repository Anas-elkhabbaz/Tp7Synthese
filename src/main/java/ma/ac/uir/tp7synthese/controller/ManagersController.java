package ma.ac.uir.tp7synthese.controller;

import ma.ac.uir.tp7synthese.entity.*;
import ma.ac.uir.tp7synthese.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                              EvalAssiServiceImpl evalAssiService) {
        this.managersService = (ManagersServiceImpl) managerService;
        this.projectService = (ProjectsServiceImpl) projectService;
        this.developersService = (DevelopersServiceImpl) developersService;
        this.skillsService = (SkillsServiceImpl) skillsService;
        this.evalAssiService = (EvalAssiServiceImpl) evalAssiService;
    }

    @GetMapping("/list")
    public String listManagers(Model model){
        List<Managers> managers = managersService.findAll();
        model.addAttribute("managers", managers);
        return "list-managers";
    }

    @GetMapping("/showFormAdd")
    public String showFormAdd(Model model){
        Managers theManager = new Managers();
        model.addAttribute("manager", theManager);
        return "manager-form";
    }

    @PostMapping("/save")
    public String saveManager(@ModelAttribute("manager") Managers theManager){
        managersService.save(theManager);
        return "redirect:/managers/list";
    }

    @GetMapping("/delete")
    public String deleteManager(@RequestParam("managerId") int id){
        managersService.deleteById(id);
        return "redirect:/managers/list";
    }

    @GetMapping("/loadFormUpdate")
    public String loadFormUpdate(@RequestParam("managerId") int id, Model model){
        Managers theManager = managersService.findById(id);
        model.addAttribute("manager", theManager);
        return "manager-updateForm";
    }

    @PostMapping("/updateManager")
    public String updateManager(@ModelAttribute("manager") Managers theManager){
        managersService.update(theManager);
        return "redirect:/managers/list";
    }

    @GetMapping("/projects")
    public String manageProjects(Model model) {
        List<Projects> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "manageProjects"; // Template pour gérer les projets
    }

    @GetMapping("/developers")
    public String manageDevelopers(Model model) {
        List<Developers> developers = developersService.findAll();
        model.addAttribute("developers", developers);
        return "manageDevelopers"; // Template pour gérer les développeurs
    }

    @GetMapping("/skills")
    public String manageSkills(Model model) {
        List<Skills> skills = skillsService.findAll();
        model.addAttribute("skills", skills);
        return "manageSkills"; // Template pour gérer les compétences
    }

    @GetMapping("/assignments")
    public String manageAssignments(Model model) {
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
        return "redirect:/manager/assignments";
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
                                    @RequestParam int score,
                                    RedirectAttributes redirectAttributes) {
        try {
            EvalAssiServiceImpl.evaluateAssignment(assignmentId, rating, score);
            redirectAttributes.addFlashAttribute("successMessage", "Evaluation successful!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Evaluation failed: " + e.getMessage());
        }
        return "redirect:/manager/evaluations";
    }

    @PostMapping("/projects")
    public String addOrUpdateProject(@ModelAttribute("project") Projects project, RedirectAttributes redirectAttributes) {
        try {
            projectService.save(project); // Assuming saveOrUpdate handles both create and update
            redirectAttributes.addFlashAttribute("successMessage", "Project saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save project: " + e.getMessage());
        }
        return "redirect:/projects"; // Redirect back to the projects page
    }
    @PostMapping("/projects/delete")
    public String deleteProject(@RequestParam("projectId") int projectId, RedirectAttributes redirectAttributes) {
        try {
            projectService.deleteById(projectId); // Delete the project by its ID
            redirectAttributes.addFlashAttribute("successMessage", "Project deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete project: " + e.getMessage());
        }
        return "redirect:/projects"; // Redirect back to the projects page
    }

    @PostMapping("/developers")
    public String addOrUpdateDeveloper(@ModelAttribute("developer") Developers developer, RedirectAttributes redirectAttributes) {
        try {
            developersService.save(developer); // Assuming saveOrUpdate handles both create and update
            redirectAttributes.addFlashAttribute("successMessage", "Developer saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save developer: " + e.getMessage());
        }
        return "redirect:/developers"; // Redirect back to the developers page
    }

    @PostMapping("/developers/delete")
    public String deleteDeveloper(@RequestParam("developerId") int developerId, RedirectAttributes redirectAttributes) {
        try {
            developersService.deleteById(developerId); // Delete the developer by their ID
            redirectAttributes.addFlashAttribute("successMessage", "Developer deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete developer: " + e.getMessage());
        }
        return "redirect:/developers"; // Redirect back to the developers page
    }

    @PostMapping("/skills")
    public String addOrUpdateSkill(@ModelAttribute("skill") Skills skill, RedirectAttributes redirectAttributes) {
        try {
            skillsService.save(skill); // Assuming saveOrUpdate handles both create and update
            redirectAttributes.addFlashAttribute("successMessage", "Skill saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save skill: " + e.getMessage());
        }
        return "redirect:/skills"; // Redirect back to the skills page
    }

    @PostMapping("/skills/delete")
    public String deleteSkill(@RequestParam("skillId") int skillId, RedirectAttributes redirectAttributes) {
        try {
            skillsService.deleteById(skillId); // Delete the skill by its ID
            redirectAttributes.addFlashAttribute("successMessage", "Skill deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete skill: " + e.getMessage());
        }
        return "redirect:/skills"; // Redirect back to the skills page
    }




}
