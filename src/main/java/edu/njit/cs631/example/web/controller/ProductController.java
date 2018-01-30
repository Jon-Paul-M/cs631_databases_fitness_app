package edu.njit.cs631.example.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.cs631.example.business.entities.Product;
import edu.njit.cs631.example.business.services.ProductService;

@Controller
@RequestMapping(value="/example/product")
public class ProductController {

	// In the original example the below functionality was split between 
	// ProductListController & ProductCommentsController 
	// (b/c thymeleaf's built in MVC is not as flexible as spring's)
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProductService productService;
	
	public ProductController() {
		super();
	}

	@RequestMapping(value="/list")
	public ModelAndView list() {
		logger.info("In ProductController.list");
		final List<Product> allProducts = productService.findAll();
		ModelAndView modelAndView = new ModelAndView("example/product/list");
		modelAndView.addObject("prods", allProducts);
		return modelAndView;
	}
	
	@RequestMapping(value="/comments")
	public ModelAndView comments(@RequestParam(name="prodId", required=true) Integer productId) {
		logger.info("In ProductController.list");
		final Product product = productService.findById(productId);
		ModelAndView modelAndView = new ModelAndView("example/product/comments");
		modelAndView.addObject("prod", product);
		return modelAndView;
	}
}
