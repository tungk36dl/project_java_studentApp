package controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class HomeController {
	

	@GetMapping("/")
	public String redirectToProductView(Model model) {
		model.addAttribute("pageActive", "user");
		return "home/home";
	}
	
}
