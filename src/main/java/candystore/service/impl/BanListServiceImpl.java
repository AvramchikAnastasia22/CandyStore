package candystore.service.impl;

import candystore.model.BanList;
import candystore.model.User;
import candystore.repository.BanListRepository;
import candystore.service.BanListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BanListServiceImpl implements BanListService {
    @Autowired
    BanListRepository repository;
    @Override
    public List<BanList> getBanListAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Integer user_id) {
        repository.deleteById(user_id);
    }

    @Override
    public void createUserBlock(User user, String comment) {
        BanList banList=new BanList();
        banList.setFIO(user.getName()+" "+user.getSur_name()+" "+user.getPatronymic());
        banList.setReason(comment);
        banList.setName_photo(user.getName_photo_file());
        banList.setId_employee(user.getId());
        repository.save(banList);

    }
}
