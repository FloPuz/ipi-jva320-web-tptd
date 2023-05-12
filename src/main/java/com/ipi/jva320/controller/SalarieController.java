package com.ipi.jva320.controller;

import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.repository.SalarieAideADomicileRepository;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SalarieController {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    /*
    @GetMapping("/salaries")
    public ModelAndView displayAllSalarie(){
        ModelAndView modelAndView = new ModelAndView("list");
        List<SalarieAideADomicile> salarieAideADomicileList = salarieAideADomicileService.getSalaries();
        modelAndView.addObject("salaries", salarieAideADomicileList);
        return modelAndView;
    }
*/
    @RequestMapping(value = {"/salarie/addUpdate", "/salarie/addUpdate/{id}"})
    public ModelAndView displayAddUpdateSalarie(@PathVariable(required = false) Optional<Long> id){
        ModelAndView modelAndView = new ModelAndView("detail_Salarie");

        //Si pas d'Id on fourni le model à la vue pour le add
        if (id.isEmpty()){
            //create
            SalarieAideADomicile salarieAideADomicile = new SalarieAideADomicile();
            modelAndView.addObject("salarie",salarieAideADomicile);
        }else{
            //Update
            SalarieAideADomicile salarieAideADomicile = salarieAideADomicileService.getSalarie(id.get());
            modelAndView.addObject("salarie",salarieAideADomicile);
        }
        Long nbSalarie = salarieAideADomicileService.countSalaries();
        modelAndView.addObject("nbSalarie",nbSalarie);

        return modelAndView;
    }


    /***
     * Add or Update a new Salarie based on the id field in detail_Salarie
     * @param salarieAideADomicile
     * @param bindingResult
     * @return
     */
    @PostMapping(value = {"/salarie/addUpdate"})
    public ModelAndView addUpdateSalarieById(SalarieAideADomicile salarieAideADomicile, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("detail_Salarie");
        List<String> errorList = new ArrayList<>();
        SalarieAideADomicile updatedSalarie = new SalarieAideADomicile();
        try{
            if (salarieAideADomicile.getId() == null){
                updatedSalarie = salarieAideADomicileService.creerSalarieAideADomicile(salarieAideADomicile);
            }else {
                updatedSalarie = salarieAideADomicileService.updateSalarieAideADomicile(salarieAideADomicile);
            }
            modelAndView.addObject("salarie",updatedSalarie);
            return modelAndView;
        }catch (Exception e){
            //Traitement à faire sur les erreurs pour le bindingResult

            return modelAndView;
        }
    }


    @RequestMapping(value = "/salarie/findByNom")
    public ModelAndView findSalariesByNom(String name){
        ModelAndView modelAndView = new ModelAndView("list");
        List<SalarieAideADomicile> salarieAideADomicileList = salarieAideADomicileService.getSalaries(name);
        modelAndView.addObject("salaries",salarieAideADomicileList);
        return modelAndView;
    }

    @RequestMapping(value = "/salaries", method = RequestMethod.GET)
    public ModelAndView displaySalariePagination(@RequestParam("page") Optional<Integer> page,
                                                 @RequestParam("size") Optional<Integer> size,
                                                 @RequestParam("sortProperty") String sortField,
                                                 @RequestParam("sortDirection") String sortDirection
                                                 ){
        ModelAndView modelAndView = new ModelAndView("list");

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        if (sortField.isEmpty()){
            sortField = "id";
        }
        if (sortDirection.isEmpty()){
            sortDirection = "ASC";
        }

        /**
         * TODO
         * Si je veux faire la pagination et qu'elle soit répercuté je dois passer à ma page web la current page, et la current size à chaque fois.
         * Donc je dois modifier les boutons pour changer de page pour qu'ils soient dynamique
         * Je dois bien récupérer et remapper les arguments au niveau du endpoint
         * On va rechecke la methode getPaginated aussi
         *
         */
        Page<SalarieAideADomicile> salarieAideADomicilePage = salarieAideADomicileService.getPaginated(PageRequest.of(currentPage, pageSize),sortDirection,sortField);

        modelAndView.addObject("salaries", salarieAideADomicilePage);
        Long nbSalarie = salarieAideADomicileService.countSalaries();
        modelAndView.addObject("nbSalarie",nbSalarie);


        return modelAndView;
    }


}


