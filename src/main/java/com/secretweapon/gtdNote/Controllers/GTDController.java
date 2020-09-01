package com.secretweapon.gtdNote.Controllers;

import com.secretweapon.gtdNote.Models.Data.GTDDao;
import com.secretweapon.gtdNote.Controllers.UserController;
import com.secretweapon.gtdNote.Models.Data.UserDao;
import com.secretweapon.gtdNote.Models.GTD;
import com.secretweapon.gtdNote.Models.Data.GTDDao;
import com.secretweapon.gtdNote.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.taglibs.TagLibConfig;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Scanner;

@Controller
@RequestMapping("gtd")

public class GTDController {
    @OneToOne
    public UserController userController;

    @Autowired
    private GTDDao gtdDao;

    @Autowired
    private UserDao userDao;



    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayHomePage(){
        return "home";
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("paos", gtdDao.findAll());
        model.addAttribute("title", "My GTD");
        return "gtd/index";
    }


    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String displayEditGTDForm(Model model) {
        model.addAttribute("title", "Add GTD");
        model.addAttribute(new GTD());
        return "gtd/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEditPaoForm(@ModelAttribute @Valid GTD newPao,
                                     Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Pao");
            return "pao/edit";
        }

        gtdDao.save(newGTD);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemovePaoForm(HttpSession session, Model model) {

//        if (userController.getUserFromSession(session) == null){
//            model.addAttribute("title", "Login");
//            model.addAttribute("users", userDao.findAll());
//            return "redirect:login";
//        }

        model.addAttribute("GTDs", gtdDao.findAll());
        model.addAttribute("title", "Remove GTD");
        return "gtd/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveGTDForm(@RequestParam int[] gtdIds){
        for(int gtdId : gtdIds) {
            paoDao.deleteById(gtdId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "train", method = RequestMethod.GET)
    public String displayTrainForm(Model model) {
        model.addAttribute("gtds", gtdDao.findAll());
        return "gtd/flashcards";
    }

    @RequestMapping(value = "train", method = RequestMethod.POST)
    public String processTrainForm(Model model, @RequestParam int[] gtdIds){
        model.addAttribute("gtds", gtdDao.findAll());
        model.addAttribute("gtdId", gtdIds);

        return "gtd/flashcards";
    }

}



