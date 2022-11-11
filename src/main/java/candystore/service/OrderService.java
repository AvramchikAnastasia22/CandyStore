package candystore.service;

import candystore.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface OrderService {
    List<Order> getOrderList();

    void addOrder(String type, String name_order, double price, MultipartFile photo_order) throws IOException;

    Order getOrderById(int id_order);

    void deleteOrder(Integer id_order);
}
