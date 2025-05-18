package controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import beans.Classes;
import beans.Role;
import beans.Score;
import beans.ScoreComponent;
import beans.Semester;
import beans.Subject;
import beans.SubjectDetail;
import beans.TypeScore;
import beans.User;
import response.SubjectDetailResponse;
import utils.IdUtil;


@Controller
public class SubjectDetailController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDetailController.class);
	
	@PostMapping("/subjectDetail/list")
	public String listSubjectDetails(
	        @RequestParam(value = "classId", required = false) String classId,
	        @RequestParam(value = "subjectId", required = false) String subjectId,
	        @RequestParam(value = "teacherId", required = false) String teacherId,
	        @RequestParam(value = "semester", required = false) String semester,
	        @RequestParam(value = "status", required = false) String status,
	        Model model) {

	    LOGGER.debug("---- Call API: subjectDetail/list");
	    LOGGER.debug("- classId: {}", classId);
	    LOGGER.debug("- subjectId: {}", subjectId);
	    LOGGER.debug("- teacherId: {}", teacherId);
	    LOGGER.debug("- semester: {}", semester);
	    LOGGER.debug("- status: {}", status);

	    List<SubjectDetail> subjectDetails = daoSubjectDetail.getAll();

	    // Apply filters
	    if (classId != null && !classId.isEmpty()) {
	        subjectDetails = subjectDetails.stream()
	                .filter(sd -> sd.getClassId() != null && sd.getClassId().equals(classId))
	                .collect(Collectors.toList());
	    }

	    if (subjectId != null && !subjectId.isEmpty()) {
	        subjectDetails = subjectDetails.stream()
	                .filter(sd -> sd.getSubjectId() != null && sd.getSubjectId().equals(subjectId))
	                .collect(Collectors.toList());
	    }

	    if (teacherId != null && !teacherId.isEmpty()) {
	        subjectDetails = subjectDetails.stream()
	                .filter(sd -> sd.getTeacherId() != null && sd.getTeacherId().equals(teacherId))
	                .collect(Collectors.toList());
	    }

	    if (semester != null && !semester.isEmpty()) {
	        subjectDetails = subjectDetails.stream()
	                .filter(sd -> semester.equals(sd.getSemester().name()))
	                .collect(Collectors.toList());
	    }

	    if (status != null && !status.isEmpty()) {
	        boolean active = Boolean.parseBoolean(status);
	        subjectDetails = subjectDetails.stream()
	                .filter(sd -> sd.isStatus() == active)
	                .collect(Collectors.toList());
	    }

	    // Convert to SubjectDetailResponse
	    List<SubjectDetailResponse> responseList = new ArrayList<>();
	    for (SubjectDetail s : subjectDetails) {
	        SubjectDetailResponse sdr = new SubjectDetailResponse();
	        sdr.setId(s.getId());
	        sdr.setSemester(s.getSemester().name());
	        sdr.setCredit(s.getCredit());

	        Classes classes = daoClasses.getClassesById(s.getClassId());
	        sdr.setClassName(classes == null ? null : classes.getClassName());

	        Subject subj = daoSubject.getSubjectById(s.getSubjectId());
	        sdr.setSubjectName(subj == null ? null : subj.getName());

	        User teacher = daoUser.getUserById(s.getTeacherId());
	        sdr.setTeacherName(teacher == null || !"TEACHER".equals(teacher.getRole()) ? null : teacher.getUsername());

	        sdr.setStatus(s.isStatus());
	        sdr.setCreatedDate(s.getCreatedDate());
	        sdr.setUpdatedDate(s.getUpdatedDate());

	        responseList.add(sdr);
	    }

	    // Add to model
	    model.addAttribute("list", responseList);
	    model.addAttribute("classes", daoClasses.getAll());
	    model.addAttribute("subjects", daoSubject.getAll());
	    model.addAttribute("teachers", daoUser.getUsersByRole(Role.TEACHER.getCode()));
	    model.addAttribute("semesters", Semester.values());

	    return "subjectDetails/subjectDetail_view";
	}

	
	
	@GetMapping("/subjectDetail/subjectDetail_view")
	public String getAll(Model model) {
		LOGGER.debug("----------------- API /subjectDetail/subjectDetail_view");
		List<SubjectDetailResponse> list1 = new ArrayList<SubjectDetailResponse>();
		List<SubjectDetail> list = new ArrayList<>();
		list = daoSubjectDetail.getAll();
		if(list == null) {
			LOGGER.debug("----------------subjectDetail null!");
			return "/";
		}
		for(SubjectDetail s : list) {
			SubjectDetailResponse sdr = new SubjectDetailResponse();
			sdr.setId(s.getId());
			sdr.setSemester(s.getSemester().name());
			sdr.setCredit(s.getCredit());
			Classes classes = daoClasses.getClassesById(s.getClassId());
			if(classes == null) {
				sdr.setClassName(null);
			}else {
				sdr.setClassName(classes.getClassName());
			}
			
			
			Subject subject = daoSubject.getSubjectById(s.getSubjectId());
			sdr.setSubjectName(subject == null ? null : subject.getName());
			
			User teacher = daoUser.getUserById(s.getTeacherId());
			sdr.setTeacherName(teacher == null || !teacher.getRole().equals("TEACHER") ? null : teacher.getUsername());
			sdr.setStatus(s.isStatus());
			sdr.setCreatedDate(s.getCreatedDate());
			sdr.setUpdatedDate(s.getUpdatedDate());
			list1.add(sdr);
		}
		
		
		List<User> teachers = daoUser.getUsersByRole(Role.TEACHER.getCode());
		List<Subject> subjects = daoSubject.getAll();
		Semester[] semesters = Semester.values();
		List<Classes> classes = daoClasses.getAll();
		model.addAttribute("teachers", teachers);
		model.addAttribute("subjects", subjects);
		model.addAttribute("classes", classes);
		model.addAttribute("semesters", semesters);
		model.addAttribute("list", list1);
		return "subjectDetails/subjectDetail_view";
	}
	
	@GetMapping("subjectDetail/subjectDetail_create_form")
	public String createSubjectDetail(Model model) {
		List<Subject> subjects = daoSubject.getAll();
		List<Classes> classes = daoClasses.getAll();
		model.addAttribute("subjects", subjects);
		model.addAttribute("classes", classes);
		Semester[] semesters = Semester.values();
		model.addAttribute("semesters", semesters);
		List<User> teachers = daoUser.getUsersByRole(Role.TEACHER.name());
		model.addAttribute("teachers", teachers);
		SubjectDetail subjectDetail = new SubjectDetail();
		model.addAttribute("subjectDetail", subjectDetail);
		return "subjectDetails/subjectDetail_create_form";
	}
	
	@PostMapping("subjectDetail/save")
	public String saveSubjectDetail(@ModelAttribute("subjectDetail") SubjectDetail subjectDetail, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "subjectDetails/subjectDetail_create_form";
		}
        
		SubjectDetail subjectDetailExist = daoSubjectDetail.getSubjectDetailByClassIdAndSubjectId(subjectDetail.getClassId(), subjectDetail.getSubjectId());
		if(subjectDetailExist != null) {
			model.addAttribute("nameExist", "Lớp đã hoặc đang học môn này");
			List<Subject> subjects = daoSubject.getAll();
			List<Classes> classes = daoClasses.getAll();
			model.addAttribute("subjects", subjects);
			model.addAttribute("classes", classes);
			Semester[] semesters = Semester.values();
			model.addAttribute("semesters", semesters);
			List<User> teachers = daoUser.getUsersByRole(Role.TEACHER.name());
			model.addAttribute("teachers", teachers);
			model.addAttribute("subjectDetail", subjectDetail);
			return "subjectDetails/subjectDetail_create_form";
			
		}
		subjectDetail.setId(IdUtil.generateId());
		subjectDetail.setCreatedDate(new Date());
		subjectDetail.setUpdatedDate(new Date());
		daoSubjectDetail.save(subjectDetail);
		List<User> students = daoUser.getUsersByClassId(subjectDetail.getClassId());
		for (User user : students) {
			Score score = new Score(user.getId(), subjectDetail.getId(), true);
			daoScore.save(score);
			ScoreComponent score1 = new ScoreComponent(score.getId(), TypeScore.attendance.getCode(), true);
			score1.setId(IdUtil.generateId());
			ScoreComponent score2 = new ScoreComponent(score.getId(), TypeScore.test.getCode(), true);
			score2.setId(IdUtil.generateId());
			ScoreComponent score3 = new ScoreComponent(score.getId(), TypeScore.final_exam.getCode(), true);
			score3.setId(IdUtil.generateId());
			LOGGER.debug("typeScore: {}", score1.getTypeScore());
			daoScoreComponent.save(score1);
			daoScoreComponent.save(score2);
			daoScoreComponent.save(score3);
		}
		return "redirect:/subjectDetail/subjectDetail_view";
	}
	
	@GetMapping("/subjectDetail/editSubjectDetail/{id}")
    public String edit(@PathVariable String id, Model model) {
		LOGGER.debug("code đã vào API: /subjectDetail/editSubjectDetail");
		LOGGER.debug("ID: {}", id);
		List<Subject> subjects = daoSubject.getAll();
		List<Classes> classes = daoClasses.getAll();
		model.addAttribute("subjects", subjects);
		model.addAttribute("classes", classes);
		Semester[] semesters = Semester.values();
		model.addAttribute("semesters", semesters);
		List<User> teachers = daoUser.getUsersByRole(Role.TEACHER.name());
		model.addAttribute("teachers", teachers);
		
        SubjectDetail sub = daoSubjectDetail.getById(id);
        model.addAttribute("subjectDetail", sub);
        return "subjectDetails/subjectDetail_edit_form";
    }

    @PostMapping("/subjectDetail/editsave")
    public String editSave(@ModelAttribute("subjectDetail") SubjectDetail subject, BindingResult result) {
        if (result.hasErrors()) {
            return "subjectDetails/subjectDetail_edit_form";
        }

        subject.setUpdatedDate(new Date());
        daoSubjectDetail.update(subject);
        return "redirect:/subjectDetail/subjectDetail_view";
    }
	

	    

