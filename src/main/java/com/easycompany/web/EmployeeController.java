package com.easycompany.web;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.easycompany.cmm.vo.SearchCriteria;
import com.easycompany.service.DepartmentService;
import com.easycompany.service.Employee;
import com.easycompany.service.EmployeeService;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private DefaultBeanValidator beanValidator;

	private Logger log = LoggerFactory.getLogger(EmployeeController.class);

	@RequestMapping(value = "/employeeList.do", method = RequestMethod.POST)
	public String searchEmpList(@RequestParam(required = false, value = "pageNo") String pageNo,
								SearchCriteria searchCriteria, ModelMap model,
								@RequestParam Map<String, Object> commandMap) throws Exception {

		int currentPageNo = 1;
		try {
			currentPageNo = Integer.parseInt(pageNo);
		} catch (Exception e) {}

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(currentPageNo);
		paginationInfo.setRecordCountPerPage(3);
		paginationInfo.setPageSize(8);

		commandMap.put("firstIndex", paginationInfo.getFirstRecordIndex());
		commandMap.put("lastIndex", paginationInfo.getLastRecordIndex());
		commandMap.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());

		commandMap.put("searchEid", searchCriteria.getSearchEid());
		commandMap.put("searchDid", searchCriteria.getSearchDid());
		commandMap.put("searchName", searchCriteria.getSearchName());

		List<Employee> employees = employeeService.getAllEmployees(commandMap);
		for (Employee employee : employees) {
			log.debug("+ id = " + employee.getEmployeeid() + ", name = " + employee.getName() + ", emp = " + employee);
		}
		model.addAttribute("employeelist", employees);
		model.addAttribute("searchCriteria", searchCriteria);

		int employeeCount = employeeService.getEmployeeCount(commandMap);
		paginationInfo.setTotalRecordCount(employeeCount);
		model.addAttribute("paginationInfo", paginationInfo);

		return "employeelist";
	}

	// v [Step 4-2-01] employeeList.do 경로로 요청과 메소드(getEmpList)를 매핑해준다.
	// v [Step 4-2-02] 화면에서 넘어오는 pageNo 파라미터 값을 반드시 넘어오지 않아도 됨을 명시적표현해 본다.
	@RequestMapping(value = "/employeeList.do", method = RequestMethod.GET)
	public String getEmpList(@RequestParam(required = false, value = "pageNo") String pageNo,
							 SearchCriteria searchCriteria,
							 ModelMap model,
			@RequestParam Map<String, Object> commandMap) throws Exception {

		int currentPageNo;
		try {
			currentPageNo = Integer.parseInt(pageNo);
		} catch (Exception e) {
			currentPageNo = 1;
		}

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(currentPageNo);
		paginationInfo.setRecordCountPerPage(3);
		paginationInfo.setPageSize(8);

		commandMap.put("firstIndex", paginationInfo.getFirstRecordIndex());
		commandMap.put("lastIndex", paginationInfo.getLastRecordIndex());
		commandMap.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());

		List<Employee> employlist = employeeService.getAllEmployees(commandMap);
		model.addAttribute("employeelist", employlist);
		model.addAttribute("searchCriteria", searchCriteria);

		int employeeCount = employeeService.getEmployeeCount(commandMap);
		paginationInfo.setTotalRecordCount(employeeCount);
		model.addAttribute("paginationInfo", paginationInfo);

		return "employeelist";
	}

	// v [Step 4-3-01] 요청되는 insertEmployee.do 와 메소드를 매핑(RequestMapping)한다. 단 GET 방식(RequestMethod 를 통해 세팅) 에 대해서만 처리하다록 한다.
	@RequestMapping(value = "/insertEmployee.do", method = RequestMethod.GET)
	public String setupForm(Model model) {
		// v [Step 4-3-02] 먼저 아래 두라인을 지우고 employee 객체를 new 하지 말고
		// 메소드 형태로 정의하여 ModelAttributes 를 이용하여 세팅하여보자.
//		Employee employee = new Employee();
//		model.addAttribute("employee", employee);
		return "addemployee";
	}

	// v [Step 4-4-01] 요청되는 insertEmployee.do 와 메소드를 매핑(RequestMapping)한다. 단 POST 방식(RequestMethod 를 통해 세팅)에 대해서만 처리하다록 한다.
	@RequestMapping(value = "/insertEmployee.do", method = RequestMethod.POST)
	public String insertEmployee(@ModelAttribute Employee employee, BindingResult bindingResult, Model model) {

		if (employee == null) {
			log.warn("Employee is null...");
			return "addemployee";
		}

		// v [Step 4-4-02] DefaultBeanValidator의 validate 메소드를 이용하여 employee 객체 값을 체크한다. 파라미터중 하나는 bindingResult 이다.
		beanValidator.validate(employee, bindingResult);

		// v [Step 4-4-03] DefaultBeanValidator 를 실행한 결과 에러가 있을경우(hasErrors()), 원래 페이지(addemployee.jsp)를 다시 보여주고 에러 메세지를 뿌려준다.
		if (bindingResult.hasErrors()) {
			model.addAttribute("hasErrors", bindingResult.hasErrors());
			log.error("input parameter has some errors!");
			return "addemployee";
		}

		if ( employeeService.insertEmployee(employee) < 0 ) {
			model.addAttribute("hasErrors", "데이터 추가시 오류 발생!");
			log.error("insert employee has some errors!");
			return "addemployee";
		}

		return "changenotify";
	}

	// v [Step 4-3-03] deptInfoOneDepthCategory 객체를 ModelAttributes 를 이용하고 있다
	// 아래를 완성하여라.
	// getDepartmentIdNameList 는 Map<String, String> 타입을 리턴하고 있다.
	// 또한 referenceDataOneDepthDept 메소드 도 Map<String, String> 타입을 리턴한다.
	@ModelAttribute("deptInfoOneDepthCategory")
	public Map<String, String> referenceDataOneDepthDept() {
		Map<String, String> list = departmentService.getDepartmentIdNameList("1");
		for (String key : list.keySet()) {
			log.debug("key = " + key + ", v = " + list.get(key));
		}
		return list;
	}


	// v [Step 4-6-01] defaultUpdateEmployee 메소드에 @RequestMapping을 이용하여
	// updateEmployee.do 요청시 value 와 method (GET)를 만들어준다.
	/**
	 * 초기 입력폼 호출시에 동작한다.(GET방식의 호출) 입력폼에 채워질 기존의 사원정보를 실제 가져오는 메소드는
	 * getEmployeeInfo이다.
	 */
	@RequestMapping(value = "/updateEmployee.do", method = RequestMethod.GET)
	public String defaultUpdateEmployee(@RequestParam("employeeid") String employeeid, ModelMap model) {
		Employee employee = employeeService.getEmployeeInfoById(employeeid);

		model.addAttribute("employee", employee);
		return "modifyemployee";
	}

	// 아래 구현 시, insertEmployee.do 실행시 employee 객체가 null 로 넘어온다...
	// TODO [Step 4-6-02] getEmployeeInfo 메소드에 updateEmployee.do 요청시 Employee 객체
	// 를 가져오도록 ModelAttribute 를 설정한다.
	// 파라미터로 “employeeid” key 가 넘어온다. 이것으로 EmployeeService 의 getEmployeeInfoById
	// 메소드를 호출하여 Employee를 구해 리턴한다.
	// (참고: referenceDataOneDepthDept() )
	/**
	 * 사원아이디에 해당하는 사원 정보를 가져와, ModelMap 객체에 저장한다. 따라서, 사원정보수정페이지 호출시에(GET 방식호출)
	 * 폼에서 기존의 사원 데이터를 보여 줄때, ModelMap 객체에 담긴 이 사원정보데이터를 사용할 수가 있다.
	 *
	 * @param id
	 *            사원아이디
	 * @return 사원정보
	 */
