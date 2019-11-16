package ir.sharifi.soroush.soroush_test_project.base.repository;

import ir.sharifi.soroush.soroush_test_project.base.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<I>, I extends Serializable> extends JpaRepository<T, I> {
}
