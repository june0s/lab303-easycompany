package com.easycompany.mapper;

import com.easycompany.service.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/context-*" })
@Transactional
public class EmpMapperTest {

    @Autowired
    private EmpMapper empMapper;

    @Test
    public void getAllEmployees() {
        // given
        Map<String, Object> map = new HashMap<>();
        // mysql 에서는 아래 2 인자 넣어줘야 한다!
        map.put("recordCountPerPage", 10);
        map.put("firstIndex", 0);

        // when
        List<Employee> employees = empMapper.getAllEmployees(map);

        // then
        assertTrue(0 < employees.size());
    }

    @Test
    public void getAllEmployees_bySearchID() {
        // given
        String id = "2000";
        Employee employee = new Employee(id, "tom", 20, "1200", "1234", "tom@tams.com");
        empMapper.insertEmployee(employee);

        Map<String, Object> map = new HashMap<>();
        map.put("searchEid", id);
        map.put("recordCountPerPage", 5);
        map.put("firstIndex", 0);

        // when
        List<Employee> employees = empMapper.getAllEmployees(map);

        // then
        assertTrue(1 == employees.size());
        assertEquals(id, employees.get(0).getEmployeeid());
        for (Employee emp : employees) {
            System.out.println(emp.toString());
        }
    }

    // 부서 id로 조회
    @Test
    public void getAllEmployees_byDepartmentID() throws Exception {
        // given
        String id = "2000";
        String deptId = "5200";
        Employee employee = new Employee(id, "tom", 20, deptId, "1234", "tom@tams.com");
        empMapper.insertEmployee(employee);

        Map<String, Object> map = new HashMap<>();
        map.put("searchDid", deptId);
        map.put("recordCountPerPage", 100);
        map.put("firstIndex", 0);

        // when
        List<Employee> employees = empMapper.getAllEmployees(map);

        // then
        assertTrue(1 <= employees.size());
        int i = 0;
        for (Employee emp : employees) {
            assertEquals(deptId, employees.get(i++).getDepartmentid());
            System.out.println(emp.toString());
        }
    }

    // 이름으로 조회
    @Test
    public void getAllEmployees_byName() throws Exception {
        // given
        String id = "2000";
        String deptId = "5200";
        String name = "tom";
        Employee employee = new Employee(id, name, 20, deptId, "1234", "tom@tams.com");
        empMapper.insertEmployee(employee);

        Map<String, Object> map = new HashMap<>();
        map.put("searchName", name);
        map.put("recordCountPerPage", 100);
        map.put("firstIndex", 0);

        // when
        List<Employee> employees = empMapper.getAllEmployees(map);

        // then
        assertTrue(1 <= employees.size());
        assertEquals(name, employees.get(0).getName());
        for (Employee emp : employees) {
            System.out.println(emp.toString());
        }
    }

    // id & 부서id 검색
    @Test
    public void getAllEmployees_byIdAndDeptId() throws Exception {
        // given
        String id = "2000";
        String deptId = "5200";
        String name = "tom";
        Employee employee = new Employee(id, name, 20, deptId, "1234", "tom@tams.com");
        empMapper.insertEmployee(employee);

        Map<String, Object> map = new HashMap<>();
        map.put("searchEid", id);
        map.put("searchDid", deptId);
        map.put("recordCountPerPage", 100);
        map.put("firstIndex", 0);

        // when
        // select employeeid, name, age, departmentid, password, email from employee
        // WHERE employeeid =? and departmentid =? order by cast(employeeid as unsigned) limit ? OFFSET ?
        // Parameters: 2000(String), 5200(String), 100(Integer), 0(Integer)
        List<Employee> employees = empMapper.getAllEmployees(map);

        // then
        assertTrue(1 <= employees.size());
        assertEquals(id, employees.get(0).getEmployeeid());
        assertEquals(deptId, employees.get(0).getDepartmentid());
        for (Employee emp : employees) {
            System.out.println(emp.toString());
        }
    }

    // id & 이름 검색
    @Test
    public void getAllEmployees_byIdAndName() throws Exception {
        // given
        String id = "2000";
        String deptId = "5200";
        String name = "tom";
        Employee employee = new Employee(id, name, 20, deptId, "1234", "tom@tams.com");
        empMapper.insertEmployee(employee);

        Map<String, Object> map = new HashMap<>();
        map.put("searchEid", id);
        map.put("searchName", name);
        map.put("recordCountPerPage", 100);
        map.put("firstIndex", 0);

        // when
        // select employeeid, name, age, departmentid, password, email from employee
        // WHERE employeeid =? and name like '%tom%' order by cast(employeeid as unsigned) limit ? OFFSET ?
        // 2000(String), 100(Integer), 0(Integer)
        List<Employee> employees = empMapper.getAllEmployees(map);

        // then
        assertTrue(1 <= employees.size());
        assertEquals(id, employees.get(0).getEmployeeid());
        assertEquals(name, employees.get(0).getName());
        for (Employee emp : employees) {
            System.out.println(emp.toString());
        }
    }

