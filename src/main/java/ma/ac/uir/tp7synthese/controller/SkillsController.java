package ma.ac.uir.tp7synthese.controller;

import ma.ac.uir.tp7synthese.entity.Skills;
import ma.ac.uir.tp7synthese.DAO.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/skills")
public class SkillsController {

    @Autowired
    private SkillsRepository skillsRepository;

    // List all skills
    @GetMapping
    public String listSkills(Model model) {
        List<Skills> skills = skillsRepository.findAll();
        model.addAttribute("skillsList", skills);
        return "skills/list"; // Refers to skills/list.html
    }

    // Show form to create a new skill
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("skill", new Skills());
        return "skills/form"; // Refers to skills/form.html
    }

    // Save a new skill
    @PostMapping("/save")
    public String saveSkill(@ModelAttribute Skills skill, RedirectAttributes redirectAttributes) {
        skillsRepository.save(skill);
        redirectAttributes.addFlashAttribute("message", "Skill saved successfully!");
        return "redirect:/skills";
    }

    // Show form to edit a skill
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Skills> skill = skillsRepository.findById(id);
        if (skill.isPresent()) {
            model.addAttribute("skill", skill.get());
            return "skills/form"; // Refers to skills/form.html (reuse for both create and edit)
        } else {
            redirectAttributes.addFlashAttribute("error", "Skill not found!");
            return "redirect:/skills";
        }
    }

    // Update a skill (reuses save functionality)
    @PostMapping("/update/{id}")
    public String updateSkill(@PathVariable int id, @ModelAttribute Skills updatedSkill, RedirectAttributes redirectAttributes) {
        if (skillsRepository.existsById(id)) {
            updatedSkill.setId(id);
            skillsRepository.save(updatedSkill);
            redirectAttributes.addFlashAttribute("message", "Skill updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Skill not found!");
        }
        return "redirect:/skills";
    }

    // Delete a skill
    @GetMapping("/delete/{id}")
    public String deleteSkill(@PathVariable int id, RedirectAttributes redirectAttributes) {
        if (skillsRepository.existsById(id)) {
            skillsRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Skill deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Skill not found!");
        }
        return "redirect:/skills";
    }
}
