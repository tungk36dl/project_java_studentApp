package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import beans.Classes;
import beans.Score;
import beans.ScoreComponent;
import beans.Subject;
import beans.SubjectDetail;
import beans.TypeScore;
import beans.User;
import requests.InputScoreRequest;
import requests.InputScoreWrapper;
import response.InforScoreStudentResponse;
import response.InputScoreResponse;
import response.ScoreResponse;
import utils.IdUtil;

@Controller
public class ScoreController extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScoreController.class);

	// Lay danh sach diem theo role Sinh vien
	@GetMapping("/score/scoreByStudent")
	public String getScoreByStudent(HttpServletRequest request, Model model) {
		String studentId = (String) request.getSession().getAttribute("userId");
		LOGGER.debug("studentId: {}", studentId);
		if(studentId == null) {
			return "redirect: /login";
		}
		// Lấy thông tin sinh viên
		User student = daoUser.getUserById(studentId);
		if(student == null) {
			return "redirect: /login";
		}
		InforScoreStudentResponse inforScoreStudentResponse = new InforScoreStudentResponse();
		inforScoreStudentResponse.setFullName(student.getFullName());
		inforScoreStudentResponse.setDateOfBirth(student.getDateOfBirth() != null ? student.getDateOfBirth().toGMTString() : "");
		inforScoreStudentResponse.setAddress(student.getAddress());
		inforScoreStudentResponse.setStudentId(studentId);
		Classes classes = daoClasses.getClassesById(student.getClassId());
	
		inforScoreStudentResponse.setClassName(classes.getClassName());
		List<Score> scores = daoScore.getScoreByStudentId(studentId);
		List<ScoreResponse> scoreResponses = new ArrayList<>();
		Integer totalCredits = 0;
		Float totalScoreGPA = 0f;
		for (Score score : scores) {
			ScoreComponent scoreAttendance = daoScoreComponent.getScoreComponentByScoreIdAndTypeScore(score.getId(),  TypeScore.attendance.getCode());
			ScoreComponent scoreTest = daoScoreComponent.getScoreComponentByScoreIdAndTypeScore(score.getId(),  TypeScore.test.getCode());
			ScoreComponent scoreFinalExam = daoScoreComponent.getScoreComponentByScoreIdAndTypeScore(score.getId(),  TypeScore.final_exam.getCode());
			ScoreResponse scoreResponse = new ScoreResponse();
			SubjectDetail subjectDetail = daoSubjectDetail.getById(score.getSubjectDetailId());
			Subject subject = daoSubject.getSubjectById(subjectDetail.getSubjectId());
			scoreResponse.setSubjectCode(subjectDetail.getSubjectId());
			scoreResponse.setSubjectName(subject.getName());
			scoreResponse.setCredits(subjectDetail.getCredit());
			scoreResponse.setSemester(subjectDetail.getSemester().toString());
			scoreResponse.setScore10(score.getScore10());
			scoreResponse.setScoreLetter(score.getLetterGrade());
			scoreResponse.setGPA4(score.getGpa4());
			scoreResponse.setScoreAttendance(scoreAttendance.getScore());
			scoreResponse.setScoreTest(scoreTest.getScore());
			scoreResponse.setScoreFinalExam(scoreFinalExam.getScore());
			
			scoreResponses.add(scoreResponse);
			totalCredits += subjectDetail.getCredit();
			Float gpa4 = score.getGpa4() != null ? score.getGpa4() : 0f;
			totalScoreGPA += gpa4;
		}
		
		// Sắp xếp danh sách theo phần số của semester (ví dụ: semester1 -> 1)
		scoreResponses.sort(Comparator.comparingInt(sr -> {
			String semester = sr.getSemester().toLowerCase().replaceAll("[^0-9]", "");
			return Integer.parseInt(semester);
		}));

		
		inforScoreStudentResponse.setTotalCredits(totalCredits);
		inforScoreStudentResponse.setAvgGPA(totalScoreGPA / scores.size());
		
		model.addAttribute("inforScoreStudentResponse", inforScoreStudentResponse);
		model.addAttribute("scoreResponses", scoreResponses);
		model.addAttribute("studentId", studentId);
		LOGGER.debug("So luong diem: {}", scoreResponses.size());
		
		
		return "student/score/score_view";
	}
	
	// export điểm ra file excel
	@GetMapping("/score/export")
	public void exportScoreByStudent(@RequestParam(value = "studentId", required = false) String studentId, HttpServletRequest request, HttpServletResponse response) throws IOException {

		LOGGER.debug("Export request for studentId: {}", studentId);

	    if (studentId == null) {
	        LOGGER.debug("studentId is null, redirecting to login");
	        response.sendRedirect("/login");
	        return;
	    }

	    User student = daoUser.getUserById(studentId);
	    LOGGER.debug("Loaded student: {}", student);

	    if (student == null) {
	        LOGGER.debug("Student not found in DB, redirecting to login");
	        response.sendRedirect("/login");
	        return;
	    }

	    Classes classes = daoClasses.getClassesById(student.getClassId());
	    LOGGER.debug("Loaded class info: {}", classes);

	    List<Score> scores = daoScore.getScoreByStudentId(studentId);
	    LOGGER.debug("Number of scores loaded: {}", scores.size());

	    Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Student Scores");

	    // Styles
	    Font boldFont = workbook.createFont();
	    boldFont.setBold(true);

	    CellStyle headerStyle = workbook.createCellStyle();
	    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    headerStyle.setBorderTop(BorderStyle.THIN);
	    headerStyle.setBorderBottom(BorderStyle.THIN);
	    headerStyle.setBorderLeft(BorderStyle.THIN);
	    headerStyle.setBorderRight(BorderStyle.THIN);
	    headerStyle.setFont(boldFont);
	    headerStyle.setAlignment(HorizontalAlignment.CENTER);

	    CellStyle dataStyle = workbook.createCellStyle();
	    dataStyle.setBorderTop(BorderStyle.THIN);
	    dataStyle.setBorderBottom(BorderStyle.THIN);
	    dataStyle.setBorderLeft(BorderStyle.THIN);
	    dataStyle.setBorderRight(BorderStyle.THIN);
	    dataStyle.setAlignment(HorizontalAlignment.CENTER);

	    CellStyle infoBoldStyle = workbook.createCellStyle();
	    infoBoldStyle.setFont(boldFont);


		 // Làm tròn số tới 1 chữ số thập phân
		 DataFormat format = workbook.createDataFormat();
		 dataStyle.setDataFormat(format.getFormat("0.0"));
		 
		 
	    // Thong tin sinh vien
	    sheet.createRow(0).createCell(0).setCellValue("Họ tên:");
	    sheet.getRow(0).getCell(0).setCellStyle(infoBoldStyle);
	    sheet.getRow(0).createCell(1).setCellValue(student.getFullName());
	    sheet.getRow(0).createCell(3).setCellValue("Mã SV:");
	    sheet.getRow(0).getCell(3).setCellStyle(infoBoldStyle);
	    sheet.getRow(0).createCell(4).setCellValue(student.getCode());

	    sheet.createRow(1).createCell(0).setCellValue("Lớp:");
	    sheet.getRow(1).getCell(0).setCellStyle(infoBoldStyle);
	    sheet.getRow(1).createCell(1).setCellValue(classes.getClassName());
	    sheet.getRow(1).createCell(3).setCellValue("Địa chỉ:");
	    sheet.getRow(1).getCell(3).setCellStyle(infoBoldStyle);
	    sheet.getRow(1).createCell(4).setCellValue(student.getAddress());

	    sheet.createRow(2).createCell(0).setCellValue("Ngày sinh:");
	    sheet.getRow(2).getCell(0).setCellStyle(infoBoldStyle);
	    sheet.getRow(2).createCell(1).setCellValue(student.getDateOfBirth().toString());

	    int totalCredits = 0;
	    float totalGPA = 0f;
	    for (Score score : scores) {
	        SubjectDetail detail = daoSubjectDetail.getById(score.getSubjectDetailId());
	        if (detail != null) {
	            totalCredits += detail.getCredit();
	            totalGPA += (score.getGpa4() != null ? score.getGpa4() : 0f);
	        } else {
	            LOGGER.debug("SubjectDetail not found for scoreId: {}", score.getId());
	        }
	    }

	    sheet.createRow(3).createCell(0).setCellValue("Tổng số tín chỉ đã học:");
	    sheet.getRow(3).getCell(0).setCellStyle(infoBoldStyle);
	    sheet.getRow(3).createCell(1).setCellValue(totalCredits);
	    sheet.getRow(3).createCell(3).setCellValue("GPA trung bình:");
	    sheet.getRow(3).getCell(3).setCellStyle(infoBoldStyle);
	    sheet.getRow(3).createCell(4).setCellValue(scores.size() > 0 ? totalGPA / scores.size() : 0f);

	    Row headerRow = sheet.createRow(5);
	    String[] headers = {"Mã môn", "Tên môn", "Số tín chỉ", "Học kỳ", "Điểm chuyên cần", "Điểm kiểm tra", "Điểm cuối kỳ", "Điểm hệ 10", "Điểm chữ", "GPA 4"};
	    for (int i = 0; i < headers.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headers[i]);
	        cell.setCellStyle(headerStyle);
	    }

	    int rowNum = 6;
	    for (Score score : scores) {
	        SubjectDetail detail = daoSubjectDetail.getById(score.getSubjectDetailId());
	        Subject subject = daoSubject.getSubjectById(detail.getSubjectId());

	        ScoreComponent scoreAttendance = daoScoreComponent.getScoreComponentByScoreIdAndTypeScore(score.getId(), TypeScore.attendance.getCode());
	        ScoreComponent scoreTest = daoScoreComponent.getScoreComponentByScoreIdAndTypeScore(score.getId(), TypeScore.test.getCode());
	        ScoreComponent scoreFinal = daoScoreComponent.getScoreComponentByScoreIdAndTypeScore(score.getId(), TypeScore.final_exam.getCode());

	        LOGGER.debug("Writing score row for subject: {}, GPA4: {}", subject.getName(), score.getGpa4());

	        Row row = sheet.createRow(rowNum++);
	        int col = 0;
	        row.createCell(col).setCellValue(detail.getSubjectId());
	        row.getCell(col++).setCellStyle(dataStyle);

	        row.createCell(col).setCellValue(subject.getName());
	        row.getCell(col++).setCellStyle(dataStyle);

	        row.createCell(col).setCellValue(detail.getCredit());
	        row.getCell(col++).setCellStyle(dataStyle);

	        row.createCell(col).setCellValue(detail.getSemester().toString());
	        row.getCell(col++).setCellStyle(dataStyle);
	        Double attendanceValue = (scoreAttendance != null && scoreAttendance.getScore() != null)
	        		? scoreAttendance.getScore() : 0.0;
	        		row.createCell(col).setCellValue(attendanceValue);
	        		row.getCell(col++).setCellStyle(dataStyle);

	        		Double testValue = (scoreTest != null && scoreTest.getScore() != null)
	        		? scoreTest.getScore() : 0.0;
	        		row.createCell(col).setCellValue(testValue);
	        		row.getCell(col++).setCellStyle(dataStyle);

	        		Double finalValue = (scoreFinal != null && scoreFinal.getScore() != null)
	        		? scoreFinal.getScore() : 0.0;
	        		row.createCell(col).setCellValue(finalValue);
	        		row.getCell(col++).setCellStyle(dataStyle);

	        		if (score != null && score.getScore10() != null) {
	        			row.createCell(col).setCellValue(Math.round(score.getScore10() * 10.0) / 10.0);
	        			} else {
	        			row.createCell(col).setCellValue("");
	        			}
	        			row.getCell(col++).setCellStyle(dataStyle);

	        			row.createCell(col).setCellValue(score != null && score.getLetterGrade() != null ? score.getLetterGrade() : "");
	        			row.getCell(col++).setCellStyle(dataStyle);

	        			if (score != null && score.getGpa4() != null) {
	        			row.createCell(col).setCellValue(score.getGpa4());
	        			} else {
	        			row.createCell(col).setCellValue("");
	        			}
	        			row.getCell(col++).setCellStyle(dataStyle);
	    }

	    for (int i = 0; i < headers.length; i++) {
	        sheet.autoSizeColumn(i);
	    }

	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=scores.xlsx");

	    LOGGER.debug("Exporting Excel file for studentId: {}", studentId);
	    workbook.write(response.getOutputStream());
	    workbook.close();
	}
	
	// Hiển thị điểm sinh viên từ view của PDT
	@GetMapping("/score/scoreByStudentFromPDT/{studentId}")
	public String getScoreByStudentFormPDT(@PathVariable String studentId, HttpServletRequest request, Model model) {
		LOGGER.debug("studentId: {}", studentId);
		if(studentId == null) {
			return "redirect: /login";
		}
		// Lấy thông tin sinh viên
		User student = daoUser.getUserById(studentId);
		if(student == null) {
			return "redirect: /login";
		}
		InforScoreStudentResponse inforScoreStudentResponse = new InforScoreStudentResponse();
		inforScoreStudentResponse.setFullName(student.getFullName());
		inforScoreStudentResponse.setDateOfBirth(student.getDateOfBirth() != null ? student.getDateOfBirth().toGMTString() : "");
		inforScoreStudentResponse.setAddress(student.getAddress());
		inforScoreStudentResponse.setStudentId(studentId);
		Classes classes = daoClasses.getClassesById(student.getClassId());
	
		inforScoreStudentResponse.setClassName(classes.getClassName());
		List<Score> scores = daoScore.getScoreByStudentId(studentId);
		List<ScoreResponse> scoreResponses = new ArrayList<>();
		Integer totalCredits = 0;
		Float totalScoreGPA = 0f;
		
		// Sắp xếp danh sách theo phần số của semester (ví dụ: semester1 -> 1)
	    scores.sort(Comparator.comparingInt(sr -> {
	    	SubjectDetail detail = daoSubjectDetail.getById(sr.getSubjectDetailId());
			String semester = detail.getSemester().toString().toLowerCase().replaceAll("[^0-9]", "");
			return Integer.parseInt(semester);
		}));
		for (Score score : scores) {
			ScoreComponent scoreAttendance = daoScoreComponent.getScoreComponentByScoreIdAndTypeScore(score.getId(),  TypeScore.attendance.getCode());
			ScoreComponent scoreTest = daoScoreComponent.getScoreComponentByScoreIdAndTypeScore(score.getId(),  TypeScore.test.getCode());
			ScoreComponent scoreFinalExam = daoScoreComponent.getScoreComponentByScoreIdAndTypeScore(score.getId(),  TypeScore.final_exam.getCode());
			ScoreResponse scoreResponse = new ScoreResponse();
			SubjectDetail subjectDetail = daoSubjectDetail.getById(score.getSubjectDetailId());
			Subject subject = daoSubject.getSubjectById(subjectDetail.getSubjectId());
			scoreResponse.setSubjectCode(subjectDetail.getSubjectId());
			scoreResponse.setSubjectName(subject.getName());
			scoreResponse.setCredits(subjectDetail.getCredit());
			scoreResponse.setSemester(subjectDetail.getSemester().toString());
			scoreResponse.setScore10(score.getScore10());
			scoreResponse.setScoreLetter(score.getLetterGrade());
			scoreResponse.setGPA4(score.getGpa4());
			scoreResponse.setScoreAttendance(scoreAttendance.getScore());
			scoreResponse.setScoreTest(scoreTest.getScore());
			scoreResponse.setScoreFinalExam(scoreFinalExam.getScore());
			
			scoreResponses.add(scoreResponse);
			totalCredits += subjectDetail.getCredit();
			Float gpa4 = score.getGpa4() != null ? score.getGpa4() : 0f;
			totalScoreGPA += gpa4;
		}
		
		// Sắp xếp danh sách theo phần số của semester (ví dụ: semester1 -> 1)
		scoreResponses.sort(Comparator.comparingInt(sr -> {
			String semester = sr.getSemester().toLowerCase().replaceAll("[^0-9]", "");
			return Integer.parseInt(semester);
		}));
		
		inforScoreStudentResponse.setTotalCredits(totalCredits);
		inforScoreStudentResponse.setAvgGPA(totalScoreGPA / scores.size());
		
		model.addAttribute("inforScoreStudentResponse", inforScoreStudentResponse);
		model.addAttribute("scoreResponses", scoreResponses);
		model.addAttribute("studentId", studentId);
		LOGGER.debug("So luong diem: {}", scoreResponses.size());
		return "student/score/score_view";
	}

	
	// Lay danh sach diem theo tung student de nhap diem
	@GetMapping("/score/inputScore/{subjectDetailId}")
	public String inputScore(@PathVariable String subjectDetailId, Model model) {
		LOGGER.debug("Calling api /score/inputScore");
		LOGGER.debug("subjectDetail: {}", subjectDetailId);
		SubjectDetail subjectDetail = daoSubjectDetail.getById(subjectDetailId);
		List<InputScoreResponse> inputScoreResponses =  new ArrayList<>();
		
		if(subjectDetail == null) {
			return "error/500.jsp";
		}
		LOGGER.debug("Credit: {}", subjectDetail.getCredit());

		Classes classes = daoClasses.getClassesById(subjectDetail.getClassId());
		if(classes == null) {
			return "error/500.jsp";
		}
		List<User> students = daoUser.getUsersByClassId(classes.getId());
		LOGGER.debug("SO luong student: {}", students.size());
		
		String updatedBy = null;
		String updatedDate = null;
		
		for (User student : students) {
		    InputScoreResponse inputScoreResponse = new InputScoreResponse();
		    inputScoreResponse.setStudentName(student.getFullName());
		    inputScoreResponse.setStudentId(student.getCode());
		    LOGGER.debug("studentName: {}", student.getFullName());

		    try {
		        Score score = daoScore.getScoreByStudentIdAndSubjectDetailId(student.getId(), subjectDetailId);
		        if(updatedBy == null && updatedDate == null) {
		        	updatedBy = score.getUpdatedBy();
		        	updatedDate = score.getUpdatedDate().toGMTString();
		        }
		        inputScoreResponse.setScoreId(score.getId());
		        
		        ScoreComponent attendance = daoScoreComponent.getScoreComponentByScoreIdAndTypeScore(score.getId(), TypeScore.attendance.getCode());
		        if (attendance != null && attendance.getScore() != null) {
		            inputScoreResponse.setScoreAttendance(attendance.getScore());
		        } else {
		            inputScoreResponse.setScoreAttendance(-1); // hoặc không set gì nếu muốn
		        }

		        // Điểm kiểm tra
		        ScoreComponent test = daoScoreComponent.getScoreComponentByScoreIdAndTypeScore(score.getId(), TypeScore.test.getCode());
		        if (test != null && test.getScore() != null) {
		            inputScoreResponse.setScoreTest(test.getScore());
		        } else {
		            inputScoreResponse.setScoreTest(-1);
		        }

		        // Điểm thi cuối kỳ
		        ScoreComponent finalExam = daoScoreComponent.getScoreComponentByScoreIdAndTypeScore(score.getId(), TypeScore.final_exam.getCode());
		        if (finalExam != null && finalExam.getScore() != null) {
		            inputScoreResponse.setScoreFinalExam(finalExam.getScore());
		        } else {
		            inputScoreResponse.setScoreFinalExam(-1);
		        }

		        inputScoreResponses.add(inputScoreResponse);
		    } catch (org.springframework.dao.EmptyResultDataAccessException e) {
		        LOGGER.warn("Không tìm thấy score cho studentId={} và subjectDetailId={}", student.getId(), subjectDetailId);
		        // Nếu bạn muốn bỏ qua student này, thì không thêm vào danh sách
		        // Hoặc có thể tạo một response với các điểm là null
		    } catch (Exception e) {
		        LOGGER.error("Lỗi không xác định khi xử lý studentId={}", student.getId(), e);
		    }
		}

		LOGGER.debug("DOne mapping student");
		model.addAttribute("inputScoreResponses", inputScoreResponses);
		model.addAttribute("subjectDetail", subjectDetail);
		model.addAttribute("className", classes.getClassName());
		model.addAttribute("updatedBy", updatedBy);
		model.addAttribute("updatedDate", updatedDate);
		
		Subject subject = daoSubject.getSubjectById(subjectDetail.getSubjectId());
		model.addAttribute("subjectName", subject != null ? subject.getName() : "");
		User teacher = daoUser.getUserById(subjectDetail.getTeacherId());
		model.addAttribute("teacherName", teacher.getFullName());
		// Thông tin môn học 
		return "teacher/scores/score_update_view";
	}
	
	
	@PostMapping("/score/submitScores")
	public String submitScores(HttpServletRequest request,
			@ModelAttribute("scores") InputScoreWrapper wrapper, RedirectAttributes redirectAttributes) {
		String userId = (String) request.getSession().getAttribute("userId");
		if(userId == null) {
			return "redirect:/login";
		}
		User user = daoUser.getUserById(userId);
		if(user == null) {
			return "error/500";
		}
	    List<InputScoreRequest> scores = wrapper.getScores();
	    LOGGER.debug("Đang lưu danh sách điểm, tổng số: {}", scores.size());

	    for (InputScoreRequest scoreReq : scores) {

	        String scoreId = scoreReq.getScoreId();

	        // Xử lý điểm null
	        Float attendance = (scoreReq.getScoreAttendance() == null) ? 0f : scoreReq.getScoreAttendance().floatValue();
	        Float test = (scoreReq.getScoreTest() == null) ? 0f : scoreReq.getScoreTest().floatValue();
	        Float finalExam = (scoreReq.getScoreFinalExam() == null) ? 0f : scoreReq.getScoreFinalExam().floatValue();

	        // Lưu từng loại điểm
	        saveOrUpdateScoreComponent(scoreId, TypeScore.attendance.getCode(), attendance);
	        saveOrUpdateScoreComponent(scoreId, TypeScore.test.getCode(), test);
	        saveOrUpdateScoreComponent(scoreId, TypeScore.final_exam.getCode(), finalExam);

	        Score score = daoScore.getScoreById(scoreId);
	        calculateAndUpdateScoreInfo(score, attendance, test, finalExam);

	        score.setUpdatedBy(user.getUsername());
	            daoScore.update(score); // đảm bảo có method update
	    }

	    redirectAttributes.addFlashAttribute("message", "Lưu điểm thành công!");
	    LOGGER.debug("subjectDetailId: {}", wrapper.getSubjectDetailId());
	    return "redirect:/score/inputScore/" + wrapper.getSubjectDetailId();
	}

	private void saveOrUpdateScoreComponent(String scoreId, String typeScore, Float value) {
	    ScoreComponent scoreComponent = daoScoreComponent.getScoreComponentByScoreIdAndTypeScore(scoreId, typeScore);
	    if (scoreComponent == null) {
	        scoreComponent = new ScoreComponent();
	        scoreComponent.setId(IdUtil.generateId());
	        scoreComponent.setScoreId(scoreId);
	        scoreComponent.setTypeScore(typeScore);
	        scoreComponent.setStatus(true);
	        scoreComponent.setCreatedDate(new Date());
	    }
	    scoreComponent.setScore(value);
	    scoreComponent.setUpdatedDate(new Date());

	    daoScoreComponent.update(scoreComponent); // Viết thêm hàm này nếu cần
	}
	
	private void calculateAndUpdateScoreInfo(Score score, Float attendance, Float test, Float finalExam) {
	    if (attendance == null) attendance = 0f;
	    if (test == null) test = 0f;
	    if (finalExam == null) finalExam = 0f;

	    float score10 = attendance * 0.1f + test * 0.2f + finalExam * 0.7f;
	    score.setScore10(score10);

	    // Điểm chữ
	    String letterGrade;
	    float gpa4;
	    if (score10 >= 8.5f) {
	        letterGrade = "A";
	        gpa4 = 4.0f;
	    } else if (score10 >= 7.0f) {
	        letterGrade = "B";
	        gpa4 = 3.0f;
	    } else if (score10 >= 5.5f) {
	        letterGrade = "C";
	        gpa4 = 2.0f;
	    } else if (score10 >= 4.0f) {
	        letterGrade = "D";
	        gpa4 = 1.0f;
	    } else {
	        letterGrade = "F";
	        gpa4 = 0.0f;
	    }

	    score.setLetterGrade(letterGrade);
	    score.setGpa4(gpa4);
	    score.setUpdatedDate(new Date());
	}


}
