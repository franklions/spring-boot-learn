package elasticsearch.client.demo;

import elasticsearch.client.demo.domain.Employee;
import elasticsearch.client.demo.service.EmployeeService;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/7
 * @since Jdk 1.8
 */
public class EsRunApplicaton {

    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();

        // 添加员工信息
        employeeService.addEmployee(new Employee(1,"John"
                ,"Smith",25,"I love to go rock climbing", Arrays.asList("sports","music")));

        employeeService.addEmployee(new Employee(2,"Jane"
                ,"Smith",32,"I like to collect rock albums", Arrays.asList("music")));

        employeeService.addEmployee(new Employee(3,"Douglas"
                ,"Fir",35,"I like to build cabinets", Arrays.asList("forestry")));

        //查询员工1的信息
        Employee employee1 = employeeService.getEmployee(1);
        System.out.println(employee1);
//        System.out.println(employeeService.getEmployee(2));
//        System.out.println(employeeService.getEmployee(3));

//        //修改员工1d
//        employee1.setFirst_name("John bob");
//        employeeService.updateEmployee(employee1);
//        //再次打印修改后的员工1
//        System.out.println(employeeService.getEmployee(1));

        //查询多个文档
        System.out.println(employeeService.searchEmployee());

        try {
            Integer key = System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
