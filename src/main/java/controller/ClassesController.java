package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import beans.Classes;
import beans.Cohort;
import beans.Major;
import response.ClassesResponse;
import utils.IdUtil;

@Controller
public class ClassesController extends BaseController {

   
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
    public String save(@ModelAttribute("classes")Classes classes, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "classes/classes_create_form";
        }
        
        String className = classes.getClassName();
        
        if(className.isEmpty() || className == null) {
        	model.addAttribute("nameEmpty", "Tên lớp học chưa được nhập!");
        	model.addAttribute("classes", new Classes()); 
        	return "classes/classes_create_form";
        	}
        
        Classes classesExist = daoClasses.getClassesByMajorIdAndCohortId(classes.getMajorId(), classes.getCohortId());
        if(classesExist != null && classesExist.getClassName().equalsIgnoreCase(className)) {
        	model.addAttribute("nameExist", "Lớp học đã tồn tại!");
        	model.addAttribute("classes", new Classes()); 
        	return "classes/classes_create_form";
        }

        classes.setId(IdUtil.generateId());
        classes.setCreatedDate(new Date());
        classes.setUpdatedDate(new Date());
        System.out.println("Dữ liệu: " + classes);
        daoClasses.save(classes);
        return "redirect:/classes/classes_view";
    }

    @GetMapping("/classes/classes_view")
    public String viewClasses(Model model) {
        List<Classes> list = daoClasses.getAll();
        List<ClassesResponse> list1 = new ArrayList<>();
        for(Classes cl : list) {
        	ClassesResponse clr = new ClassesResponse();
        	clr.setId(cl.getId());
        	clr.setClassName(cl.getClassName());
        	clr.setStatus(cl.isStatus());
        	clr.setCreatedDate(cl.getCreatedDate());
        	clr.setUpdatedDate(cl.getUpdatedDate());
        	Major major = daoMajor.getMajorById(cl.getMajorId());
        	if (major != null) {
        	    clr.setMajorName(major.getMajorName());
        	} else {
        	    clr.setMajorName("Unknown"); // hoặc để null tùy trường hợp
        	}
        	
        	// cohort tuong tu
        	Cohort cohort = daoCohort.getCohortById(cl.getCohortId());
        	if (cohort != null) {
        	    clr.setCohortName(cohort.getCohortName());
        	} else {
        	    clr.setCohortName("Unknown"); 
        	}
        	
        	list1.add(clr);
        	
        }
        model.addAttribute("list", list1);
        return "classes/classes_view";
    }

    @GetMapping("/classes/editclasses/{id}")
    public String edit(@PathVariable String id, Model model) {
        Classes classes = daoClasses.getClassesById(id);
        List<Major> majors = daoMajor.getMajor();
        List<Cohort> cohorts = daoCohort.getCohort();

        model.addAttribute("classes", classes);
        model.addAttribute("majors", majors);
        model.addAttribute("cohorts", cohorts);
        return "classes/classes_edit_form";
    }


    @PostMapping("/classes/editsave")
    public String editSave(@ModelAttribute("classes") Classes classes, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("Có lỗi trong dữ liệu: " + result.getAllErrors());
            return "classes/classes_edit_form";
        }
        
    String className = classes.getClassName();
        
        if(className.isEmpty() || className == null) {
        	model.addAttribute("nameEmpty", "Tên lớp học chưa được nhập!");
            List<Major> majors = daoMajor.getMajor();
            List<Cohort> cohorts = daoCohort.getCohort();

            model.addAttribute("classes", classes);
            model.addAttribute("majors", majors);
            model.addAttribute("cohorts", cohorts);
            return "classes/classes_edit_form";
        }
                
        Classes classesById = daoClasses.getClassesById(classes.getId());
        Classes classesExist = daoClasses.getClassesByClassName(className);
        if(classesExist != null && !classesById.getClassName().equalsIgnoreCase(className)) { 
        	model.addAttribute("nameExist", "Lớp học đã tồn tại!");
            List<Major> majors = daoMajor.getMajor();
            List<Cohort> cohorts = daoCohort.getCohort();

            model.addAttribute("classes", classes);
            model.addAttribute("majors", majors);
            model.addAttribute("cohorts", cohorts);
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
    
    @GetMapping("/classes/addClasses")
    public String addClasses(Model model) {
    	List<Major> majors = daoMajor.getMajor();
    	List<Cohort> cohorts = daoCohort.getCohort();
    	model.addAttribute("majors", majors);
    	model.addAttribute("cohorts", cohorts);
    	model.addAttribute("classes", new Classes());
    	return "classes/classes_create_form";
    }
}
