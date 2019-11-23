package ir.sharifi.soroush.soroush_test_project.detergent.service;

import ir.sharifi.soroush.soroush_test_project.base.service.BaseService;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentInDto;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentInsertDto;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentOutDto;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentUpdateDto;
import ir.sharifi.soroush.soroush_test_project.detergent.model.Detergent;

public interface IDetergentService extends BaseService<Detergent, Long, DetergentInDto, DetergentOutDto, DetergentInsertDto, DetergentUpdateDto> {
}
