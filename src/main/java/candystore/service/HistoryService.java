package candystore.service;

import candystore.model.History;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HistoryService {
    List<History> sortHistory(List<History>list);

    List<History> getHistoryList();
}
