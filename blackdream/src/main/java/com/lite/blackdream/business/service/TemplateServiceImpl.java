package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.business.parameter.template.*;
import com.lite.blackdream.business.repository.GeneratorRepository;
import com.lite.blackdream.business.repository.TemplateRepository;
import com.lite.blackdream.business.repository.UserRepository;
import com.lite.blackdream.framework.exception.AppException;
import com.lite.blackdream.framework.component.BaseService;
import com.lite.blackdream.framework.model.Base64FileItem;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.util.ConfigProperties;
import com.lite.blackdream.framework.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LaineyC
 */
@Service
public class TemplateServiceImpl extends BaseService implements TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private GeneratorRepository generatorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Template create(TemplateCreateRequest request) {
        Long userId = request.getAuthentication().getUserId();

        Long generatorId = request.getGeneratorId();
        Generator generatorPersistence = generatorRepository.selectById(generatorId);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }

        Template template = new Template();
        template.setId(idWorker.nextId());
        template.setName(request.getName());
        template.setCreateDate(new Date());
        template.setModifyDate(new Date());
        template.setIsDelete(false);
        template.setSequence(Integer.MAX_VALUE);
        Generator generator = new Generator();
        generator.setId(generatorPersistence.getId());
        template.setGenerator(generator);
        User developer = new User();
        developer.setId(userId);
        template.setDeveloper(developer);
        Base64FileItem templateFile = request.getTemplateFile();
        String fileName = template.getId() + ".vm";
        String uploadPath = "/Template/" + generatorId + "/" + fileName;
        String fileAbsolutePath = ConfigProperties.FILEBASE_PATH + uploadPath.replace("/", ConfigProperties.fileSeparator);
        try {
            FileUtil.writeBase64(templateFile.getContent(), fileAbsolutePath);
        }
        catch (Exception e) {
            throw new AppException("上传失败");
        }
        template.setUrl(uploadPath);
        templateRepository.insert(template);

        generatorPersistence.setIsOpen(false);
        generatorPersistence.setModifyDate(new Date());
        generatorRepository.update(generatorPersistence);

        return template;
    }

    @Override
    public Template get(TemplateGetRequest request) {
        Long id = request.getId();
        Template templatePersistence = templateRepository.selectById(id);
        if(templatePersistence == null){
            throw new AppException("模板文件不存在");
        }

        Template template = new Template();
        template.setId(templatePersistence.getId());
        template.setName(templatePersistence.getName());
        template.setModifyDate(templatePersistence.getModifyDate());
        template.setIsDelete(templatePersistence.getIsDelete());
        Long generatorId = templatePersistence.getGenerator().getId();
        Generator generatorPersistence = generatorRepository.selectById(generatorId);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }

        User developerPersistence = userRepository.selectById(templatePersistence.getDeveloper().getId());
        template.setDeveloper(developerPersistence);

        template.setGenerator(generatorPersistence);
        template.setUrl(templatePersistence.getUrl());

        return template;
    }

    @Override
    public Template delete(TemplateDeleteRequest request) {
        Long id = request.getId();
        Template templatePersistence = templateRepository.selectById(id);
        if(templatePersistence == null){
            throw new AppException("模板文件不存在");
        }

        Long userId = request.getAuthentication().getUserId();
        if(!userId.equals(templatePersistence.getDeveloper().getId())){
            throw new AppException("权限不足");
        }

        Generator generatorPersistence = generatorRepository.selectById(templatePersistence.getGenerator().getId());
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }

        templateRepository.delete(templatePersistence);

        generatorPersistence.setIsOpen(false);
        generatorPersistence.setModifyDate(new Date());
        generatorRepository.update(generatorPersistence);

        return templatePersistence;
    }

    @Override
    public List<Template> query(TemplateQueryRequest request) {
        Template templateTemplate = new Template();
        templateTemplate.setIsDelete(false);
        Long generatorId = request.getGeneratorId();
        Generator generator = new Generator();
        generator.setId(generatorId);
        templateTemplate.setGenerator(generator);

        List<Template> records = templateRepository.selectList(templateTemplate);
        List<Template> result = new ArrayList<>();
        for(Template t : records){
            Template template = new Template();
            template.setId(t.getId());
            template.setName(t.getName());
            template.setCreateDate(t.getCreateDate());
            template.setModifyDate(t.getModifyDate());
            template.setIsDelete(t.getIsDelete());
            template.setSequence(t.getSequence());
            Generator generatorPersistence = generatorRepository.selectById(generatorId);
            if(generatorPersistence == null){
                throw new AppException("生成器不存在");
            }
            template.setGenerator(generatorPersistence);
            template.setUrl(t.getUrl());
            result.add(template);
        }
        result.sort((t1, t2) -> {
            int s1 = t1.getSequence();
            int s2 = t2.getSequence();
            return s1 == s2 ? (int)(t1.getId() - t2.getId()) : s1 - s2;
        });
        return result;
    }

    @Override
    public PagerResult<Template> search(TemplateSearchRequest request) {
        Long generatorId = request.getGeneratorId();
        String name = StringUtils.hasText(request.getName())  ? request.getName() : null;
        List<Template> records = templateRepository.filter(template -> {
            if (template.getIsDelete()) {
                return false;
            }
            if (name != null) {
                if (!template.getName().contains(name)) {
                    return false;
                }
            }
            if (generatorId != null) {
                if (!generatorId.equals(template.getGenerator().getId())) {
                    return false;
                }
            }
            return true;
        });
        Integer page = request.getPage();
        Integer pageSize = request.getPageSize();
        Integer fromIndex = (page - 1) * pageSize;
        Integer toIndex = fromIndex + pageSize > records.size() ? records.size() : fromIndex + pageSize;
        List<Template> limitRecords = records.subList(fromIndex, toIndex);
        List<Template> result = new ArrayList<>();
        for(Template t : limitRecords){
            Template template = new Template();
            template.setId(t.getId());
            template.setName(t.getName());
            template.setCreateDate(t.getCreateDate());
            template.setModifyDate(t.getModifyDate());
            template.setIsDelete(t.getIsDelete());
            User userPersistence = userRepository.selectById(t.getDeveloper().getId());
            template.setDeveloper(userPersistence);
            Generator generatorPersistence = generatorRepository.selectById(t.getGenerator().getId());
            if(generatorPersistence == null){
                throw new AppException("生成器不存在");
            }
            template.setGenerator(generatorPersistence);
            template.setUrl(t.getUrl());
            result.add(template);
        }
        return new PagerResult<>(result, (long)records.size());
    }

    @Override
    public Template update(TemplateUpdateRequest request) {
        Long id = request.getId();
        Template templatePersistence = templateRepository.selectById(id);
        if(templatePersistence == null){
            throw new AppException("模板文件不存在");
        }

        Long userId = request.getAuthentication().getUserId();
        if(!userId.equals(templatePersistence.getDeveloper().getId())){
            throw new AppException("权限不足");
        }

        Generator generatorPersistence = generatorRepository.selectById(templatePersistence.getGenerator().getId());
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }

        templatePersistence.setName(request.getName());

        Base64FileItem templateFile = request.getTemplateFile();
        if(templateFile != null){
            String uploadPath = templatePersistence.getUrl();
            String fileAbsolutePath = ConfigProperties.FILEBASE_PATH + uploadPath.replace("/", ConfigProperties.fileSeparator);
            try {
                FileUtil.writeBase64(templateFile.getContent(), fileAbsolutePath);
            }
            catch (Exception e) {
                throw new AppException("上传失败");
            }
        }

        templatePersistence.setModifyDate(new Date());
        templateRepository.update(templatePersistence);

        generatorPersistence.setIsOpen(false);
        generatorPersistence.setModifyDate(new Date());
        generatorRepository.update(generatorPersistence);

        return templatePersistence;
    }

    @Override
    public String codeGet(TemplateCodeGetRequest request) {
        Long id = request.getId();
        Template templatePersistence = templateRepository.selectById(id);
        if(templatePersistence == null){
            throw new AppException("模板文件不存在");
        }

        String uploadPath = templatePersistence.getUrl();
        String fileAbsolutePath = ConfigProperties.FILEBASE_PATH + uploadPath.replace("/", ConfigProperties.fileSeparator);
        File file = new File(fileAbsolutePath);
        byte buffer[] = new byte[(int) file.length()];
        try {
            FileInputStream fileInput = new FileInputStream(file);
            fileInput.read(buffer);
            return new String(buffer, "UTF-8");
        }
        catch (Exception e){
            throw new AppException("源文件读取失败");
        }
    }

    @Override
    public Template sort(TemplateSortRequest request) {
        Long id = request.getId();
        Template templatePersistence = templateRepository.selectById(id);
        if(templatePersistence == null){
            throw new AppException("模板文件不存在");
        }

        Long userId = request.getAuthentication().getUserId();
        if(!userId.equals(templatePersistence.getDeveloper().getId())){
            throw new AppException("权限不足");
        }

        Generator generatorPersistence = generatorRepository.selectById(templatePersistence.getGenerator().getId());
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }

        Template templateTemplate = new Template();
        templateTemplate.setIsDelete(false);
        templateTemplate.setGenerator(templatePersistence.getGenerator());
        List<Template> records = templateRepository.selectList(templateTemplate);

        int fromIndex = request.getFromIndex();
        int toIndex = request.getToIndex();
        int size = records.size();
        if(size == 0 || toIndex > size - 1 || fromIndex > size - 1){
            throw new AppException("请保存并刷新数据，重新操作！");
        }
        records.sort((t1, t2) -> {
            int s1 = t1.getSequence();
            int s2 = t2.getSequence();
            return s1 == s2 ? (int)(t1.getId() - t2.getId()) : s1 - s2;
        });
        if(templatePersistence != records.remove(fromIndex)){
            throw new AppException("请保存并刷新数据，重新操作！");
        }
        records.add(toIndex, templatePersistence);

        int sequence = 1;
        for(Template t : records){
            if(sequence != t.getSequence()){
                t.setSequence(sequence);
                t.setModifyDate(new Date());
                templateRepository.update(t);
            }
            sequence++;
        }

        generatorPersistence.setModifyDate(new Date());
        generatorRepository.update(generatorPersistence);

        return templatePersistence;
    }

}
