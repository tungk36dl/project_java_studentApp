package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import beans.Product;
import dao.DaoProduct;


@Controller
public class HomeController {
	
	@Autowired
	DaoProduct daoProduct;
	
	@GetMapping("/")
	public String redirectToProductView(Model model) {
		model.addAttribute("pageActive", "user");
		return "test";
	}
	
	@GetMapping("product_create_form")
	public String showForm(Model model) {
		model.addAttribute("product", new Product());
		return "product_create_form";
	}
	
	@PostMapping(value="/save")
	public String save(@ModelAttribute("product") Product prd, BindingResult result) {
		System.out.println("Du lieu san pham: " + prd);
		daoProduct.save(prd);
		return "redirect:/product_view";
	}
	
	@RequestMapping("/product_view")
    public String viewProducts(Model model) {
        List<Product> list = daoProduct.getProducts();
        model.addAttribute("list", list);
        return "product_view";
    }

    @RequestMapping(value = "/editproduct/{idSanPham}")
    public String edit(@PathVariable int idSanPham, Model model) {
        Product product = daoProduct.getProductById(idSanPham);
        model.addAttribute("product", product);
        return "product_edit_form";
    }

    @RequestMapping(value = "/editsave", method = RequestMethod.POST)
    public String editSave(@ModelAttribute("product") Product product, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Có lỗi trong dữ liệu: " + result.getAllErrors());
            return "product_edit_form";
        }
        daoProduct.update(product);
        return "redirect:/product_view";
    }

    @RequestMapping(value = "/deleteproduct/{idSanPham}", method = RequestMethod.GET)
    public String delete(@PathVariable int idSanPham) {
        daoProduct.delete(idSanPham);
        return "redirect:/product_view";
    }
}
