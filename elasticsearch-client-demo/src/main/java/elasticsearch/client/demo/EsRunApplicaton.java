package elasticsearch.client.demo;

import elasticsearch.client.demo.domain.Employee;
import elasticsearch.client.demo.service.EmployeeService;

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

        employeeService.addEmployee(new Employee(1,"John"
                ,"Smith",25,"I love to go rock climbing", Arrays.asList("sports","music")));

        employeeService.addEmployee(new Employee(2,"Jane"
                ,"Smith",32,"I like to collect rock albums", Arrays.asList("music")));

        employeeService.addEmployee(new Employee(3,"Douglas"
                ,"Fir",35,"I like to build cabinets", Arrays.asList("forestry")));
    }
}