    // 부서id & 이름 검색
    @Test
    public void getAllEmployees_byDeptidAndName() throws Exception {
        // given
        String id = "2000";
        String deptId = "5200";
        String name = "tom";
        Employee employee = new Employee(id, name, 20, deptId, "1234", "tom@tams.com");
        empMapper.insertEmployee(employee);

        Map<String, Object> map = new HashMap<>();
        map.put("searchDid", deptId);
        map.put("searchName", name);
        map.put("recordCountPerPage", 100);
        map.put("firstIndex", 0);

        // when
        // select employeeid, name, age, departmentid, password, email from employee
        // WHERE departmentid =? and name like '%tom%' order by cast(employeeid as unsigned) limit ? OFFSET ?
        // 5200(String), 100(Integer), 0(Integer)
        List<Employee> employees = empMapper.getAllEmployees(map);

        // then
        assertTrue(1 <= employees.size());
        assertEquals(deptId, employees.get(0).getDepartmentid());
        assertEquals(name, employees.get(0).getName());
        for (Employee emp : employees) {
            System.out.println(emp.toString());
        }
    }

    // page 개수 테스트
    @Test
    public void getAllEmployees_page() throws Exception {
        // given
        String id = "2000";
        String deptId = "1200";
        int pageNum = 5;
        Employee employee = new Employee(id, "tom", 20, deptId, "1234", "tom@tams.com");
        empMapper.insertEmployee(employee);

        Map<String, Object> map = new HashMap<>();
        map.put("recordCountPerPage", pageNum);
        map.put("firstIndex", 0);

        // when
        // select employeeid, name, age, departmentid, password, email from employee order by cast(employeeid as unsigned) limit ? OFFSET ?
        // 5(Integer), 0(Integer)
        List<Employee> employees = empMapper.getAllEmployees(map);

        // then
        assertTrue(pageNum == employees.size());
        for (Employee emp : employees) {
            System.out.println(emp.toString());
        }
    }

    // page 개수 테스트 (직원수보다 더 큰 숫자로 조회)
    @Test
    public void getAllEmployees_pageOver() throws Exception {
        // given
        String id = "2000";
        String deptId = "1200";
        Employee employee = new Employee(id, "tom", 20, deptId, "1234", "tom@tams.com");
        empMapper.insertEmployee(employee);

        Map<String, Object> map = new HashMap<>();
        int empCount = empMapper.getEmployeeCount(map);

        int pageNum = empCount + 1;
        map.put("recordCountPerPage", pageNum);
        map.put("firstIndex", 0);

        // when
        // select employeeid, name, age, departmentid, password, email from employee order by cast(employeeid as unsigned) limit ? OFFSET ?
        // 100(Integer), 0(Integer)
        List<Employee> employees = empMapper.getAllEmployees(map);

        // then
        assertTrue(empCount == employees.size());
        assertTrue(pageNum > employees.size());
        for (Employee emp : employees) {
            System.out.println(emp.toString());
        }
    }

    // offset  테스트
    @Test
    public void getAllEmployees_offset() throws Exception {
        // given
//        String id = "2000";
//        String deptId = "1200";
//        Employee employee = new Employee(id, "tom", 20, deptId, "1234", "tom@tams.com");
//        empMapper.insertEmployee(employee);

        Map<String, Object> map = new HashMap<>();
        int pageNum = 10;
        map.put("recordCountPerPage", pageNum);
        map.put("firstIndex", 0);

        // when
        // select employeeid, name, age, departmentid, password, email from employee order by cast(employeeid as unsigned) limit ? OFFSET ?
        // 10(Integer), 0(Integer)
        List<Employee> employees = empMapper.getAllEmployees(map);

        // then
        int empId = 1;
        for (Employee emp : employees) {
            assertEquals(Integer.toString(empId++), emp.getEmployeeid());
            System.out.println(emp.toString());
        }
    }

    // offset(시작 위치)  테스트
    @Test
    public void getAllEmployees_offsetNext() throws Exception {
        // given
        Map<String, Object> map = new HashMap<>();
        int pageNum = 10;
        map.put("recordCountPerPage", pageNum);
        map.put("firstIndex", 1);

        // when - 두번째 임직원부터 pageNum 만큼 조회.
        // select employeeid, name, age, departmentid, password, email from employee order by cast(employeeid as unsigned) limit ? OFFSET ?
        // 10(Integer), 1(Integer)
        List<Employee> employees = empMapper.getAllEmployees(map);

        // then
        int empId = 2;
        for (Employee emp : employees) {
            assertEquals(Integer.toString(empId++), emp.getEmployeeid());
            System.out.println(emp.toString());
        }
    }

