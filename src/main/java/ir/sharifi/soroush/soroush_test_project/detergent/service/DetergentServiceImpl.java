package ir.sharifi.soroush.soroush_test_project.detergent.service;

import ir.sharifi.soroush.soroush_test_project.base.repository.BaseRepository;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseServiceImpl;
import ir.sharifi.soroush.soroush_test_project.detergent.model.Detergent;
import ir.sharifi.soroush.soroush_test_project.detergent.repo.DetergentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetergentServiceImpl extends BaseServiceImpl<Detergent, Long> implements IDetergentService {

    private final DetergentRepository detergentRepository;

    @Autowired
    public DetergentServiceImpl(DetergentRepository detergentRepository) {
        this.detergentRepository = detergentRepository;
    }

    @Override
    public BaseRepository<Detergent, Long> getRepository() {
        return detergentRepository;
    }
}
