package candystore.repository;

import candystore.model.BanList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanListRepository extends JpaRepository<BanList,Integer> {
}
