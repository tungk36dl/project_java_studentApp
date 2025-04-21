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

import beans.Major;  // Thay Subject thành Major
import dao.DaoMajor;  // Thay DaoSubject thành DaoMajor
import utils.IdUtil;

@Controller
public class MajorController {
    
    @Autowired
    private DaoMajor daoMajor;  // Thay DaoSubject thành DaoMajor

    @GetMapping("/major/major_create_form")
    public String showForm(Model model) {
        model.addAttribute("major", new Major(null, null, false, null, null)); // Thay Subject thành Major
        return "majors/major_create_form";  // Đảm bảo đường dẫn và tên form là đúng
    }

    @PostMapping("/major/save")
    public String save(@ModelAttribute("major") Major major, BindingResult result) {  // Thay subject thành major
        if (result.hasErrors()) {
            return "majors/major_create_form";  // Đảm bảo đường dẫn và tên form là đúng
        }
        System.out.println("Dữ liệu ngành học: " + major);
        
        major.setId(IdUtil.generateId());
        major.setCreatedDate(new Date());
        major.setUpdatedDate(new Date());
        daoMajor.save(major);  // Thay daoSubject thành daoMajor
        return "redirect:/major/major_view";  // Cập nhật lại URL nếu cần
    }

    @GetMapping("/major/major_view")
    public String viewMajors(Model model) {
        List<Major> list = daoMajor.getMajor();  // Thay daoSubject thành daoMajor
        model.addAttribute("list", list);
        return "majors/major_view";  // Đảm bảo đường dẫn và tên view là đúng
    }

    @GetMapping("/major/editmajor/{id}")
    public String edit(@PathVariable String id, Model model) {
        Major major = daoMajor.getMajorById(id);  // Thay daoSubject thành daoMajor
        model.addAttribute("major", major);  // Thay subject thành major
        return "majors/major_edit_form";  // Đảm bảo đường dẫn và tên form là đúng
    }

    @PostMapping("/major/editsave")
    public String editSave(@ModelAttribute("major") Major major, BindingResult result) {  // Thay subject thành major
        if (result.hasErrors()) {
            System.out.println("Có lỗi trong dữ liệu: " + result.getAllErrors());
            return "majors/major_edit_form";  // Đảm bảo đường dẫn và tên form là đúng
        }
        major.setUpdatedDate(new Date());
        daoMajor.update(major);  // Thay daoSubject thành daoMajor
        return "redirect:/major/major_view";  // Cập nhật lại URL nếu cần
    }

    @GetMapping("/major/deletemajor/{id}")
    public String delete(@PathVariable String id) {
        daoMajor.delete(id);  // Thay daoSubject thành daoMajor
        return "redirect:/major/major_view";  // Cập nhật lại URL nếu cần
    }
}
