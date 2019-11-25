package ir.sharifi.soroush.soroush_test_project.detergent.service;

import ir.sharifi.soroush.soroush_test_project.base.repository.BaseRepository;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseServiceImpl;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentInDto;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentInsertDto;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentOutDto;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentUpdateDto;
import ir.sharifi.soroush.soroush_test_project.detergent.model.Detergent;
import ir.sharifi.soroush.soroush_test_project.detergent.repo.DetergentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class DetergentServiceImpl extends BaseServiceImpl<Detergent, Long, DetergentInDto, DetergentOutDto, DetergentInsertDto, DetergentUpdateDto> implements IDetergentService {

    private final DetergentRepository detergentRepository;

    @Autowired
    public DetergentServiceImpl(DetergentRepository detergentRepository) {
        this.detergentRepository = detergentRepository;
    }

    @Override
    public BaseRepository<Detergent, Long> getRepository() {
        return detergentRepository;
    }

    @Override
    @Cacheable("getAllDetergent")
    public List<DetergentOutDto> getModels() {
        return super.getModels();
    }
}
