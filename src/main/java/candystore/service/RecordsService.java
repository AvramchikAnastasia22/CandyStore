package candystore.service;

import candystore.model.Records;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface RecordsService {
    List<Records> sortRecord(List<Records>list);
    void close_record() throws ParseException;

    List<Records> getRecordsList();
    void addRecords(int id_order, int id_employee, String fio_employee, LocalDateTime date);

    void deleteById(Integer id_order);
}
