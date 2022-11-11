package candystore.service;

import candystore.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface EmployeeService {
    List<Employee> getEmployeeList();

    void deleteEmployee(Integer employee_id);

    void addEmployee(String name, String sur_name, String patronymic, String phone, String position, int id_order, MultipartFile photo_employee) throws IOException;
}
