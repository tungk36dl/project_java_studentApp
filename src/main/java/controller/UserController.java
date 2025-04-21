package controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import beans.Role;
import beans.User;
import dao.DaoUser;
import requests.AccountReq;
import utils.IdUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class UserController {

    @Autowired
    private DaoUser daoUser;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);



    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    
    // Hiển thị form tạo user
    @GetMapping("/user/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "users/user_create_form";
    }


    // Lưu user sau khi tạo
    @PostMapping("/user/save")
    public String saveUser(@ModelAttribute("user") User user, BindingResult result) {
        logger.debug("--------------user: {}", user.toString());

        if (result.hasErrors()) {
            logger.error("Form submission has errors. Details:");
            result.getAllErrors().forEach(error -> {
                logger.error("Object: {}, Field: {}, Message: {}",
                    error.getObjectName(),
                    (error instanceof FieldError) ? ((FieldError) error).getField() : "N/A",
                    error.getDefaultMessage());
            });
            return "users/user_create_form";
        }

        // Mã hóa mật khẩu
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // Gán mặc định các giá trị
        user.setId(IdUtil.generateId());
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        user.setStatus(true);

        daoUser.save(user);
        return "redirect:/user/user_view";
    }
    
    // Hiển thị form tạo user
    @GetMapping("/user/createBasic")
    public String showCreateBasicForm(Model model) {
        model.addAttribute("user", new User());
        
        // Truyền danh sách role từ enum
        Map<String, String> roles = Arrays.stream(Role.values())
            .collect(Collectors.toMap(Role::getCode, Role::getDescription));

        model.addAttribute("roles", roles);
        return "users/user_create_basic_form";
    }
    
    @PostMapping("/user/saveBasic")
    public String saveBasic(@ModelAttribute("user") User user, BindingResult result) {
    	logger.debug("++ API: user/saveBasic");
    	if(result.hasErrors()) {
    		logger.error("Form submission has errors. Details:");
            result.getAllErrors().forEach(error -> {
                logger.error("Object: {}, Field: {}, Message: {}",
                    error.getObjectName(),
                    (error instanceof FieldError) ? ((FieldError) error).getField() : "N/A",
                    error.getDefaultMessage());
            });
            return "users/user_create_basic_form";
    	}
    	logger.debug("-- username: {}", user.getUsername());
    	logger.debug("-- password: {}", user.getPassword());
    	logger.debug("-- email: {}", user.getEmail());
    	   // Mã hóa mật khẩu
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        logger.debug("--- password hash: {}", hashedPassword); 
        // Gán mặc định các giá trị
        user.setId(IdUtil.generateId());
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        user.setStatus(true);

        daoUser.saveBasic(user);
        return "redirect:/user/user_view";
    }


    // Danh sách tất cả user
    @GetMapping("/user/user_view")
    public String listUsers(Model model) {
        List<User> users = daoUser.getAllUsers();
        model.addAttribute("list", users);
        return "users/user_view";
    }

    // Hiển thị form edit user
    @GetMapping("/user/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        User user = daoUser.getUserById(id);
        model.addAttribute("user", user);
        return "users/user_edit_form";
    }

    // Cập nhật user sau khi chỉnh sửa
    @PostMapping("/user/update")
    public String updateUser(@ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "users/user_edit_form";
        }

        // Lấy user gốc để kiểm tra mật khẩu có thay đổi không
        User existingUser = daoUser.getUserById(user.getId());

        // Nếu mật khẩu được chỉnh sửa thì mã hóa lại
        if (!user.getPassword().equals(existingUser.getPassword())) {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
        }

        user.setUpdatedDate(new Date());

        daoUser.update(user);
        return "redirect:/user/list";
    }

    // Xóa user
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        daoUser.delete(id);
        return "redirect:/user/list";
    }
    
 // Lọc danh sách user theo roleId
//    @GetMapping("/user/role/{roleId}")
//    public String listUsersByRole(@PathVariable String roleId, Model model) {
//        List<User> users = daoUser.getUsersByRoleId(roleId);
//        model.addAttribute("list", users);
//        model.addAttribute("filter", "Role: " + roleId);
//        return "users/user_list";
//    }

    // Lọc danh sách user theo classId
    @GetMapping("/user/class/{classId}")
    public String listUsersByClass(@PathVariable Integer classId, Model model) {
        List<User> users = daoUser.getUsersByClassId(classId);
        model.addAttribute("list", users);
        model.addAttribute("filter", "Class ID: " + classId);
        return "users/user_list";
    }
    
    @PostMapping("/user/import")
    public String importUsersFromExcel(@RequestParam("file") MultipartFile file, Model model) {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ qua dòng tiêu đề
                Row row = sheet.getRow(i);
                if (row == null) continue;

                User user = new User();
                user.setUsername(row.getCell(0).getStringCellValue());
                user.setPassword(passwordEncoder.encode(row.getCell(1).getStringCellValue()));
                user.setFullName(row.getCell(2).getStringCellValue());
                user.setEmail(row.getCell(3).getStringCellValue());
                user.setDateOfBirth(row.getCell(4).getDateCellValue());
                user.setGender(row.getCell(5).getStringCellValue());
                user.setAvatar(row.getCell(6).getStringCellValue());
                user.setIdentificationNumber(row.getCell(7).getStringCellValue());
                user.setPhone(row.getCell(8).getStringCellValue());
                user.setAddress(row.getCell(9).getStringCellValue());
                user.setClassId(row.getCell(10).getStringCellValue());
                user.setRole(row.getCell(11).getStringCellValue());
                user.setStatus(true);
                user.setCreatedDate(new Date());
                user.setUpdatedDate(new Date());

                daoUser.save(user);
            }

            workbook.close();
            model.addAttribute("message", "Import thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Đã xảy ra lỗi khi import file!");
        }

        return "redirect:/user/user_view";
    }

}