//	    @GetMapping("/subjectDetail/deletesubjectDetail/{id}")
//	    public String delete(@PathVariable String id) {
//	    	LOGGER.debug("Calling api /subjectDetail/deletesubjectDetail");
//	    	LOGGER.debug("subjectDetailId: {}", id);
//	    	List<Score> scores = daoScore.getScoreBySubjectDetailId(id);
//	    	LOGGER.debug("SO luong score: {}", scores.size());
//
//	    	for(Score score : scores) {
//	    		List<ScoreComponent> scoreComponents = daoScoreComponent.getScoreComponentByScoreId(score.getId());
//	    		for(ScoreComponent scoreComponent : scoreComponents) {
//	    			daoScoreComponent.delete(scoreComponent.getId());
//	    		}
//	    	}
//	    	LOGGER.debug("Delete ScoreComponent successfully!");
//	    	
//	    	for(Score score : scores) {
//	    		daoScore.delete(score.getId());
//	    	}
//	    	LOGGER.debug("Delete Score successfully!");
//
//	        daoSubjectDetail.delete(id);
//	        return "redirect:/subjectDetail/subjectDetail_view";
//	    }
    
    
    @GetMapping("/subjectDetail/deletesubjectDetail/{id}")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        LOGGER.debug("Calling api /subjectDetail/deletesubjectDetail");
        LOGGER.debug("subjectDetailId: {}", id);

        try {
            List<Score> scores = daoScore.getScoreBySubjectDetailId(id);
            LOGGER.debug("SO luong score: {}", scores.size());

            for (Score score : scores) {
                List<ScoreComponent> scoreComponents = daoScoreComponent.getScoreComponentByScoreId(score.getId());
                for (ScoreComponent scoreComponent : scoreComponents) {
                    daoScoreComponent.delete(scoreComponent.getId());
                }
            }
            LOGGER.debug("Delete ScoreComponent successfully!");

            for (Score score : scores) {
                daoScore.delete(score.getId());
            }
            LOGGER.debug("Delete Score successfully!");

            daoSubjectDetail.delete(id);
            LOGGER.debug("Delete SubjectDetail successfully!");

            redirectAttributes.addFlashAttribute("message", "Xóa SubjectDetail thành công!");
        } catch (DataIntegrityViolationException ex) {
            LOGGER.error("Lỗi ràng buộc toàn vẹn dữ liệu khi xóa subjectDetail id={}: {}", id, ex.getMessage());
            redirectAttributes.addFlashAttribute("error", "Không thể xóa SubjectDetail do ràng buộc dữ liệu!");
        } catch (Exception ex) {
            LOGGER.error("Lỗi không xác định khi xóa subjectDetail id={}: {}", id, ex.getMessage(), ex);
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi xóa SubjectDetail.");
        }

        return "redirect:/subjectDetail/subjectDetail_view";
    }

