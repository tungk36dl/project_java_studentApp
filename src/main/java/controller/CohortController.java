package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import beans.Cohort;
import beans.Major;
import utils.IdUtil;

@Controller
public class CohortController extends BaseController {



    @GetMapping("/cohort/cohort_create_form")
    public String showForm(Model model) {
        model.addAttribute("cohort", new Cohort());
        return "cohorts/cohort_create_form";
    }

    @PostMapping("/cohort/save")
    public String save(@ModelAttribute("cohort")Cohort cohort, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "cohorts/cohort_create_form";
        }
        
        String cohortName = cohort.getCohortName();
        
        if(cohortName.isEmpty() || cohortName == null) {
        	model.addAttribute("nameEmpty", "Tên khóa sinh viên chưa được nhập!");
        	model.addAttribute("cohort", new Cohort()); 
            return "cohorts/cohort_create_form"; 
        }
        
        Cohort cohortExist = daoCohort.getCohortByName(cohortName);
        if(cohortExist != null) {
        	model.addAttribute("nameExist", "Khóa sinh viên đã tồn tại!");
        	model.addAttribute("cohort", new Cohort()); 
            return "cohorts/cohort_create_form"; 
        }
        
        cohort.setId(IdUtil.generateId());
        cohort.setCreatedDate(new Date());
        cohort.setUpdatedDate(new Date());
        System.out.println("Dữ liệu: " + cohort);
        daoCohort.save(cohort);
        return "redirect:/cohort/cohort_view";
    }

    @GetMapping("/cohort/cohort_view")
    public String viewCohorts(Model model) {
        List<Cohort> list = daoCohort.getCohort();
        model.addAttribute("list", list);
        return "cohorts/cohort_view";
    }

    @GetMapping("/cohort/editcohort/{id}")
    public String edit(@PathVariable String id, Model model) {
        Cohort cohort = daoCohort.getCohortById(id);
        model.addAttribute("cohort", cohort);
        return "cohorts/cohort_edit_form";
    }

    @PostMapping("/cohort/editsave")
    public String editSave(@ModelAttribute("cohort") Cohort cohort, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("Có lỗi trong dữ liệu: " + result.getAllErrors());
            return "cohorts/cohort_edit_form";
        }
        
        String cohortName = cohort.getCohortName();
        
        if(cohortName.isEmpty() || cohortName == null) {
        	model.addAttribute("nameEmpty", "Tên khóa sinh viên chưa được nhập!");
        	model.addAttribute("cohort", new Cohort()); 
            return "cohorts/cohort_edit_form"; 
        }
                
        Cohort cohortById = daoCohort.getCohortById(cohort.getId());
        Cohort cohortExist = daoCohort.getCohortByName(cohortName);
        if(cohortExist != null && !cohortById.getCohortName().equalsIgnoreCase(cohortName)) { // "kê toán" == "kế toán" <> "ke toan".equals("ke toannnn")
        	model.addAttribute("nameExist", "Khóa sinh viên đã tồn tại!");
        	model.addAttribute("cohort", cohortExist); 
        	return "cohorts/cohort_edit_form";
        }
        
        
        cohort.setUpdatedDate(new Date());
        daoCohort.update(cohort);
        return "redirect:/cohort/cohort_view";
    }

    @GetMapping("/cohort/deletecohort/{id}")
    public String delete(@PathVariable String id) {
        daoCohort.delete(id);
        return "redirect:/cohort/cohort_view";
    }
}
