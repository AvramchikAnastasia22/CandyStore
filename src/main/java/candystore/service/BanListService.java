package candystore.service;

import candystore.model.BanList;
import candystore.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BanListService {
    List<BanList> getBanListAll();

    void deleteById(Integer user_id);

    void createUserBlock(User user, String comment);
}
