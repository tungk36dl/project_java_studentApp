package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import beans.Classes;
import dao.DaoClasses;

import utils.IdUtil;

@Controller
public class ClassesController {

    @Autowired
    private DaoClasses daoClasses;

//    @GetMapping("/")
//    public String redirectToClassesView() {
//        return "redirect:/classes_view";
//    }

    @GetMapping("/classes/classes_create_form")
    public String showForm(Model model) {
        model.addAttribute("classes", new Classes());
        return "classes/classes_create_form";
    }

    @PostMapping("/classes/save")
    public String save(@ModelAttribute("classes")Classes classes, BindingResult result) {
        if (result.hasErrors()) {
            return "classes_create_form";
        }
        classes.setId(IdUtil.generateId());
        classes.setCreatedDate(new Date());
        classes.setUpdatedDate(new Date());
        System.out.println("Dữ liệu: " + classes);
        daoClasses.save(classes);
        return "redirect:classes/classes_view";
    }

    @GetMapping("/classes/classes_view")
    public String viewClasses(Model model) {
        List<Classes> list = daoClasses.getClasses();
        model.addAttribute("list", list);
        return "classes/classes_view";
    }

    @GetMapping("/classes/editclasses/{id}")
    public String edit(@PathVariable String id, Model model) {
        Classes classes = daoClasses.getClassesById(id);
        model.addAttribute("classes", classes);
        return "classes/classes_edit_form";
    }

    @PostMapping("/classes/editsave")
    public String editSave(@ModelAttribute("classes") Classes classes, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Có lỗi trong dữ liệu: " + result.getAllErrors());
            return "classes/classes_edit_form";
        }
        classes.setUpdatedDate(new Date());
        
        
        daoClasses.update(classes);
        return "redirect:/classes/classes_view";
    }

    @GetMapping("/classes/deleteclasses/{id}")
    public String delete(@PathVariable String id) {
        daoClasses.delete(id);
        return "redirect:/classes/classes_view";
    }
}
