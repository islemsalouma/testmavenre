package fr.sparkit.crm.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.sparkit.crm.dao.BaseRepository;
import fr.sparkit.crm.services.IGenericService;

public class GenericService<T, D extends Serializable> implements IGenericService<T, D> {

    @Autowired
    private BaseRepository<T, D> baseRepository;

    @Autowired
    private JpaRepository<T, D> jpaRepository;

    public GenericService() {
        super();
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    @Override
    public List<T> findAll(Iterable<D> ids) {
        return baseRepository.findAll(ids);
    }

    @Override
    public <S extends T> List<S> save(Iterable<S> entities) {
        return baseRepository.save(entities);
    }

    @Override
    public void flush() {
        baseRepository.flush();

    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return baseRepository.saveAndFlush(entity);
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {
        baseRepository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch() {
        baseRepository.deleteAllInBatch();

    }

    @Override
    public Optional<T> findOne(D id) {
        return Optional.ofNullable(baseRepository.findOne(id));
    }

    @Override
    public void delete(D id) {
        UUID uuid = UUID.randomUUID();
        baseRepository.delete(id, uuid);
    }

    @Override
    public void delete(T entity) {
        UUID uuid = UUID.randomUUID();
        baseRepository.delete(entity, uuid);
    }

    @Override
    public void deleteSoft(D id) {
        jpaRepository.delete(id);
    }

    @Override
    public void deleteSoft(T entity) {
        jpaRepository.delete(entity);
    }

    @Override
    public Page<T> findAllByPaginationAndIsDeletedFalse(Pageable pageable) {
        return baseRepository.findAllByIsDeletedFalse(pageable);
    }

    @Override
    public List<T> findAllAndDelete() {
        return jpaRepository.findAll();
    }

    @Override
    public List<T> findAllAndDelete(Sort sort) {
        return jpaRepository.findAll(sort);
    }

    @Override
    public List<T> findAllAndDelete(Iterable<D> ids) {
        return jpaRepository.findAll(ids);
    }

}
