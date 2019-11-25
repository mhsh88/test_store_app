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

    @Override
    public DetergentOutDto insert( DetergentInsertDto insertDto) {

        if(insertDto.getBringInDate().isBefore(LocalDateTime.of(insertDto.getProductionDate(), LocalTime.of(0,0)))){
            throw new IllegalArgumentException("bring in date must be after production date!");
        }
        if(insertDto.getBringOutDate().isBefore(insertDto.getBringInDate()))
        {
            throw new IllegalArgumentException("bring out date must be before bring in date!");
        }
        if(insertDto.getExpirationDate().isBefore(insertDto.getProductionDate())){

            throw new IllegalArgumentException("expiration date must be after production date!");
        }
        if(insertDto.getExpirationDate().isBefore(insertDto.getBringInDate().toLocalDate())){
            throw new IllegalArgumentException("the product must not be expired for storing!");
        }
        return super.insert(insertDto);
    }
}
