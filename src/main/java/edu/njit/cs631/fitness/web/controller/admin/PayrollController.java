package edu.njit.cs631.fitness.web.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.cs631.fitness.data.projection.HourlyInstructorPayroll;
import edu.njit.cs631.fitness.service.api.PayrollService;
import edu.njit.cs631.fitness.web.controller.BaseController;

@Controller
@RequestMapping(value="/admin/payroll")
public class PayrollController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	

	@Autowired
	private PayrollService payrollService;
	
	
	public PayrollController() {
		super();
	}

    @Override
    protected String getCommonViewName() {
        return "admin/payroll/list";
    }

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		logger.info("In PayrollController.list");
		ModelAndView modelAndView = commonModelAndView();
		
		List<HourlyInstructorPayroll> payrolls = payrollService.generateHourlyPayroll();
		logger.info(payrolls.toString());
		
		addClazzes(modelAndView);
		return modelAndView;
	}	
	
	
}

