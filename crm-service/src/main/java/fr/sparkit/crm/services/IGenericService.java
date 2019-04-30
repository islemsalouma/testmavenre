package fr.sparkit.crm.services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface IGenericService<T, D extends Serializable> {

    List<T> findAll();

    List<T> findAll(Sort sort);

    List<T> findAll(Iterable<D> ids);

    <S extends T> List<S> save(Iterable<S> entities);

    void flush();

    <S extends T> S saveAndFlush(S entity);

    void deleteInBatch(Iterable<T> entities);

    void deleteAllInBatch();

    void delete(D id);

    void delete(T entity);

    Optional<T> findOne(D id);

    List<T> findAllAndDelete();

    List<T> findAllAndDelete(Sort sort);

    List<T> findAllAndDelete(Iterable<D> ids);

    void deleteSoft(D id);

    void deleteSoft(T entity);

    Page<T> findAllByPaginationAndIsDeletedFalse(Pageable pageable);

}
