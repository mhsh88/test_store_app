package ir.sharifi.soroush.soroush_test_project.detergent.repo;

import ir.sharifi.soroush.soroush_test_project.base.repository.BaseRepository;
import ir.sharifi.soroush.soroush_test_project.detergent.model.Detergent;
import org.springframework.stereotype.Repository;


@Repository
public interface DetergentRepository extends BaseRepository<Detergent, Long> {

}
