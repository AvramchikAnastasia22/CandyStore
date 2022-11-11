package candystore.service.impl;

import candystore.model.BanList;
import candystore.model.User;
import candystore.repository.UserRepository;
import candystore.service.BanListService;
import candystore.service.UserService;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    BanListService banListService;
    @Value("${upload.path_user}")
    private String load_user;
@Override
    public void first_registration() {
        List<User> users = getAllUsers();
        if (users.size() == 0) {
            Date date = new Date();
            SimpleDateFormat simpl = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String dat = simpl.format(date);
            User user = new User();
            user.setName("Анастасия");
            user.setSur_name("Панфилова");
            user.setPatronymic("Сергеевна");
            user.setName_photo_file("Women.png");
            user.setPhone("293708501");
            user.setType("Admin");
            user.setStatus("Активно");
            user.setLogin("123");
            user.setPassword("123");
            user.setData_registration(dat);
            repository.save(user);
        }
    }
    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public void registration(String name, String sur_name, String patronymic, String phone, String login, String password) {
        Date date=new Date();
        SimpleDateFormat simpl=new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dat=simpl.format(date);
        User user=new User();
        user.setName(name);
        user.setSur_name(sur_name);
        user.setPatronymic(patronymic);
        user.setPhone(phone);
        user.setLogin(login);
        user.setPassword(password);
        user.setName_photo_file("no_name");
        user.setStatus("Активно");
        user.setType("User");
        user.setData_registration(dat);
        repository.save(user);
    }
    private int pers_id;
    @Override
    public void updateSettings(String name_set, String sur_set, String patr_set, String phone_set, String login_set, String password_set, MultipartFile pers_photo) throws IOException {
        User employee=repository.findById(pers_id).get();
        employee.setName(name_set);
        employee.setSur_name(sur_set);
        employee.setPatronymic(patr_set);
        employee.setPhone(phone_set);
        employee.setLogin(login_set);
        employee.setPassword(password_set);
        if(!pers_photo.getOriginalFilename().isEmpty()&&!pers_photo.getOriginalFilename().equals(employee.getName_photo_file())){
            File file=new File(load_user);
            if(!file.exists()) {
                file.mkdir();
            }
            if(!pers_photo.isEmpty()){
                if(!employee.getName_photo_file().equals("Meni.png")&&!employee.getName_photo_file().equals("Women.png")){
                    File file1=new File(file.getAbsolutePath()+"/"+employee.getName_photo_file());
                    file1.delete();
                }
                String uuid_file= UUID.randomUUID().toString();
                String Or_file_name=pers_photo.getOriginalFilename().substring(pers_photo.getOriginalFilename().indexOf("."),pers_photo.getOriginalFilename().length());
                String file_name=uuid_file+"_"+employee.getId()+Or_file_name;
                pers_photo.transferTo(new File(file.getAbsolutePath()+"/"+file_name));
                employee.setName_photo_file(file_name);
            }
        }
        repository.save(employee);
    }

    @Override
    public User getUserById(Integer id_user) {
        return repository.findById(id_user).get();
    }

    @Override
    public void deletedUser(Integer id) {
        if(!repository.findById(id).get().getName_photo_file().equals("no_name.png")){
            File file=new File(load_user);
            File file1=new File(file.getAbsolutePath()+"/"+repository.findById(id).get().getName_photo_file());
            file1.delete();
        }
        repository.deleteById(id);
    }

    @Override
    public void doAdmin(Integer user_id) {
        User user=getUserById(user_id);
        user.setType("Admin");
        repository.save(user);
    }

    @Override
    public void unblockUser(Integer user_id) {
        User user=getUserById(user_id);
        user.setStatus("Активно");
        banListService.deleteById(user_id);
        repository.save(user);
    }

    @Override
    public void blockUser(String comment, int id_user) {
        User user=getUserById(id_user);
        user.setStatus("Заблокировано");
        banListService.createUserBlock(user, comment);
        repository.save(user);
    }


}
