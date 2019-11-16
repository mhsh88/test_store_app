package ir.sharifi.soroush.soroush_test_project.base.service;

import ir.sharifi.soroush.soroush_test_project.base.model.BaseEntity;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T extends BaseEntity<I>, I extends Serializable> implements BaseService<T, I> {

    @Override
    public T findById(I id) {
        Optional<T> optionalModel = getRepository().findById(id);
        if(!optionalModel.isPresent()){
            throw new EntityNotFoundException();
        }
        return optionalModel.orElse(null);
    }

    @Override
    public T getOne(I id) {
        return getRepository().getOne(id);
    }

    @Override
    public T insert(T model) {
        return getRepository().save(model);
    }

    @Override
    public T update(T model) {
        return getRepository().save(model);
    }

    @Override
    public void delete(T model) {
        getRepository().delete(model);
    }

    @Override
    public List<T> getModels() {
        return getRepository().findAll();
    }
}
