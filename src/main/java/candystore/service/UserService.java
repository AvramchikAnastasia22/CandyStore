package candystore.service;

import candystore.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {
    void first_registration();
    List<User> getAllUsers();

    void registration(String name, String sur_name, String patronymic, String phone, String login, String password);

    void updateSettings(String name_set, String sur_set, String patr_set, String phone_set, String login_set, String password_set, MultipartFile pers_photo) throws IOException;


    User getUserById(Integer id_user);
    void deletedUser(Integer id);

    void doAdmin(Integer user_id);

    void unblockUser(Integer user_id);

    void blockUser(String comment, int id_user);
}
