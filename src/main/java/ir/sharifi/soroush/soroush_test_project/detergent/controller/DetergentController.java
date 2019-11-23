package ir.sharifi.soroush.soroush_test_project.detergent.controller;

import ir.sharifi.soroush.soroush_test_project.base.controller.BaseController;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseService;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentInDto;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentInsertDto;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentOutDto;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentUpdateDto;
import ir.sharifi.soroush.soroush_test_project.detergent.model.Detergent;
import ir.sharifi.soroush.soroush_test_project.detergent.service.IDetergentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/detergent")
public class DetergentController extends BaseController<Detergent, Long, DetergentInDto, DetergentOutDto, DetergentInsertDto, DetergentUpdateDto> {

    private final IDetergentService detergentService;

    @Autowired
    public DetergentController(IDetergentService detergentService) {
        this.detergentService = detergentService;
    }



    @Override
    public BaseService<Detergent, Long, DetergentInDto, DetergentOutDto, DetergentInsertDto, DetergentUpdateDto> getService() {
        return detergentService;
    }
}
