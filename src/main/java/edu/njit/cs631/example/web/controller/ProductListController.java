package edu.njit.cs631.example.web.controller;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.cs631.example.business.entities.Product;
import edu.njit.cs631.example.business.services.ProductService;

@Controller
@RequestMapping(value="/example/product")
public class ProductListController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProductService productService;
	
	public ProductListController() {
		super();
	}

	@RequestMapping(value="/list")
	public ModelAndView list() {
		logger.info("In ProductListController.list");
		
		final List<Product> allProducts = productService.findAll();
		
		ModelAndView modelAndView = new ModelAndView("example/product/list");
		modelAndView.addObject("prods", allProducts);
		return modelAndView;
	}
}
