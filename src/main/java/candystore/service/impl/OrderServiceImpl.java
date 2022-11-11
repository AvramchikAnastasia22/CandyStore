package candystore.service.impl;

import candystore.model.Employee;
import candystore.model.Order;
import candystore.repository.EmployeeRepository;
import candystore.repository.OrderRepository;
import candystore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository repository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Value("${upload.path_order}")
    private String load_order;

    @Override
    public List<Order> getOrderList() {
        return repository.findAll();
    }

    @Override
    public void addOrder(String type, String name_order, double price, MultipartFile photo_order) throws IOException {
        Order order = new Order();
        order.setNameOrder(name_order);
        order.setType(type);
        order.setPrice(price);

        File file = new File(load_order);
        if (!photo_order.isEmpty()) {
            String uuid_file = UUID.randomUUID().toString();
            String Or_file_name = photo_order.getOriginalFilename().substring(photo_order.getOriginalFilename().indexOf("."), photo_order.getOriginalFilename().length());
            String file_name = uuid_file + "_" + Or_file_name;
            photo_order.transferTo(new File(file.getAbsolutePath() + "/" + file_name));
            order.setName_photo_file(file_name);
        }
        repository.save(order);
    }

    @Override
    public Order getOrderById(int id_order) {
        return repository.findById(id_order).get();
    }

    @Override
    public void deleteOrder(Integer id_order) {
        File file=new File(load_order);
        File file1=new File(file.getAbsolutePath()+"/"+repository.findById(id_order).get().getName_photo_file());
        file1.delete();
        List<Employee>list=employeeRepository.findAll();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getId_order()==id_order){
                list.get(i).setId_order(-1);
            }
        }
        employeeRepository.saveAll(list);
        repository.deleteById(id_order);
    }


}
