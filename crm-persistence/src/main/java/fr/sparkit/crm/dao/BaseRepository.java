package fr.sparkit.crm.dao;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.isDeleted = false")
    List<T> findAll();

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.isDeleted = false")
    List<T> findAll(Sort sort);

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.id in ?1 and e.isDeleted = false")
    List<T> findAll(Iterable<ID> ids);

    @Query("select e from #{#entityName} e where e.isDeleted = true")
    @Transactional(readOnly = true)
    List<T> findIsDeleted();

    @Override
    @Transactional(readOnly = true)
    @Query("select count(e) from #{#entityName} e where e.isDeleted = false")
    long count();

    @Query("update #{#entityName} e set e.isDeleted=true, e.deletedToken = ?2 where e.id = ?1 ")
    @Transactional
    @Modifying
    void delete(ID id, UUID uuid);

    @Query("update #{#entityName} e set e.isDeleted=true, e.deletedToken = ?2 where e = ?1")
    @Transactional
    @Modifying
    void delete(T entity, UUID uuid);

    @Override
    @Transactional
    default void delete(Iterable<? extends T> entities) {
        entities.forEach(this::delete);
    }

    @Query("update #{#entityName} e set e.isDeleted=true, e.deletedToken = ?1")
    @Transactional
    @Modifying
    void deleteAll(UUID uuid);

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.id = ?1 and e.isDeleted = false")
    T findOne(ID id);

    Page<T> findAllByIsDeletedFalse(Pageable pageable);

}
