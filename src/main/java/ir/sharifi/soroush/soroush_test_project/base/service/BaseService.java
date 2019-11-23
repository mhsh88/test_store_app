package ir.sharifi.soroush.soroush_test_project.base.service;

import ir.sharifi.soroush.soroush_test_project.base.dto.BaseInDto;
import ir.sharifi.soroush.soroush_test_project.base.dto.BaseInsertDto;
import ir.sharifi.soroush.soroush_test_project.base.dto.BaseOutDto;
import ir.sharifi.soroush.soroush_test_project.base.dto.BaseUpdateDto;
import ir.sharifi.soroush.soroush_test_project.base.model.BaseEntity;
import ir.sharifi.soroush.soroush_test_project.base.repository.BaseRepository;
import ir.sharifi.soroush.soroush_test_project.user.dto.LoginRequest;
import ir.sharifi.soroush.soroush_test_project.user.dto.UserLoginResponse;
import ir.sharifi.soroush.soroush_test_project.user.dto.UserRegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends BaseEntity<I>, I extends Serializable, V extends BaseInDto, Y extends BaseOutDto<I>, Z extends BaseInsertDto, X extends BaseUpdateDto<I>> {
    BaseRepository<T, I> getRepository();
    Y findById(I id);
    Y getOne(I id);
    @Transactional
    Y insert(Z model);
    @Transactional
    Y update(X model);
    @Transactional
    void delete(I id);
    List<Y> getModels();


}