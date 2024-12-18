package ma.ac.uir.tp7synthese.controller;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.tp7synthese.DAO.SkillsRepository;
import ma.ac.uir.tp7synthese.entity.*;
import ma.ac.uir.tp7synthese.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/developers")
public class DevelopersController {

    @Autowired
    private DevelopersServiceImpl developersService;
    private ManagersServiceImpl managersService;
    private SkillsServiceImpl skillsService;
    private ProjectsServiceImpl projectService;
    private DevSkillsServiceImpl devSkillsService;
    private EvalAssiServiceImpl evalAssiService;

    @Autowired
    public DevelopersController(DevelopersServiceImpl developersService,ManagersServiceImpl managersService,
                                SkillsServiceImpl skillsService,ProjectsServiceImpl projectService,
                                DevSkillsServiceImpl devSkillsService,EvalAssiServiceImpl evalAssiService) {
        this.developersService = developersService;
        this.managersService = managersService;
        this.skillsService = skillsService;
        this.projectService = projectService;
        this.devSkillsService = devSkillsService;
        this.evalAssiService = evalAssiService;
    }

    @GetMapping("/list")
    public String listDevelopers(Model model) {
        List<Developers> developers = developersService.findAll();
        model.addAttribute("developers", developers);
        return "list-developers";
    }

    @GetMapping("/showFormAdd")
    public String showFormAdd(Model model) {
        Developers developer = new Developers();
        model.addAttribute("developer", developer);
        return "developer-form";
    }

    @PostMapping("/save")
    public String saveDeveloper(@ModelAttribute("developer") Developers developer) {
        developersService.save(developer);
        return "redirect:/developers/list";
    }

    @GetMapping("/delete")
    public String deleteDeveloper(@RequestParam("developerId") int id) {
        developersService.deleteById(id);
        return "redirect:/developers/list";
    }

    @GetMapping("/loadFormUpdate")
    public String loadFormUpdate(@RequestParam("developerId") int id, Model model) {
        Developers developer = developersService.findById(id);
        model.addAttribute("developer", developer);
        return "developer-updateForm";
    }

    @PostMapping("/update")
    public String updateDeveloper(@ModelAttribute("developer") Developers developer) {
        developersService.update(developer);
        return "redirect:/developers/list";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("developer", new Developers());
        model.addAttribute("manager", new Managers());

        return "Login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, Model model, HttpSession session) {
        Developers developer = developersService.login(login, password,session);
        Managers managers = managersService.login(login, password,session);

        if (developer != null) {
            model.addAttribute("developer", developer);
            return "redirect:/developers/dashboardDeveloper";
        } else if (managers != null) {
            model.addAttribute("manager", managers);
            return "redirect:/managers/dashboardManager";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "Login";
        }
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("developer") Developers developer, @ModelAttribute("manager") Managers manager, @RequestParam("role") String role) {
        if ("developer".equalsIgnoreCase(role)) {
            developersService.save(developer);
            return "redirect:/developers/login";// Save to Developer table
        } else if ("manager".equalsIgnoreCase(role)) {
            managersService.save(manager);
            return "redirect:/developers/login";// Assuming you have a managerService to save data to Manager table
        }
        return "CreateAccount";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("developer", new Developers());
        model.addAttribute("manager", new Managers());
        model.addAttribute("skillsList", skillsService.findAll());
        model.addAttribute("disponibility", true); // Par défaut : Available
// Prepare the form object
        return "CreateAccount"; // Return the registration form view (register.html)
    }

    @GetMapping("dashboardDeveloper")
    public String showDashboard(Model model,HttpSession session) {
        // Get the logged-in developer
        Developers developer = developersService.getLoggedInDeveloper(session); // Exemple de méthode pour récupérer le développeur connecté
        List<Projects> assignedProjects = evalAssiService.findProjectsByDeveloper(developer);
        List<Skills> skills = devSkillsService.findSkillsByDeveloper(developer);

        model.addAttribute("developer", developer);
        model.addAttribute("assignedProjects", assignedProjects);
        model.addAttribute("skills", skills);

        return "dashboardDeveloper"; // Template Thymeleaf pour le dashboard
    }

    @GetMapping("/projects")
    public String manageProjects(Model model,HttpSession session) {
        Developers developer = developersService.getLoggedInDeveloper(session);
        List<Projects> assignedProjects = evalAssiService.findProjectsByDeveloper(developer);
        model.addAttribute("assignedProjects", assignedProjects);
        return "projectsDeveloper"; // Template pour afficher les projets
    }

    @GetMapping("/skills")
    public String manageSkills(Model model,HttpSession session) {
        Developers developer = developersService.getLoggedInDeveloper(session);
        List<Skills> skills = devSkillsService.findSkillsByDeveloper(developer);
        model.addAttribute("skills", skills);
        return "skilsDeveloper"; // Template pour gérer les compétences
    }

    @GetMapping("/profile")
    public String manageProfile(Model model,HttpSession session) {
        Developers developer = developersService.getLoggedInDeveloper(session);
        model.addAttribute("developer", developer);
        return "profilDeveloper"; // Template pour les informations personnelles
    }

    @PostMapping("/skills")
    public String updateSkills(@RequestParam("skillIds") List<Integer> skillIds,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        Developers developer = developersService.getLoggedInDeveloper(session);

        try {
            // Loop through skillIds to associate each skill with the developer
            for (Integer skillId : skillIds) {
                Skills skill = skillsService.findById(skillId); // Use the existing SkillsService to find a skill
                if (skill != null) {
                    DevSkills devSkill = new DevSkills(skill, developer); // Create a DevSkills object
                    devSkillsService.update(devSkill); // Update the developer's skills
                }
            }
            redirectAttributes.addFlashAttribute("successMessage", "Skills updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update skills: " + e.getMessage());
        }

        return "redirect:/skills"; // Redirect back to the skills page
    }



}