package controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import beans.Subject;

import dao.DaoSubject;
import utils.IdUtil;

@Controller
public class SubjectController {
	@Autowired
	private DaoSubject daoSubject;


	    @GetMapping("/subject/subject_create_form")
	    public String showForm(Model model) {
	        model.addAttribute("subject", new Subject(null, null, false, null, null));
	        return "subjects/subject_create_form";
	    }

	    @PostMapping("/subject/save")
	    public String save(@ModelAttribute("subject") Subject sub, BindingResult result) {
	        if (result.hasErrors()) {
	            return "subject_create_form";
	        }
	        System.out.println("Dữ liệu sản phẩm: " + sub);
	        
	        sub.setId(IdUtil.generateId());
	        sub.setCreatedDate(new Date());
	        sub.setUpdatedDate(new Date());
	        daoSubject.save(sub);
	        return "redirect:/subject/subject_view";
	    }

	    @GetMapping("/subject/subject_view")
	    public String viewProducts(Model model) {
	        List<Subject> list = daoSubject.getSubject();
	        model.addAttribute("list", list);
	        return "subjects/subject_view";
	    }

	    @GetMapping("/subject/editsubject/{id}")
	    public String edit(@PathVariable String id, Model model) {
	    	Subject sub = daoSubject.getSubjectById(id);
	        model.addAttribute("subject", sub);
	        return "subjects/subject_edit_form";
	    }

	    @PostMapping("subject/editsave")
	    public String editSave(@ModelAttribute("product") Subject subject, BindingResult result) {
	        if (result.hasErrors()) {
	            System.out.println("Có lỗi trong dữ liệu: " + result.getAllErrors());
	            return "subject_edit_form";
	        }
	        subject.setUpdatedDate(new Date());

	        daoSubject.update(subject);
	        return "redirect:/subject/subject_view";
	    }

	    @GetMapping("/subject/deletesubject/{id}")
	    public String delete(@PathVariable String id) {
	        daoSubject.delete(id);
	        return "redirect:/subject/subject_view";
	    }
}
