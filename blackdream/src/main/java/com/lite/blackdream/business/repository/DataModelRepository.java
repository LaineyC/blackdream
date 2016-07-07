package com.lite.blackdream.business.repository;

import com.lite.blackdream.business.domain.DataModel;
import com.lite.blackdream.framework.component.Repository;

/**
 * @author LaineyC
 */
public interface DataModelRepository extends Repository<DataModel, Long> {

    void insert(DataModel entity, DataModel root);

    void delete(DataModel entity, DataModel root);

    DataModel selectById(Long id, DataModel root);

}
