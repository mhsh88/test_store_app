package ir.sharifi.soroush.soroush_test_project.base.controller;

import ir.sharifi.soroush.soroush_test_project.base.dto.BaseInDto;
import ir.sharifi.soroush.soroush_test_project.base.dto.BaseInsertDto;
import ir.sharifi.soroush.soroush_test_project.base.dto.BaseOutDto;
import ir.sharifi.soroush.soroush_test_project.base.dto.BaseUpdateDto;
import ir.sharifi.soroush.soroush_test_project.base.model.BaseEntity;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;

public abstract class BaseController<T extends BaseEntity<ID>, ID extends Serializable, V extends BaseInDto, Y extends BaseOutDto<ID>, Z extends BaseInsertDto, X extends BaseUpdateDto<ID>> {
    public abstract BaseService<T, ID> getService();


    @Autowired
    public ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity load(@PathVariable("id") ID id) {


        T byId = getService().findById(id);
        Y map = modelMapper.map(Objects.requireNonNull(byId), getOutDtoClass());
        return ResponseEntity.ok(map);
    }

    @Transactional
    @PostMapping
    public ResponseEntity insert(@RequestBody Z insertDto) {

        T model = modelMapper.map(insertDto, getModelClass());
        T save = getService().insert(model);

        Y map = modelMapper.map(save, getOutDtoClass());
        return ResponseEntity.ok(map);

    }

    @Transactional
    @PutMapping
    public ResponseEntity update(@RequestBody X updateDto)  {
        T model = modelMapper.map(updateDto, getModelClass());
        T save = getService().update(model);

        Y map = modelMapper.map(save, getOutDtoClass());
        return ResponseEntity.ok(map);

    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable ID id)  {
        getService().delete(getService().findById(id));
        return ResponseEntity.ok("object deleted successfully");
    }

    @GetMapping
    public ResponseEntity getAll()  {

        List<T> all = getService().getModels();
        List<Y> entityToDto = modelMapper.map(all, new TypeToken<List>(){}.getType());
        return ResponseEntity.ok(entityToDto);

    }

    protected Class<?> getGenericClass(int genericNumber) {
        Class<?> clazz = getClass();
        while (clazz.getGenericSuperclass() != null) {
            if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
                return (Class<?>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[genericNumber];
            } else {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    protected Class<T> getModelClass(){
        return (Class<T>) getGenericClass(0);
    }
    protected Class<V> getDtoInClass(){
        return (Class<V>) getGenericClass(2);
    }
    protected Class<Y> getOutDtoClass(){
        return (Class<Y>) getGenericClass(3);
    }
    protected Class<Z> getInsertDtoClass(){
        return (Class<Z>) getGenericClass(4);
    }
    protected Class<X> getUpdateDtoClass(){
        return (Class<X>) getGenericClass(5);
    }

}
