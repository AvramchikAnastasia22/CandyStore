package candystore.service.impl;

import candystore.model.Employee;
import candystore.model.Order;
import candystore.repository.EmployeeRepository;
import candystore.service.EmployeeService;
import candystore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository repository;
    @Autowired
    OrderService orderService;
    @Value("${upload.path_employee}")
    private  String load_employee;
    @Override
    public List<Employee> getEmployeeList() {
        return repository.findAll();
    }

    @Override
    public void deleteEmployee(Integer employee_id) {
        File file=new File(load_employee);
        File file1=new File(file.getAbsolutePath()+"/"+repository.findById(employee_id).get().getName_photo_file());
        file1.delete();
        repository.deleteById(employee_id);
    }

    @Override
    public void addEmployee(String name, String sur_name, String patronymic, String phone, String position, int id_order, MultipartFile photo_employee) throws IOException {
        Date date=new Date();
        SimpleDateFormat simpl=new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dat=simpl.format(date);
        Order order= orderService.getOrderById(id_order);
        Employee employee=new Employee();
        employee.setName(name);
        employee.setSecond_name(sur_name);
        employee.setPatronymic(patronymic);
        employee.setPhone(phone);
        employee.setPosition(order.getType());
        employee.setDate_settled(dat);
        employee.setId_order(id_order);
        File file=new File(load_employee);
        if(!photo_employee.isEmpty()){
            String uuid_file= UUID.randomUUID().toString();
            String Or_file_name=photo_employee.getOriginalFilename().substring(photo_employee.getOriginalFilename().indexOf("."),photo_employee.getOriginalFilename().length());
            String file_name=uuid_file+"_"+Or_file_name;
            photo_employee.transferTo(new File(file.getAbsolutePath()+"/"+file_name));
            employee.setName_photo_file(file_name);
        }
        repository.save(employee);
    }
}