//	    
	    
	    // View với role Teacher
	    @GetMapping("/subjectDetail/subjectDetailByTeacher")
		public String getSubjectDetailByTeacherId(HttpServletRequest request,
				Model model) {
			LOGGER.debug("----------------- API /subjectDetail/subjectDetail_view");
			List<SubjectDetailResponse> list1 = new ArrayList<SubjectDetailResponse>();
			String teacherId =  (String) request.getSession().getAttribute("userId");
			LOGGER.debug("teacherId: {}", teacherId);
			List<SubjectDetail> list =  daoSubjectDetail.getSubjectDetailByTeacherId(teacherId);
			if(list == null) {
				LOGGER.debug("----------------subjectDetail null!");
				return "/";
			}
			for(SubjectDetail s : list) {
				SubjectDetailResponse sdr = new SubjectDetailResponse();
				sdr.setId(s.getId());
				sdr.setSemester(s.getSemester().name());
				sdr.setCredit(s.getCredit());
				Classes classes = daoClasses.getClassesById(s.getClassId());
				if(classes == null) {
					sdr.setClassName(null);
				}else {
					sdr.setClassName(classes.getClassName());
				}
				
				Subject subject = daoSubject.getSubjectById(s.getSubjectId());
				sdr.setSubjectName(subject == null ? null : subject.getName());
				
				User teacher = daoUser.getUserById(s.getTeacherId());
				sdr.setTeacherName(teacher == null || !teacher.getRole().equals("TEACHER") ? null : teacher.getUsername());
				sdr.setStatus(s.isStatus());
				sdr.setCreatedDate(s.getCreatedDate());
				sdr.setUpdatedDate(s.getUpdatedDate());
				list1.add(sdr);
			}
			
			model.addAttribute("list", list1);
			return "teacher/subjectDetails/subjectDetail_view";
		}
		
}
