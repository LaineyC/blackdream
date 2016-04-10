package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.business.parameter.dynamicmodel.*;
import com.lite.blackdream.business.repository.DynamicModelRepository;
import com.lite.blackdream.business.repository.GeneratorRepository;
import com.lite.blackdream.framework.exception.AppException;
import com.lite.blackdream.framework.layer.BaseService;
import com.lite.blackdream.framework.model.PagerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LaineyC
 */
@Service
public class DynamicModelServiceImpl extends BaseService implements DynamicModelService {

    @Autowired
    private DynamicModelRepository dynamicModelRepository;

    @Autowired
    private GeneratorRepository generatorRepository;

    @Override
    public DynamicModel create(DynamicModelCreateRequest request) {
        DynamicModel dynamicModel = new DynamicModel();
        dynamicModel.setId(idWorker.nextId());
        dynamicModel.setName(request.getName());
        dynamicModel.setIcon(request.getIcon());
        dynamicModel.setIsDelete(false);

        Long generatorId = request.getGeneratorId();
        Generator generatorPersistence = generatorRepository.selectById(generatorId);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }
        dynamicModel.setGenerator(generatorPersistence);

        dynamicModel.setProperties(request.getProperties());
        dynamicModel.setAssociation(request.getAssociation());
        dynamicModel.setPredefinedAssociation(request.getPredefinedAssociation());

        for(Long childId : request.getChildren()){
            DynamicModel child = new DynamicModel();
            child.setId(childId);
            dynamicModel.getChildren().add(child);
        }

        dynamicModelRepository.insert(dynamicModel);
        return dynamicModel;
    }

    @Override
    public DynamicModel delete(DynamicModelDeleteRequest request) {
        return null;
    }

    @Override
    public DynamicModel get(DynamicModelGetRequest request) {
        Long id = request.getId();
        DynamicModel dynamicModelPersistence = dynamicModelRepository.selectById(id);
        if(dynamicModelPersistence == null){
            throw new AppException("模型不存在");
        }
        DynamicModel dynamicModel = new DynamicModel();
        dynamicModel.setId(dynamicModelPersistence.getId());
        dynamicModel.setName(dynamicModelPersistence.getName());
        dynamicModel.setIcon(dynamicModelPersistence.getIcon());
        dynamicModel.setProperties(dynamicModelPersistence.getProperties());
        dynamicModel.setAssociation(dynamicModelPersistence.getAssociation());
        dynamicModel.setPredefinedAssociation(dynamicModelPersistence.getPredefinedAssociation());

        Long generatorId = dynamicModelPersistence.getGenerator().getId();
        Generator generatorPersistence = generatorRepository.selectById(generatorId);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }
        dynamicModel.setGenerator(generatorPersistence);

        dynamicModelPersistence.getChildren().forEach(child -> {
            DynamicModel c = dynamicModelRepository.selectById(child.getId());
            if(c != null){
                dynamicModel.getChildren().add(c);
            }
        });
        return dynamicModel;
    }

    @Override
    public PagerResult<DynamicModel> search(DynamicModelSearchRequest request) {
        DynamicModel dynamicModelTemplate = new DynamicModel();
        dynamicModelTemplate.setIsDelete(false);
        Long generatorId = request.getGeneratorId();
        Generator generator = new Generator();
        generator.setId(generatorId);
        dynamicModelTemplate.setGenerator(generator);

        List<DynamicModel> records = dynamicModelRepository.selectList(dynamicModelTemplate);
        Integer page = request.getPage();
        Integer pageSize = request.getPageSize();
        Integer fromIndex = (page - 1) * pageSize;
        Integer toIndex = fromIndex + pageSize > records.size() ? records.size() : fromIndex + pageSize;
        List<DynamicModel> limitRecords = records.subList(fromIndex, toIndex);
        List<DynamicModel> result = new ArrayList<>();
        for(DynamicModel d : limitRecords){
            DynamicModel dynamicModel = new DynamicModel();
            dynamicModel.setId(d.getId());
            dynamicModel.setName(d.getName());
            dynamicModel.setIcon(d.getIcon());
            //dynamicModel.setProperties(d.getProperties());
            //dynamicModel.setAssociation(d.getAssociation());

            Generator generatorPersistence = generatorRepository.selectById(generatorId);
            if(generatorPersistence == null){
                throw new AppException("生成器不存在");
            }
            dynamicModel.setGenerator(generatorPersistence);

            d.getChildren().forEach(child -> {
                DynamicModel c = dynamicModelRepository.selectById(child.getId());
                if (c != null) {
                    dynamicModel.getChildren().add(c);
                }
            });
            result.add(dynamicModel);
        }
        return new PagerResult<>(result, (long)records.size());
    }

    @Override
    public List<DynamicModel> query(DynamicModelQueryRequest request) {
        DynamicModel dynamicModelTemplate = new DynamicModel();
        Long generatorId = request.getGeneratorId();
        Generator generator = new Generator();
        generator.setId(generatorId);
        dynamicModelTemplate.setGenerator(generator);

        List<DynamicModel> records = dynamicModelRepository.selectList(dynamicModelTemplate);
        List<DynamicModel> result = new ArrayList<>();
        for(DynamicModel d : records){
            DynamicModel dynamicModel = new DynamicModel();
            dynamicModel.setId(d.getId());
            dynamicModel.setName(d.getName());
            dynamicModel.setIcon(d.getIcon());
            dynamicModel.setProperties(d.getProperties());
            dynamicModel.setAssociation(d.getAssociation());
            dynamicModel.setPredefinedAssociation(d.getPredefinedAssociation());

            Generator generatorPersistence = generatorRepository.selectById(generatorId);
            if(generatorPersistence == null){
                throw new AppException("生成器不存在");
            }
            dynamicModel.setGenerator(generatorPersistence);

            d.getChildren().forEach(child -> {
                DynamicModel c = dynamicModelRepository.selectById(child.getId());
                if (c != null) {
                    DynamicModel dynamicModelChild = new DynamicModel();
                    dynamicModelChild.setId(c.getId());
                    dynamicModelChild.setName(c.getName());
                    dynamicModelChild.setIcon(c.getIcon());
                    dynamicModel.getChildren().add(dynamicModelChild);
                }
            });
            result.add(dynamicModel);
        }
        return result;
    }

    @Override
    public DynamicModel update(DynamicModelUpdateRequest request) {
        Long id = request.getId();
        DynamicModel dynamicModelPersistence = dynamicModelRepository.selectById(id);
        if(dynamicModelPersistence == null) {
            throw new AppException("模型不存在");
        }

        dynamicModelPersistence.setName(request.getName());
        dynamicModelPersistence.setIcon(request.getIcon());
        dynamicModelPersistence.getChildren().clear();
        for(Long childId : request.getChildren()){
            DynamicModel child = new DynamicModel();
            child.setId(childId);
            dynamicModelPersistence.getChildren().add(child);
        }

        dynamicModelPersistence.setProperties(request.getProperties());
        dynamicModelPersistence.setAssociation(request.getAssociation());
        dynamicModelPersistence.setPredefinedAssociation(request.getPredefinedAssociation());
        dynamicModelRepository.update(dynamicModelPersistence);
        return dynamicModelPersistence;
    }

}