//	@ModelAttribute("employee")
//	public Employee getEmployeeInfo(String id) {
//		 Employee employee = employeeService.getEmployeeInfoById(id);
//         if (employee != null) {
//         	log.debug("get employee info : " + employee);
//         }
//         return employee;
//     }


	/**
	 * 사원수정폼 제출시(POST) 처리한다. 사용자가 사원정보 수정페이지에서 입력한 데이터는 ModelMap 객체에 저장되어 있는데,
	 * 메소드 파라미터에 annotation ModelAttribute 선언으로 인해 employee에는 사용자가 입력한 데이터가 바인딩
	 * 되어 있다. 이 데이터로 DB 업데이트를 수행한다.
	 *
	 * @param employee
	 *            사용자가 수정 폼페이지에서 입력한 사원정보.
	 * @param bindingResult
	 */
	@RequestMapping(value = "/updateEmployee.do", method = RequestMethod.POST)
	public String updateEmployee(@ModelAttribute("employee") Employee employee, BindingResult bindingResult) {

		beanValidator.validate(employee, bindingResult);

		if (bindingResult.hasErrors()) {
			return "modifyemployee";
		}

		employeeService.updateEmployee(employee);

		return "changenotify";
	}

	/**
	 * 사원정보 수정페이지의 부서정보리스트(selectbox)는 이 메소드가 ModelMap 객체에
	 * "deptInfoTwoDepthCategory" 이름의 속성으로 저장한 데이터를 사용하게 된다.
	 *
	 * @param employee
	 *            사원정보.
	 * @return <부서아이디,부서이름> Map 객체
	 */
	@ModelAttribute("deptInfoTwoDepthCategory")
	private Map<String, String> referenceDataTwoDepthDept(@ModelAttribute("employee") Employee employee) {
		if (employee == null) {
			return null;
		}
		log.info("+ dept two depth, super id = " + employee.getSuperdeptid());

		Map<String, String> list = departmentService.getDepartmentIdNameList("2", employee.getSuperdeptid());
		for (String key : list.keySet()) {
			log.debug("key = " + key + ", v = " + list.get(key));
		}
		return list;
	}

	// /**
	// * 사원정보 수정페이지의 상위부서정보(selectbox)는 이 메소드가 ModelMap 객체에
	// * "deptInfoOneDepthCategory" 이름의 속성으로 저장한 데이터를 사용하게 된다.
	// *
	// * @return <상위부서아이디,상위부서이름> Map 객체
	// */
	// @SuppressWarnings("unused")
	// @ModelAttribute("deptInfoOneDepthCategory")
	// private Map<String, String> referenceDataOneDepthDept() {
	// return departmentService.getDepartmentIdNameList("1");
	// }
}
