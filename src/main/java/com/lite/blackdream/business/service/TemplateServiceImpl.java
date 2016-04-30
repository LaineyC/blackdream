package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.business.parameter.template.*;
import com.lite.blackdream.business.repository.GeneratorRepository;
import com.lite.blackdream.business.repository.TemplateRepository;
import com.lite.blackdream.framework.exception.AppException;
import com.lite.blackdream.framework.component.BaseService;
import com.lite.blackdream.framework.model.Authentication;
import com.lite.blackdream.framework.model.Base64FileItem;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.util.ConfigProperties;
import com.lite.blackdream.framework.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
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

    @Override
    public Template create(TemplateCreateRequest request) {
        Template template = new Template();
        template.setId(idWorker.nextId());
        template.setName(request.getName());
        template.setIsDelete(false);
        Long generatorId = request.getGeneratorId();
        Generator generatorPersistence = generatorRepository.selectById(generatorId);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }
        template.setGenerator(generatorPersistence);

        Base64FileItem templateFile = request.getTemplateFile();
        String fileName = idWorker.nextId() + ".vm";
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
        return template;
    }

    @Override
    public Template get(TemplateGetRequest request) {
        Long id = request.getId();
        Template templatePersistence = templateRepository.selectById(id);
        if(templatePersistence == null){
            throw new AppException("模板不存在");
        }
        Template template = new Template();
        template.setId(templatePersistence.getId());
        template.setName(templatePersistence.getName());
        template.setIsDelete(templatePersistence.getIsDelete());
        Long generatorId = templatePersistence.getGenerator().getId();
        Generator generatorPersistence = generatorRepository.selectById(generatorId);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }
        template.setGenerator(generatorPersistence);
        template.setUrl(templatePersistence.getUrl());
        return template;
    }

    @Override
    public Template delete(TemplateDeleteRequest request) {
        Long id = request.getId();
        Template templatePersistence = templateRepository.selectById(id);
        if(templatePersistence == null){
            throw new AppException("模板不存在");
        }

        Authentication authentication = request.getAuthentication();
        Long userId = authentication.getUserId();
        Generator generatorPersistence = generatorRepository.selectById(templatePersistence.getGenerator().getId());
        if(!userId.equals(generatorPersistence.getDeveloper().getId())){
            throw new AppException("权限不足");
        }

        templateRepository.delete(templatePersistence);
        String fileAbsolutePath = ConfigProperties.FILEBASE_PATH + templatePersistence.getUrl().replace("/", ConfigProperties.fileSeparator);
        File templateFile = new File(fileAbsolutePath);
        if(!templateFile.delete()){
            throw new AppException("文件未删除");
        }
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
            template.setIsDelete(t.getIsDelete());
            Generator generatorPersistence = generatorRepository.selectById(generatorId);
            if(generatorPersistence == null){
                throw new AppException("生成器不存在");
            }
            template.setGenerator(generatorPersistence);
            template.setUrl(t.getUrl());
            result.add(template);
        }
        return result;
    }

    @Override
    public PagerResult<Template> search(TemplateSearchRequest request) {
        Template templateTemplate = new Template();
        templateTemplate.setIsDelete(false);
        Long generatorId = request.getGeneratorId();
        Generator generator = new Generator();
        generator.setId(generatorId);
        templateTemplate.setGenerator(generator);

        List<Template> records = templateRepository.selectList(templateTemplate);
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
            template.setIsDelete(t.getIsDelete());
            Generator generatorPersistence = generatorRepository.selectById(generatorId);
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
            throw new AppException("模板不存在");
        }

        Authentication authentication = request.getAuthentication();
        Long userId = authentication.getUserId();
        Generator generatorPersistence = generatorRepository.selectById(templatePersistence.getGenerator().getId());
        if(!userId.equals(generatorPersistence.getDeveloper().getId())){
            throw new AppException("权限不足");
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

        templateRepository.update(templatePersistence);
        return templatePersistence;
    }

    @Override
    public String codeGet(TemplateCodeGetRequest request) {
        Long id = request.getId();
        Template templatePersistence = templateRepository.selectById(id);
        if(templatePersistence == null){
            throw new AppException("模板不存在");
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
}
