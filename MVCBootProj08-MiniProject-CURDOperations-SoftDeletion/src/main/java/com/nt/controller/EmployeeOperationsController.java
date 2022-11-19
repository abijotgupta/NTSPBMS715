package com.nt.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nt.model.Employee;
import com.nt.service.IEmployeeMgmtService;

@Controller
public class EmployeeOperationsController {
	@Autowired
	private   IEmployeeMgmtService  empService;
	
	@GetMapping("/")  //To show the home page
	public   String  showHome() {
		System.out.println("EmployeeOperationsController.showHome()");
		 //return  LVN
		return  "home";
	}
	
	
	@GetMapping("/emp_report")
	public   String    showEmployeeReport(Map<String,Object> map) {
		System.out.println("EmployeeOperationsController.showEmployeeReport()");
		//use service
		Iterable<Employee>  itEmps=empService.getAllEmployees();
		// put  result in  model attribute
		map.put("empsList",itEmps);
		//retunr  LVN
		return "show_employee_report";
	}
	
	
	@GetMapping("/emp_add")  // for form launching for add employee operation
	public  String    showFormForsaveEmployee(@ModelAttribute("emp") Employee emp) {
		System.out.println("EmployeeOperationsController.showFormForsaveEmployee()");
		//return  LVN
		return "register_employee";
	}
	
	/*@PostMapping("/emp_add")   //form submission related to  add employee operation
	public   String  saveEmployee(@ModelAttribute("emp") Employee emp,
			                                                                                           Map<String,Object> map) {
		//use  Service
		String msg=empService.registerEmployee(emp);
		Iterable<Employee> itEmps=empService.getAllEmployees();
		//keep the result in ModelAttribute
		map.put("resultMsg", msg);
		map.put("empsList", itEmps);
		//  return  LVN
		return  "show_employee_report";
	}*/
	
	
	/*@PostMapping("/emp_add")   //form submission related to  add employee operation
	public   String  saveEmployee(@ModelAttribute("emp") Employee emp,
			                                                                                           Map<String,Object> map) {
		System.out.println("EmployeeOperationsController.saveEmployee()");
		//use  Service
		String msg=empService.registerEmployee(emp);
		//keep the result in ModelAttribute
		map.put("resultMsg", msg);
		//  return  LVN
		return  "redirect:emp_report";
	}*/
	
	
	/*	@PostMapping("/emp_add")   //form submission related to  add employee operation
		public   String  saveEmployee(@ModelAttribute("emp") Employee emp,
				                                                                                          RedirectAttributes  attrs) {
			System.out.println("EmployeeOperationsController.saveEmployee()");
			//use  Service
			String msg=empService.registerEmployee(emp);
			//keep the result as   flashAttribute
			attrs.addFlashAttribute("resultMsg",msg);
			
			//  return  LVN
			return  "redirect:emp_report";
		}*/
	
	
	@PostMapping("/emp_add")   //form submission related to  add employee operation
	public   String  saveEmployee(@ModelAttribute("emp") Employee emp,
			                                                                                          HttpSession ses) {
		System.out.println("EmployeeOperationsController.saveEmployee()");
		//use  Service
		String msg=empService.registerEmployee(emp);
		//keep the result as   flashAttribute
		ses.setAttribute("resultMsg",msg);
		
		//  return  LVN
		return  "redirect:emp_report";
	}
	
	@GetMapping("/emp_edit")  //handler method lanuch  edit Employee form page
	public  String   showEditEmployeeFormPage(@RequestParam("no") int no,
			                                                                                 @ModelAttribute("emp") Employee emp) {
		//use service 
		Employee emp1=empService.getEmployeeByNo(no);
		//copy data
		BeanUtils.copyProperties(emp1, emp);
		// return LVN
		return  "update_employee";

	}
	
	
	@PostMapping("/emp_edit")   //handler method to submit  edit Employee form page
	public  String   editEmployee(RedirectAttributes attrs,
			                                                    @ModelAttribute("emp") Employee emp) {
		//user  service
		 String msg=empService.updateEmployee(emp);
		 // add the result message as Flash Attribute
		 attrs.addFlashAttribute("resultMsg",msg);
		 //redirect the request
		 return  "redirect:emp_report";
		
	}
	
	
	@GetMapping("/emp_delete")
	public  String     deleteEmployee(RedirectAttributes attrs,
			                                                         @RequestParam int no) {
		//use service
		 String msg=empService.deleteEmployeeById(no);
		 //keep the result in FlashAttribute
		 attrs.addFlashAttribute("resultMsg", msg);
		 //redirect the request
		 return  "redirect:emp_report";
		
	}
	

}
