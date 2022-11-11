package candystore.service.impl;

import candystore.model.History;
import candystore.model.Records;
import candystore.model.User;
import candystore.repository.HistoryRepository;
import candystore.repository.RecordsRepository;
import candystore.service.RecordsService;
import candystore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class RecordsServiceImpl implements RecordsService {
    @Autowired
    RecordsRepository repository;
    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    UserService userService;
    private int pers_id;
    @Override
    public List<Records> sortRecord(List<Records>list){
        for (int i=list.size()-1;i>=0;i--){
            if(list.get(i).getId_user()!=pers_id){
                list.remove(i);
            }
            else{
                if(list.get(i).getStatus().equals("Завершено")){
                    list.remove(i);
                }
            }
        }
        return list;
    }
    @Override
    public void close_record() throws ParseException {
        List<Records>list=repository.findAll();
        for (int i=0;i<list.size();i++){
            Date date1=new Date();
            SimpleDateFormat smpl= new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date=smpl.parse(list.get(i).getDate());
            if(date.before(date1)&&list.get(i).getStatus().equals("Ожидается")){
                History history=new History();
                history.setId_user(list.get(i).getId_user());
                history.setDate(list.get(i).getDate());
                history.setType(list.get(i).getType());
                history.setFIO_employee(list.get(i).getFIO_employee());
                history.setPrice(list.get(i).getPrice());
                list.get(i).setStatus("Завершено");
                historyRepository.save(history);
            }
        }
        repository.saveAll(list);
    }

    @Override
    public List<Records> getRecordsList() {
        return repository.findAll();
    }
    @Override
    public void addRecords(int id_order, int id_employee, String fio_employee, LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String dat = date.format(formatter);
        User user=userService.getUserById(pers_id);
        Records records=new Records();
        records.setStatus("Ожидается");
        records.setId_user(pers_id);
        records.setId_employee(id_employee);
        records.setPrice(repository.findById(id_order).get().getPrice());
        records.setId_service(id_order);
        records.setFIO_employee(fio_employee);
        records.setFIO_user(user.getName()+" "+user.getSur_name()+" "+user.getPatronymic());
        records.setDate(dat);
        records.setType(repository.findById(id_order).get().getType());
        repository.save(records);
    }

    @Override
    public void deleteById(Integer id_order) {
        repository.deleteById(id_order);
    }
}
