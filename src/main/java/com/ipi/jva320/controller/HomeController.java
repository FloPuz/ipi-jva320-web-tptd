package com.ipi.jva320.controller;

import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @GetMapping("/")
    public ModelAndView home() {
        // C'est la view "home" qui sera utilisée pour constuire la réponse => fichier home.html
        ModelAndView modelAndView = new ModelAndView("home");
        //Get des datas
        String message = "Nouveaux message paramétré depuis le controller, alors bienvenue sur cette application de gestion RH !";
        Long nbSalarie = salarieAideADomicileService.countSalaries();

        //Mapping à la page home.html
        modelAndView.addObject("message",message);
        modelAndView.addObject("nbSalarie",nbSalarie);
        return modelAndView;
    }
}
