package ir.sharifi.soroush.soroush_test_project.base.service;

import ir.sharifi.soroush.soroush_test_project.base.model.BaseEntity;
import ir.sharifi.soroush.soroush_test_project.base.repository.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends BaseEntity<I>, I extends Serializable> {
    BaseRepository<T, I> getRepository();
    T findById(I id);
    T getOne(I id);
    T insert(T model);
    @Transactional
    T update(T model);
    void delete(T model);
    List<T> getModels();
}