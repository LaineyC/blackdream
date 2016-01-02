package com.lite.blackdream.framework.layer;

import com.lite.blackdream.framework.model.Domain;
import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author LaineyC
 */
public interface Repository<E extends Domain, ID extends Serializable> extends Runnable{

    void insert(E entity);

    void delete(E entity);

    void update(E entity);

    E selectById(ID id);

    E selectOne(E entity);

    List<E> selectList(E entity);

    List<E> filter(Predicate<E> predicate);

    long count(E entity);

    boolean exists(E entity, ID id);

    void init();

    void read();

    void remove(E entity);

    void write(E entity);

}
