package controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import beans.Cohort;
import beans.Subject;

import dao.DaoSubject;
import utils.IdUtil;

@Controller
public class SubjectController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);


	    @GetMapping("/subject/subject_create_form")
	    public String showForm(Model model) {
	        model.addAttribute("subject", new Subject());
	        return "subjects/subject_create_form";
	    }

	    @PostMapping("/subject/save")
	    public String save(@ModelAttribute("subject") Subject sub, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	        	LOGGER.debug("Lỗi");
	            return "subjects/subject_create_form";
	        }
	        LOGGER.debug("code: {}", sub.getCode());
	        String code = sub.getCode();
	        if(code.isEmpty() || code == null) {
	        	model.addAttribute("codeEmpty", "Mã môn học chưa được nhập!");
	        	model.addAttribute("subject", new Subject()); 
	        	return "subjects/subject_create_form";	        }
	        
	        Subject subjectExist = daoSubject.getSubjectByCode(sub.getCode());
	        if(subjectExist != null) {
	        	model.addAttribute("nameExist", "Mã môn học đã tồn tại!");
	        	model.addAttribute("subject", new Subject()); 
	        	return "subjects/subject_create_form";
	        }
	        
	        
	        String subjectName = sub.getName();
	        if(subjectName.isEmpty() || subjectName == null) {
	        	model.addAttribute("nameEmpty", "Tên môn học chưa được nhập!");
	        	model.addAttribute("subject", new Subject()); 
	        	return "subjects/subject_create_form";	        }
	        
	        Subject subjectExist1 = daoSubject.getSubjectByName(subjectName);
	        if(subjectExist1 != null) {
	        	model.addAttribute("nameExist", "Môn học đã tồn tại!");
	        	model.addAttribute("subject", new Subject()); 
	        	return "subjects/subject_create_form";
	        }
	        
	        sub.setId(IdUtil.generateId());
	        sub.setCreatedDate(new Date());
	        sub.setUpdatedDate(new Date());
	        daoSubject.save(sub);
	        return "redirect:/subject/subject_view";
	    }

	    @GetMapping("/subject/subject_view")
	    public String viewProducts(Model model) {
	        List<Subject> list = daoSubject.getAll();
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
	    public String editSave(@ModelAttribute("subject") Subject subject, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            System.out.println("Có lỗi trong dữ liệu: " + result.getAllErrors());
	            return "subjects/subject_edit_form";
	        }
	        
	        String subjectName = subject.getName();
	        
	        if(subjectName.isEmpty() || subjectName == null) {
	        	model.addAttribute("nameEmpty", "Tên môn học chưa được nhập!");
	        	model.addAttribute("subject", new Subject()); 
	            return "subjects/subject_edit_form"; 
	        }
	                
	        Subject subjectById = daoSubject.getSubjectById(subject.getId());
	        Subject subjectExist = daoSubject.getSubjectByName(subjectName);
	        if(subjectExist != null && !subjectById.getName().equalsIgnoreCase(subjectName)) { 
	        	model.addAttribute("nameExist", "Khóa sinh viên đã tồn tại!");
	        	model.addAttribute("subject", new Subject()); 
	            return "subjects/subject_edit_form"; 
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
