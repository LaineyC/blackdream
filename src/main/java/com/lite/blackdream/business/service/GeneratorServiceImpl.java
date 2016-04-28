package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.business.parameter.generator.*;
import com.lite.blackdream.business.repository.GeneratorRepository;
import com.lite.blackdream.business.repository.UserRepository;
import com.lite.blackdream.framework.exception.AppException;
import com.lite.blackdream.framework.component.BaseService;
import com.lite.blackdream.framework.model.Authentication;
import com.lite.blackdream.framework.model.PagerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LaineyC
 */
@Service
public class GeneratorServiceImpl extends BaseService implements GeneratorService{

    @Autowired
    private GeneratorRepository generatorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Generator create(GeneratorCreateRequest request) {
        Authentication authentication = request.getAuthentication();
        Long userId = authentication.getUserId();
        Generator generator = new Generator();
        generator.setId(idWorker.nextId());
        generator.setName(request.getName());
        generator.setIsDelete(false);
        generator.setIsOpen(request.getIsOpen());
        generator.setDescription(request.getDescription());
        User developer = new User();
        developer.setId(userId);
        generator.setDeveloper(developer);
        generatorRepository.insert(generator);
        return generator;
    }

    @Override
    public Generator delete(GeneratorDeleteRequest request) {
        throw new AppException("未开放");
    }

    @Override
    public Generator get(GeneratorGetRequest request) {
        Long id = request.getId();
        Generator generatorPersistence = generatorRepository.selectById(id);
        if(generatorPersistence == null){
            throw new AppException("项目不存在");
        }

        Generator generator = new Generator();
        generator.setId(generatorPersistence.getId());
        generator.setName(generatorPersistence.getName());
        generator.setIsDelete(generatorPersistence.getIsDelete());
        generator.setIsOpen(generatorPersistence.getIsOpen());
        generator.setDescription(generatorPersistence.getDescription());
        User developerPersistence = userRepository.selectById(generatorPersistence.getDeveloper().getId());
        generator.setDeveloper(developerPersistence);
        return generator;
    }

    @Override
    public PagerResult<Generator> search(GeneratorSearchRequest request) {
        String keyword = StringUtils.hasText(request.getKeyword())  ? request.getKeyword() : null;
        String name =  StringUtils.hasText(request.getName())  ? request.getName() : null;
        Boolean isOpen = request.getIsOpen();
        Long developerId = request.getDeveloperId();
        List<Generator> records = generatorRepository.filter(generator -> {
            if(generator.getIsDelete()){
                return false;
            }
            if (isOpen != null) {
                if (!isOpen.equals(generator.getIsOpen())) {
                    return false;
                }
            }
            if (keyword != null) {
                User developerPersistence = userRepository.selectById(generator.getDeveloper().getId());
                if (!generator.getName().contains(keyword) && !developerPersistence.getUserName().contains(keyword)) {
                    return false;
                }
            }
            if (name != null) {
                if (!generator.getName().contains(name)) {
                    return false;
                }
            }
            if (developerId != null) {
                if (!developerId.equals(generator.getDeveloper().getId())) {
                    return false;
                }
            }
            return true;
        });
        Integer page = request.getPage();
        Integer pageSize = request.getPageSize();
        Integer fromIndex = (page - 1) * pageSize;
        Integer toIndex = fromIndex + pageSize > records.size() ? records.size() : fromIndex + pageSize;
        List<Generator> limitRecords = records.subList(fromIndex, toIndex);
        List<Generator> result = new ArrayList<>();
        for(Generator g : limitRecords){
            Generator generator = new Generator();
            generator.setId(g.getId());
            generator.setName(g.getName());
            generator.setIsDelete(g.getIsDelete());
            generator.setIsOpen(g.getIsOpen());
            generator.setDescription(g.getDescription());
            User developerPersistence = userRepository.selectById(g.getDeveloper().getId());
            generator.setDeveloper(developerPersistence);
            result.add(generator);
        }
        return new PagerResult<>(result, (long)records.size());
    }

    @Override
    public Generator update(GeneratorUpdateRequest request) {
        Authentication authentication = request.getAuthentication();
        Long userId = authentication.getUserId();
        Long id = request.getId();
        Generator generatorPersistence = generatorRepository.selectById(id);
        if(generatorPersistence == null){
            throw new AppException("项目不存在");
        }
        if(!userId.equals(generatorPersistence.getDeveloper().getId())){
            throw new AppException("权限不足");
        }
        generatorPersistence.setName(request.getName());
        generatorPersistence.setIsOpen(request.getIsOpen());
        generatorPersistence.setDescription(request.getDescription());
        generatorRepository.update(generatorPersistence);
        return generatorPersistence;
    }

}
