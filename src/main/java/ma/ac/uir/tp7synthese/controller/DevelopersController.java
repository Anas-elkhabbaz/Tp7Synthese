package ma.ac.uir.tp7synthese.controller;

import ma.ac.uir.tp7synthese.entity.Developers;
import ma.ac.uir.tp7synthese.service.DevelopersService;
import ma.ac.uir.tp7synthese.service.DevelopersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/developers")
public class DevelopersController {

    @Autowired
    private DevelopersServiceImpl developersService;

    @PostMapping("/login")
    public Developers login(@RequestParam String username, @RequestParam String password) {
        return developersService.login(username, password);
    }
}