    // offset(시작 위치)  테스트
    @Test
    public void getAllEmployees_offsetOver() throws Exception {
        // given
        Map<String, Object> map = new HashMap<>();
        int pageNum = 10;
        map.put("recordCountPerPage", pageNum);
        map.put("firstIndex", 100);

        // when - 100째 임직원부터 pageNum 만큼 조회.
        // select employeeid, name, age, departmentid, password, email from employee order by cast(employeeid as unsigned) limit ? OFFSET ?
        // 10(Integer), 100(Integer)
        List<Employee> employees = empMapper.getAllEmployees(map);

        // then
        assertEquals(0, employees.size());
    }

    // ID로 조회
    @Test
    public void getEmployeeInfoById() {
        // given
        String id = "2000";
        String deptId = "1200";
        Employee employee = new Employee(id, "tom", 20, deptId, "1234", "tom@tams.com");
        empMapper.insertEmployee(employee);

        // when
        // select employeeid, name, age, departmentid, password, email, superdeptid from employee a, department b
        // where a.departmentid = b.deptid and employeeid = ?
        // 2000(String)
        Employee saved = empMapper.getEmployeeInfoById(id);

        // then
        assertNotNull(saved);
        assertEquals(id, saved.getEmployeeid());
        System.out.println(saved.toString());
    }

    // 삭제
    @Test
    public void deleteEmployee() {
        // given
        String id = "2000";
        String deptId = "1200";
        Employee employee = new Employee(id, "tom", 20, deptId, "1234", "tom@tams.com");
        empMapper.insertEmployee(employee);

        // when
        // delete from employee where employeeid = ?
        // 2000(String)
        empMapper.deleteEmployee(id);

        // then
        Employee find = empMapper.getEmployeeInfoById(id);
        assertNull(find);
    }

    // 추가
    @Test
    public void insertEmployee() {
        // given
        String id = "2000";
        String deptId = "1200";
        Employee employee = new Employee(id, "tom", 20, deptId, "1234", "tom@tams.com");

        // when
        // insert into employee ( employeeid, name, age, departmentid, email, password) values ( ?, ?, ?, ?, ?, ?)
        // 2000(String), tom(String), 20(Integer), 1200(String), tom@tams.com(String), 1234(String)
        int result = empMapper.insertEmployee(employee);

        // then
        assertEquals(1, result);
        Employee saved = empMapper.getEmployeeInfoById(id);
        assertNotNull(saved);
        assertEquals(id, saved.getEmployeeid());
        System.out.println(saved.toString());
    }

    @Test
    public void updateEmployee() {
        // given
        String id = "2000";
        String deptId = "1200";
        Employee employee = new Employee(id, "tom", 20, deptId, "1234", "tom@tams.com");
        empMapper.insertEmployee(employee);

        // when
        // update employee set name = ?, age = ?, departmentid = ?, email = ?, password = ? where employeeid = ?
        // tomy(String), 21(Integer), 1100(String), tomy@tams.com(String), 1259(String), 2000(String)
        Employee updateEmployee = new Employee(id, "tomy", 21, "1100", "1259", "tomy@tams.com");
        int result = empMapper.updateEmployee(updateEmployee);

        // then
        assertEquals(1, result);
        Employee find = empMapper.getEmployeeInfoById(id);

        assertNotNull(find);
        assertEquals(updateEmployee.getEmployeeid(), find.getEmployeeid());
        assertEquals(updateEmployee.getName(), find.getName());
        assertEquals(updateEmployee.getAge(), find.getAge());
        assertEquals(updateEmployee.getDepartmentid(), find.getDepartmentid());
        assertEquals(updateEmployee.getPassword(), find.getPassword());
        assertEquals(updateEmployee.getEmail(), find.getEmail());
        System.out.println(find.toString());
    }

    // 유사 이름 검색
    @Test
    public void getNameListForSuggest() {
        // given
        String prefix = "Kim";
        Map<String, Object> map = new HashMap<>();
        int totalCount = empMapper.getEmployeeCount(map);

        // when
        // select name from employee where name like '%Kim%'
        List<String> names = empMapper.getNameListForSuggest(prefix);

        // then
        assertTrue(names.size() < totalCount);
        assertTrue(0 < names.size());
        System.out.println("+ suggested name ==");
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void getEmployeeCount() {
        Map<String, Object> map = new HashMap<>();

        int count = empMapper.getEmployeeCount(map);
        System.out.println("count : " + count);
    }
}
