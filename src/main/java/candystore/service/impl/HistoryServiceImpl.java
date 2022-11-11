package candystore.service.impl;

import candystore.model.History;
import candystore.repository.HistoryRepository;
import candystore.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    HistoryRepository repository;
    private int pers_id;
    @Override
    public List<History> sortHistory(List<History>list) {
        for (int i=list.size()-1;i>=0;i--){
            if(list.get(i).getId_user()!=pers_id){
                list.remove(i);
            }
        }
        return list;
    }

    @Override
    public List<History> getHistoryList() {
        return repository.findAll();
    }
}
