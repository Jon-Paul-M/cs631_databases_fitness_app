package edu.njit.cs631.fitness.web.controller.admin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.njit.cs631.fitness.data.projection.InstructorPayroll;
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

    private BigDecimal sumInstr(Function<InstructorPayroll, BigDecimal> f, List<InstructorPayroll> bigDecimals) {
	   return bigDecimals.stream()
                .filter(Objects::nonNull)
                .map(f)
                .filter(Objects::nonNull)
                .reduce(BigDecimal::add).get();
    }

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue="10.0") BigDecimal federalRate,
							 @RequestParam(defaultValue="3.0") BigDecimal otherRate) {
		logger.info("In PayrollController.list");
		ModelAndView modelAndView = commonModelAndView();
		
		BigDecimal stateRate = new BigDecimal(5.0);
		LocalDateTime ending = LocalDateTime.now().minusDays(1);
		LocalDateTime beginning = ending.minusDays(14);

		
		List<InstructorPayroll> payrolls = payrollService.generateHourlyPayroll(beginning, ending, federalRate, stateRate, otherRate);
		logger.info(payrolls.toString());
		BigDecimal total =
                sumInstr(InstructorPayroll::getGross, payrolls)
                .add(sumInstr(InstructorPayroll::getFederalTax, payrolls))
                .add(sumInstr(InstructorPayroll::getStateTax, payrolls))
                .add(sumInstr(InstructorPayroll::getOtherTax, payrolls));


		modelAndView.addObject("total", total);
        modelAndView.addObject("payrolls", payrolls);
		return modelAndView;
	}	
	
	
}

