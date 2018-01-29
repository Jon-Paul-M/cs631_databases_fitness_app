package edu.njit.cs631.example.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.cs631.example.business.entities.Order;
import edu.njit.cs631.example.business.services.OrderService;

@Controller
@RequestMapping(value = "/example/order")
public class OrderController {

	// In the original example the below functionality was split between
	// OrderListController & OrderDetailsController
	// (b/c thymeleaf's built-in MVC is not as flexible as spring's)

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrderService orderService;

	public OrderController() {
		super();
	}

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		logger.info("In OrderController.list");
		final List<Order> allOrders = orderService.findAll();
		ModelAndView modelAndView = new ModelAndView("example/order/list");
		modelAndView.addObject("orders", allOrders);
		return modelAndView;
	}

	@RequestMapping(value = "/details")
	public ModelAndView details(@RequestParam(name = "orderId", required = true) Integer orderId) {
		logger.info("In OrderController.list");
		final Order order = orderService.findById(orderId);
		ModelAndView modelAndView = new ModelAndView("example/order/details");
		modelAndView.addObject("order", order);
		return modelAndView;
	}
}
