package com.easycompany.service;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/context-*" })
@Transactional
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Before
    public void onSetUp() {
//        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/db/sampledb.sql"));
    }

    /**
     * 사원 조회.
     */
    @Test
    public void testGetEmployee() {
        // given
        String id = "10";

        // when
        Employee employee = employeeService.getEmployeeInfoById(id);

        // then
        assertNotNull(employee);
        assertEquals(id, employee.getEmployeeid());
        assertNotNull(employee.getName());
        assertNotNull(employee.getPassword());
        assertNotNull(employee.getEmail());
        assertNotNull(employee.getSuperdeptid());

        System.out.println("+ testGetEmployee() ==");
        System.out.println(employee);
    }

    // 사원 조회 (실패)
    @Test
    public void testGetEmployeeNotFound() {
        // given
        String id = "10000";

        // when
        Employee employee = employeeService.getEmployeeInfoById(id);

        // then
        assertNull(employee);
    }

    // 사원 리스트 조회
    @Test
    public void testEmployeeList() throws Exception {
        // given
        int count = 10;
        for (int i = 0; i < count; i++) {
            String id = Integer.toString(1000 + i);
            String name = "tom" + i;
            employeeService.insertEmployee(new Employee(id, name, 20+i, "1200", "1234", name + "@tams.com"));
        }

        // when
        int recordCount = 5;
        Map<String, Object> params = new HashMap<>();
        params.put("firstIndex", 0);
        params.put("recordCountPerPage", recordCount);

        List<Employee> employees = employeeService.getAllEmployees(params);

        // then
        System.out.println("+ testEmployeeList() ==");
        assertEquals(recordCount, employees.size());
        for (Employee employee : employees) {
            System.out.println(employee.toString());
        }
    }

    // 사원 추가
    @Test
    public void testAddEmployee() {
        // given
        String id = "2000";
        Employee employee = new Employee(id, "tom", 20, "1200", "1234", "tom@tams.com");

        // when
        int result = employeeService.insertEmployee(employee);

        // then
        assertTrue(0 < result);
        Employee saved = employeeService.getEmployeeInfoById(id);
        assertEquals(saved.getEmployeeid(), employee.getEmployeeid());
        assertEquals(saved.getName(), employee.getName());
        assertEquals(saved.getAge(), employee.getAge());
        assertEquals(saved.getDepartmentid(), employee.getDepartmentid());
        assertEquals(saved.getPassword(), employee.getPassword());
        assertEquals(saved.getEmail(), employee.getEmail());
    }

    // 사원 추가 실패 (중복)
    @Test
    public void testAddEmployeeDuplicated() {
        // given
        String id = "1000";
        Employee employee = new Employee(id, "tom", 20, "1200", "1234", "tom@tams.com");
        employeeService.insertEmployee(employee);

        // when
        int result = employeeService.insertEmployee(employee);

        // then
        assertTrue(0 > result);
    }

    // 사원 갱신
    @Test
    public void testUpdateEmployee() {
        // given
        String id = "1000";
        Employee employee = new Employee(id, "tom", 20, "1200", "1234", "tom@tams.com");
        employeeService.insertEmployee(employee);

        // when
        Employee modEmployee = new Employee(id, "tomy", 22, "2100", "1111", "tomy@tams.com");
        int result = employeeService.updateEmployee(modEmployee);

        // then
        assertTrue(result > 0);
        Employee find = employeeService.getEmployeeInfoById(id);
        System.out.println(find.toString());
        assertEquals(find.getEmployeeid(), modEmployee.getEmployeeid());
        assertEquals(find.getName(), modEmployee.getName());
        assertEquals(find.getAge(), modEmployee.getAge());
        assertEquals(find.getDepartmentid(), modEmployee.getDepartmentid());
        assertEquals(find.getPassword(), modEmployee.getPassword());
        assertEquals(find.getEmail(), modEmployee.getEmail());
    }

    // 사원 갱신 실패 (Not exist)
    @Test
    public void testUpdateEmployeeNotExist() throws Exception {
        // given
        String id = "2000";
//        Employee employee = new Employee(id, "tom", 20, "1200", "1234", "tom@tams.com");
//        employeeService.insertEmployee(employee);
        Employee find = employeeService.getEmployeeInfoById(id);
        assertNull(find);

        // when
        Employee modEmployee = new Employee(id, "tomy", 22, "2100", "1111", "tomy@tams.com");
        int result = employeeService.updateEmployee(modEmployee);

        // then
        assertTrue(result <= 0);
    }


    // 사원 삭제
    @Test
    public void testDeleteEmployee() throws Exception {
        // given
        String id = "1000";
        Employee employee = new Employee(id, "tom", 20, "1200", "1234", "tom@tams.com");
        employeeService.insertEmployee(employee);

        // when
        employeeService.deleteEmployee(id);

        // then
        Employee find = employeeService.getEmployeeInfoById(id);
        assertNull(find);
    }

}
