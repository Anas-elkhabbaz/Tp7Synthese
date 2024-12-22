package ma.ac.uir.tp7synthese.controller;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.tp7synthese.entity.*;
import ma.ac.uir.tp7synthese.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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



    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("developer", new Developers());
        model.addAttribute("manager", new Managers());

        return "Login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role,
            Model model,
            HttpSession session) {

        if (role.equalsIgnoreCase("DEVELOPER")) {
            Developers developer = developersService.login(username, password, session);
            if (developer != null) {
                session.setAttribute("loggedInUser", developer);
                return "redirect:/developers/dashboardDeveloper";
            } else {
                model.addAttribute("error", "Invalid username or password for Developer");
                return "Login";
            }
        } else if (role.equalsIgnoreCase("MANAGER")) {
            Managers manager = managersService.login(username, password, session);
            if (manager != null) {
                session.setAttribute("loggedInUser", manager);
                return "redirect:/managers/projects";
            } else {
                model.addAttribute("error", "Invalid username or password for Manager");
                return "Login";
            }
        } else {
            model.addAttribute("error", "Invalid role selected");
            return "Login";
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("developer", new Developers());
        model.addAttribute("manager", new Managers());
        model.addAttribute("skillsList", skillsService.findAll());
        model.addAttribute("disponibility", true);
        // Par défaut : Available
        List<Developers> developers = developersService.findAll();
        List<Skills> skills = skillsService.findAll();
        List<DevSkills> devSkills = devSkillsService.findAll();
        // Add skills and managers to the model
        model.addAttribute("skills", skills);
        model.addAttribute("devskills", devSkills);
        model.addAttribute("developers", developers);
// Prepare the form object
        return "CreateAccount"; // Return the registration form view (register.html)
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("name") String name,
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("role") String role,
            @RequestParam(required = false) List<Integer> skillIds,
            @RequestParam(required = false) Boolean disponibility,
            Model model) {

        try {
            if ("developer".equalsIgnoreCase(role)) {
                Developers developer = new Developers();
                developer.setName(name);
                developer.setLogin(login);
                developer.setPassword(password);
                developer.setEmail(email);
                developer.setDisponibility(disponibility != null && disponibility);

                developersService.save(developer);

                if (skillIds != null) {
                    for (Integer skillId : skillIds) {
                        Skills skill = skillsService.findById(skillId);
                        DevSkills devSkills = new DevSkills();
                        devSkills.setDevelopers(developer);
                        devSkills.setSkills(skill);
                        devSkillsService.save(devSkills);
                    }
                }
                return "redirect:/developers/login";

            } else if ("manager".equalsIgnoreCase(role)) {
                Managers manager = new Managers();
                manager.setName(name);
                manager.setLogin(login);
                manager.setPassword(password);
                manager.setEmail(email);

                managersService.save(manager);
                return "redirect:/developers/login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error during registration: " + e.getMessage());
            e.printStackTrace(); // Log the error
        }

        return "Login";
    }





    @GetMapping("dashboardDeveloper")
    public String showDashboard(Model model,HttpSession session) {
        // Get the logged-in developer
        Developers developer = developersService.getLoggedInDeveloper(session);
        if (developer == null) {
            return "redirect:/developers/login";  // Redirige si aucun développeur n'est connecté
        }
        List<Projects> assignedProjects = evalAssiService.findProjectsByDeveloper(developer);
        List<Skills> skills = devSkillsService.findSkillsByDeveloper(developer);
        List<EvalAssi> evaluation = evalAssiService.findAssignedProject(developer);
        System.out.println(assignedProjects);
        System.out.println(skills);
        System.out.println(developer);
        model.addAttribute("evaluation", evaluation);
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

        return "redirect:/developers/dashboardDeveloper"; // Redirect back to the skills page
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("developer", new Developers());
        model.addAttribute("manager", new Managers());
        return "Mdp_oublie";
    }
    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestParam String email,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model) {

        Developers developer = developersService.findByEmail(email);
        Managers manager = managersService.findByEmail(email);

        if (developer == null && manager == null) {
            model.addAttribute("error", "Email not found");
            return "Mdp_oublie";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "Mdp_oublie";
        }

        developer.setPassword(newPassword);
        developersService.save(developer);

        model.addAttribute("success", "Password successfully reset");
        return "redirect:/developers/login";
    }

    @GetMapping("/skills/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Skills skill = skillsService.findById(id);  // Utilisation de Optional
        if (skill != null) {
            model.addAttribute("skill", skill);  // Si la compétence existe, ajoutez-la au modèle
            return "editSkillsD"; // Renvoie la vue pour éditer la compétence
        } else {
            redirectAttributes.addFlashAttribute("error", "Skill not found!");  // Si la compétence n'existe pas
            return "redirect:/developers/dashboardDeveloper";  // Redirige vers la liste des compétences
        }
    }


    // Update a skill (reuses save functionality)
    @PostMapping("/skills/edit/{id}")
    public String updateSkill(@PathVariable int id, @ModelAttribute Skills updatedSkill, RedirectAttributes redirectAttributes) {
            updatedSkill.setId(id);
            skillsService.save(updatedSkill);
            redirectAttributes.addFlashAttribute("message", "Skill updated successfully!");

        return "redirect:/developers/dashboardDeveloper";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam("name") String name,
                                @RequestParam("email") String email) {
        // Récupérer l'utilisateur connecté (developer) basé sur le principal
        Developers developer = developersService.findByEmail(email);

        // Mettre à jour les informations de l'utilisateur
        developer.setName(name);
        developer.setEmail(email);

        // Sauvegarder les changements dans la base de données
        developersService.update(developer);

        // Rediriger vers le tableau de bord avec un message de succès
        return "redirect:/developers/profile";
    }










}