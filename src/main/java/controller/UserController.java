package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import beans.Classes;
import beans.Role;
import beans.User;
import dao.DaoUser;
import requests.ChangePasswordReq;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class UserController  extends BaseController{

    @Autowired
    private DaoUser daoUser;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);



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
        List<Classes> classes = daoClasses.getAll();
        model.addAttribute("classes", classes);
        Role[] roles = Role.values();
        model.addAttribute("roles", roles);
        
        return "users/user_create_form";
    }


    // Lưu user sau khi tạo
    @PostMapping("/user/save")
    public String saveUser(@ModelAttribute("user") User user,
                           BindingResult result,
                           @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
                           @RequestParam(value = "avatarInputType", required = false) String avatarInputType,
                           Model model,
                           HttpServletRequest request) {
        LOGGER.debug("--------------user: {}", user.toString());

        if (result.hasErrors()) {
            LOGGER.error("Form submission has errors. Details:");
            result.getAllErrors().forEach(error -> {
                LOGGER.error("Object: {}, Field: {}, Message: {}",
                        error.getObjectName(),
                        (error instanceof FieldError) ? ((FieldError) error).getField() : "N/A",
                        error.getDefaultMessage());
            });
            return "users/user_create_form";
        }

        user.setId(IdUtil.generateId());
        String maSV = user.getCode();
        User user1 = daoUser.getUserByCode(maSV);

        int countError = 0;
        if (user1 != null) {
            model.addAttribute("codeExist", true);
            user.setCode("");
            countError++;
        }

        User user2 = daoUser.findByUsername(user.getUsername());
        if (user2 != null) {
            model.addAttribute("userNameExist", true);
            user.setUsername("");
            countError++;
        }

        User user3 = daoUser.findByEmail(user.getEmail());
        if (user3 != null) {
            model.addAttribute("emailExist", true);
            user.setEmail("");
            countError++;
        }

        if (countError > 0) {
            model.addAttribute("user", user);
            List<Classes> classes = daoClasses.getAll();
            model.addAttribute("classes", classes);
            Role[] roles = Role.values();
            model.addAttribute("roles", roles);
            return "users/user_create_form";
        }

     // ✅ Lưu file ảnh avatar nếu có upload
        // Trường hợp nhập với file
      if(!avatarInputType.isEmpty() && "file".equals(avatarInputType)) {
    	  if (avatarFile != null && !avatarFile.isEmpty()) {
              try {
                  String originalFilename = avatarFile.getOriginalFilename();
                  String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

                  // Kiểm tra định dạng file
                  List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png");
                  if (!allowedExtensions.contains(fileExtension)) {
                      model.addAttribute("avatarError", "Chỉ cho phép file .jpg, .jpeg, .png");
                      model.addAttribute("user", user);
                      List<Classes> classes = daoClasses.getAll();
                      model.addAttribute("classes", classes);
                      model.addAttribute("roles", Role.values());
                      return "users/user_create_form";
                  }

                  // Lưu file nếu hợp lệ
                  String uploadDir = request.getServletContext().getRealPath("/uploads");
                  File uploadFolder = new File(uploadDir);
                  if (!uploadFolder.exists()) uploadFolder.mkdirs();

                  String fileName = UUID.randomUUID() + "_" + originalFilename;
                  File destFile = new File(uploadFolder, fileName);
                  avatarFile.transferTo(destFile);

                  user.setAvatar("uploads/" + fileName);
              } catch (IOException e) {
                  LOGGER.error("Lỗi khi upload file ảnh đại diện", e);
              }
          }
      }else {
    	  LOGGER.debug("Avatar url: {}", user.getAvatar());
      }
      
      LOGGER.debug("ClassId: {}", user.getClassId());
      LOGGER.debug("ROle: {}", user.getRole());
      if(user.getClassId().isEmpty()) {
    	  user.setClassId(null);
      }

        // Mã hóa mật khẩu
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // Gán mặc định các giá trị
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
//        user.setStatus(true);
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
    	LOGGER.debug("++ API: user/saveBasic");
    	if(result.hasErrors()) {
    		LOGGER.error("Form submission has errors. Details:");
            result.getAllErrors().forEach(error -> {
            	LOGGER.error("Object: {}, Field: {}, Message: {}",
                    error.getObjectName(),
                    (error instanceof FieldError) ? ((FieldError) error).getField() : "N/A",
                    error.getDefaultMessage());
            });
            return "users/user_create_basic_form";
    	}
    	LOGGER.debug("-- username: {}", user.getUsername());
    	LOGGER.debug("-- password: {}", user.getPassword());
    	LOGGER.debug("-- email: {}", user.getEmail());
    	   // Mã hóa mật khẩu
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        LOGGER.debug("--- password hash: {}", hashedPassword); 
        // Gán mặc định các giá trị
        user.setId(IdUtil.generateId());
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        user.setStatus(true);

        daoUser.saveBasic(user);
        return "redirect:/user/user_view";
    }



    // Hiển thị form edit user
    @GetMapping("/user/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        User user = daoUser.getUserById(id);
        model.addAttribute("user", user);
        
        // Thêm danh sách lớp học
        List<Classes> classes = daoClasses.getAll();
        model.addAttribute("classes", classes);
        
        // Thêm danh sách role
        Role[] roles = Role.values();
        model.addAttribute("roles", roles);

        return "users/user_edit_form";
    }


    // Cập nhật user sau khi chỉnh sửa
    @PostMapping("/user/update")
    public String updateUser(HttpServletRequest request,@ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "users/user_edit_form";
        }
        String userId = (String) request.getSession().getAttribute("userId");
        User userLogin = daoUser.getUserById(userId);
        if(userLogin == null) {
        	return "redirect:/login";
        }
        
        // Lấy user gốc để kiểm tra mật khẩu có thay đổi không
        User existingUser = daoUser.getUserById(user.getId());

        // Nếu mật khẩu được chỉnh sửa thì mã hóa lại
        if (!user.getPassword().equals(existingUser.getPassword())) {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
        }
        if(user.getClassId().isEmpty()) {
      	  user.setClassId(null);
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
    public String listUsersByClass(@PathVariable String classId, Model model) {
        List<User> users = daoUser.getUsersByClassId(classId);
        model.addAttribute("list", users);
        model.addAttribute("filter", "Class ID: " + classId);
        return "users/user_list";
    }
    
    @PostMapping("/user/import")
    public String importUsersFromExcel(@RequestParam("file") MultipartFile file, Model model,
                                       RedirectAttributes redirectAttributes) {
        LOGGER.debug("Đang gọi API /user/import");

        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            LOGGER.debug("Tổng số dòng dữ liệu (tính từ 0): {}", sheet.getLastRowNum());

            for (int i = 2; i <= sheet.getLastRowNum(); i++) { // Bỏ 2 dòng tiêu đề đầu
                Row row = sheet.getRow(i);
                if (row == null || row.getCell(1) == null) {
                    LOGGER.warn("Dòng {} bị bỏ qua vì rỗng hoặc thiếu MSSV", i + 1);
                    continue;
                }

                User user = new User();
                user.setId(IdUtil.generateId());
                String code = row.getCell(1).getStringCellValue();
                LOGGER.debug("-- Id: {}", code);

                if(code == null) {
                	continue;
                }
                user.setCode(code); // MSSV
                User userExist = daoUser.getUserByCode(code);
                if(userExist != null) {
                	LOGGER.debug("- Username: {} đã tồn tại", userExist.getUsername());
                	continue;
                }
                user.setFullName(row.getCell(2).getStringCellValue());
                LOGGER.debug("-- fullName : {}", row.getCell(2).getStringCellValue());

                // Xử lý ngày sinh: text hoặc dạng ngày
                Cell dobCell = row.getCell(3);
                Date dateOfBirth = null;
                if (dobCell != null) {
                    if (dobCell.getCellType() == CellType.NUMERIC) {
                        dateOfBirth = dobCell.getDateCellValue();
                    } else if (dobCell.getCellType() == CellType.STRING) {
                        String dobStr = dobCell.getStringCellValue();
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                            sdf.setLenient(false);
                            dateOfBirth = sdf.parse(dobStr);
                        } catch (ParseException e) {
                            LOGGER.error("Lỗi định dạng ngày tại dòng {}: {}", i + 1, dobStr);
                        }
                    }
                } 
                LOGGER.debug("-- dateOfBirth : {}", dateOfBirth.toGMTString());
                user.setDateOfBirth(dateOfBirth);

                user.setGender(getStringCellValue(row.getCell(4)));
                String className = getStringCellValue(row.getCell(5));
                Classes classes = daoClasses.getClassesByClassName(className);
                String classId = null;
                if(classes != null) {
                	classId = classes.getId();
                }
                user.setClassId(classId);
                user.setIdentificationNumber(getStringCellValue(row.getCell(6)));
                LOGGER.debug("-- UserName: {}", getStringCellValue(row.getCell(7)));

                user.setUsername(getStringCellValue(row.getCell(7)));
                user.setPassword(passwordEncoder.encode(getStringCellValue(row.getCell(8))));
                user.setEmail(getStringCellValue(row.getCell(9)));
                user.setRole(getStringCellValue(row.getCell(10)));
                user.setPhone(getStringCellValue(row.getCell(11)));

                user.setStatus(true);
                user.setCreatedDate(new Date());
                user.setUpdatedDate(new Date());

                daoUser.save(user);

                LOGGER.info("Đã lưu user dòng {}: MSSV={}, Username={}, Email={}", i + 1,
                            user.getId(), user.getUsername(), user.getEmail());
            }

            workbook.close();
            redirectAttributes.addFlashAttribute("messageImport", "Import thành công!");

        } catch (Exception e) {
            LOGGER.error("Lỗi khi import file: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("messageImport", "Đã xảy ra lỗi khi import file!");
        }

        return "redirect:/user/user_view";
    }

    // Hàm tiện ích để lấy chuỗi an toàn từ Cell
    private String getStringCellValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue();
        if (cell.getCellType() == CellType.NUMERIC) return String.valueOf((long) cell.getNumericCellValue());
        return cell.toString();
    }

    // Danh sách tất cả user
    @GetMapping("/user/user_view")
    public String listUsers(Model model) {
        List<Classes> classes = daoClasses.getAll();
        model.addAttribute("classes", classes);
        Role[] roles = Role.values();
        model.addAttribute("roles", roles);
        
        List<User> users = daoUser.getAllUsers();
        for(User user : users) {
        	Classes classes1 = daoClasses.getClassesById(user.getClassId());
        	user.setClassId("");
        	if(classes1 != null) {
        		user.setClassId(classes1.getClassName());
        	}
        	LOGGER.debug("code: {}", user.getCode());
        	
        }
        model.addAttribute("list", users);
        return "users/user_view";
    }

    
    
    
    
    @PostMapping("/user/list")
    public String listUsers(@RequestParam(value = "classId", required = false) String classId,
                            @RequestParam(value = "role", required = false) String role,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            Model model) {
    	LOGGER.debug("---- Call API: user/list");
    	LOGGER.debug("- classId: {}", classId);
    	LOGGER.debug("- role: {}", role);
    	LOGGER.debug("- keyword: {}", keyword);
        List<User> users = daoUser.getAllUsers(); // hoặc list đã có

        LOGGER.debug("--count1: {}", users.size());
        if (classId != null && !classId.isEmpty()) {
            users = users.stream()
                         .filter(u -> u.getClassId() != null && u.getClassId().equals(classId))
                         .collect(Collectors.toList());
        }
        LOGGER.debug("--count2: {}", users.size());


        if (role != null && !role.isEmpty()) {
            users = users.stream()
                         .filter(u -> role.equals(u.getRole()))
                         .collect(Collectors.toList());
        }
        LOGGER.debug("--count3: {}", users.size());
        // Lọc theo từ khóa tìm kiếm (vd: họ tên, username hoặc email)
        if (keyword != null && !keyword.isEmpty()) {
            String lowerKeyword = keyword.toLowerCase();
            users = users.stream()
            	    .filter(u -> {
            	        String fullName = u.getFullName();
            	        String username = u.getUsername();
            	        String email = u.getEmail();
            	        return (fullName != null && fullName.toLowerCase().contains(lowerKeyword)) ||
            	               (username != null && username.toLowerCase().contains(lowerKeyword)) ||
            	               (email != null && email.toLowerCase().contains(lowerKeyword));
            	    })
            	    .collect(Collectors.toList());

        }

        for(User user : users) {
        	Classes classes1 = daoClasses.getClassesById(user.getClassId());
        	user.setClassId("");
        	if(classes1 != null) {
        		user.setClassId(classes1.getClassName());
        	}
        	
        }

        model.addAttribute("list", users);
        model.addAttribute("classes", daoClasses.getAll()); // list lớp
        Role[] roles = Role.values();
        model.addAttribute("roles", roles);        
        return "users/user_view";
    }
    
    @GetMapping("/user/list")
    public String listUsers2(@RequestParam(value = "classId", required = false) String classId,
                            @RequestParam(value = "role", required = false) String role,
                            
                            Model model) {
    	LOGGER.debug("---- Call API: user/list");
    	LOGGER.debug("- classId: {}", classId);
    	LOGGER.debug("- role: {}", role);
    	
        List<User> users = daoUser.getAllUsers(); // hoặc list đã có

        LOGGER.debug("--count1: {}", users.size());
        if (classId != null && !classId.isEmpty()) {
            users = users.stream()
                         .filter(u -> u.getClassId() != null && u.getClassId().equals(classId))
                         .collect(Collectors.toList());
        }
        LOGGER.debug("--count2: {}", users.size());


        if (role != null && !role.isEmpty()) {
            users = users.stream()
                         .filter(u -> role.equals(u.getRole()))
                         .collect(Collectors.toList());
        }
        LOGGER.debug("--count3: {}", users.size());
   


        model.addAttribute("list", users);
        model.addAttribute("classes", daoClasses.getAll()); // list lớp
        Role[] roles = Role.values();
        model.addAttribute("roles", roles);        
        return "users/user_view";
    }

    
    // API sửa thông tin user
    @GetMapping("user/editInfo")
    public String editInfo(HttpServletRequest request,
    		Model model) {
    	LOGGER.debug("--- Call API user/editInfo");
    	String userId = (String) request.getSession().getAttribute("userId");
    	if(userId == null) {
    		return "redirect:/login";
    	}
    	User user = daoUser.getUserById(userId);
    	if(user == null) {
    		return "redirect:/login";
    	}
    	model.addAttribute("user", user);
    	return "users/user_edit_info_form";
    }
    
    @PostMapping("user/saveInfo")
    public String saveInfo(@ModelAttribute("user") User user, BindingResult result,
    		Model model) {
        if (result.hasErrors()) {
            return "users/user_edit_info_form";
        }

        // Lấy user gốc để kiểm tra mật khẩu có thay đổi không
        User existingUser = daoUser.getUserById(user.getId());

        Integer countError = 0;
        if(user.getEmail().isEmpty() ||  user.getEmail() == null) {
        	model.addAttribute("emptyEmail", "Chưa nhập email");
        	countError ++;
        }
        if(user.getPhone().isEmpty() || user.getPhone() == null) {
        	model.addAttribute("emptyPhone", "Chưa nhập sdt");
        	countError++;
        }
        
        
        if(countError > 0) {
        	model.addAttribute("user", user);
        	return "users/user_edit_info_form";
        }
        
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setUpdatedDate(new Date());

        daoUser.update(existingUser);
        return "redirect:/";
    }
    
    // CHức năng đổi mật khẩu
    @GetMapping("/user/changePassword")
    public String changePassword(HttpServletRequest request ,Model model) {
    	
    	
    	model.addAttribute("changePasswordReq", new ChangePasswordReq());
    	
    	return "users/user_changePassword";
    }
    
    @PostMapping("/user/changePassword")
    public String changePasswordPost(
            @ModelAttribute("changePasswordReq") ChangePasswordReq changePasswordReq,
            HttpServletRequest request,
            Model model,
            RedirectAttributes redirectAttributes) {

        LOGGER.debug("Calling API: /user/changePassword");

        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null) {
            LOGGER.warn("User not logged in, redirecting to /login");
            return "redirect:/login";
        }

        String currentPassword = changePasswordReq.getCurrentPassword();
        String newPassword = changePasswordReq.getNewPassword();
        String reNewPassword = changePasswordReq.getReNewPassword();

        LOGGER.debug("Received changePasswordReq - currentPassword: [{}], newPassword: [{}], reNewPassword: [{}]",
                     currentPassword, newPassword, reNewPassword);

        if (currentPassword == null || currentPassword.isEmpty() ||
            newPassword == null || newPassword.isEmpty() ||
            reNewPassword == null || reNewPassword.isEmpty()) {

            LOGGER.warn("Missing required password fields");
            redirectAttributes.addFlashAttribute("errorRePassword", "Vui lòng nhập đầy đủ các trường!");
            return "redirect:/user/changePassword";
        }

        User user = daoUser.getUserById(userId);
        if (user == null) {
            LOGGER.error("User with ID [{}] not found in DB", userId);
            return "redirect:/login";
        }

        LOGGER.debug("Validating current password...");
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            LOGGER.warn("Current password does not match for user ID: [{}]", userId);
            redirectAttributes.addFlashAttribute("notMatchPassword", "Mật khẩu hiện tại không chính xác");
            return "redirect:/user/changePassword";
        }

        if (!newPassword.equals(reNewPassword)) {
            LOGGER.warn("New password and confirm password do not match for user ID: [{}]", userId);
            redirectAttributes.addFlashAttribute("errorRePassword", "Mật khẩu nhập lại không trùng khớp");
            return "redirect:/user/changePassword";
        }

        LOGGER.debug("Updating password for user ID: [{}]", userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        daoUser.update(user);

        LOGGER.info("Password changed successfully for user ID: [{}]", userId);
        return "redirect:/login";
    }

}
