package elasticsearch.client.demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import elasticsearch.client.demo.domain.Employee;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/8
 * @since Jdk 1.8
 */

public class EmployeeService {

    public boolean addEmployee(Employee employee){
             return ESUtils.indexDocument(employee.getId().toString(),employee);
    }

    public boolean deleteEmployee(Integer id){
            return ESUtils.deleteDocument(id.toString());
    }

    public Employee getEmployee(Integer id){
            return ESUtils.getDocument(id.toString(), new TypeReference<Employee>() {});
    }

    public boolean updateEmployee(Employee employee){
        return ESUtils.updateDocument(employee.getId().toString(),employee);
    }
}
