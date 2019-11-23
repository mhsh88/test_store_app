package ir.sharifi.soroush.soroush_test_project.base.service;

import ir.sharifi.soroush.soroush_test_project.base.dto.BaseInDto;
import ir.sharifi.soroush.soroush_test_project.base.dto.BaseInsertDto;
import ir.sharifi.soroush.soroush_test_project.base.dto.BaseOutDto;
import ir.sharifi.soroush.soroush_test_project.base.dto.BaseUpdateDto;
import ir.sharifi.soroush.soroush_test_project.base.model.BaseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseServiceImpl<T extends BaseEntity<I>, I extends Serializable, V extends BaseInDto, Y extends BaseOutDto<I>, Z extends BaseInsertDto, X extends BaseUpdateDto<I>>
        implements BaseService<T, I,V,Y,Z,X> {

    @Autowired
    public ModelMapper modelMapper;

    @Override
    public Y findById(I id) {

        Optional<T> optionalModel = getRepository().findById(id);
        if(!optionalModel.isPresent()){
            throw new EntityNotFoundException();
        }
        return modelMapper.map(Objects.requireNonNull(optionalModel.orElse(null)), getOutDtoClass());
    }

    @Override
    public Y getOne(I id) {
        T one = getRepository().getOne(id);

        return modelMapper.map(one,getOutDtoClass());
    }

    @Override
    public Y insert(Z insertDto) {
        T model = modelMapper.map(insertDto, getModelClass());

        return saveOrUpdate(model);
    }

    @Override
    public Y update(X updateDto) {
        T model = modelMapper.map(updateDto, getModelClass());

        return saveOrUpdate(model);
    }

    private Y saveOrUpdate(T model) {
        T save = getRepository().save(model);

        return modelMapper.map(save, getOutDtoClass());
    }

    @Override
    public void delete(I id) {
        getRepository().delete(getRepository().getOne(id));
    }

    @Override
    public List<Y> getModels() {
        List<T> all = getRepository().findAll();
        List<Y> entityToDto = all.stream().map(e->modelMapper.map(e,getOutDtoClass())).collect(Collectors.toList());
        return entityToDto;
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
