package ir.sharifi.soroush.soroush_test_project.base.controller;

import ir.sharifi.soroush.soroush_test_project.base.dto.BaseInDto;
import ir.sharifi.soroush.soroush_test_project.base.dto.BaseInsertDto;
import ir.sharifi.soroush.soroush_test_project.base.dto.BaseOutDto;
import ir.sharifi.soroush.soroush_test_project.base.dto.BaseUpdateDto;
import ir.sharifi.soroush.soroush_test_project.base.model.BaseEntity;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

public abstract class BaseController<T extends BaseEntity<ID>, ID extends Serializable, V extends BaseInDto, Y extends BaseOutDto<ID>, Z extends BaseInsertDto, X extends BaseUpdateDto<ID>> {
    public abstract BaseService<T, ID, V, Y, Z, X> getService();


    @GetMapping("/{id}")
    public ResponseEntity loadById(@PathVariable("id") ID id) {


        Y byId = getService().findById(id);
        return ResponseEntity.ok(byId);
    }


    @PostMapping
    public ResponseEntity insert(@RequestBody Z insertDto) {

        Y map = getService().insert(insertDto);
        return ResponseEntity.ok(map);

    }


    @PutMapping
    public ResponseEntity update(@RequestBody X updateDto) {

        Y update = getService().update(updateDto);
        return ResponseEntity.ok(update);

    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable ID id) {
        getService().delete(id);
        return ResponseEntity.ok("object deleted successfully");
    }

    @GetMapping
    public ResponseEntity getAll() {

        List<Y> models = getService().getModels();
        return ResponseEntity.ok(models);

    }

}
