package candystore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String nameOrder;
    String type;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOrder() {
        return nameOrder;
    }

    public void setNameOrder(String nameOrder) {
        this.nameOrder = nameOrder;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName_photo_file() {
        return name_photo_file;
    }

    public void setName_photo_file(String name_photo_file) {
        this.name_photo_file = name_photo_file;
    }



    @Override
    public String toString() {
        return "Заказ: " +

                "Название:" + nameOrder +
                ", тип:" + type +
                ", стоимость:" + price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    double price;
    String name_photo_file;
}
