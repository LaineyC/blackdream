package com.lite.blackdream.business.service;

import com.lite.blackdream.business.converter.DynamicModelElementConverter;
import com.lite.blackdream.business.converter.GeneratorElementConverter;
import com.lite.blackdream.business.converter.TemplateElementConverter;
import com.lite.blackdream.business.converter.TemplateStrategyElementConverter;
import com.lite.blackdream.business.domain.*;
import com.lite.blackdream.business.domain.tag.Tag;
import com.lite.blackdream.business.parameter.generator.*;
import com.lite.blackdream.business.repository.*;
import com.lite.blackdream.framework.exception.AppException;
import com.lite.blackdream.framework.component.BaseService;
import com.lite.blackdream.framework.model.Authentication;
import com.lite.blackdream.framework.model.Base64FileItem;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.util.ConfigProperties;
import com.lite.blackdream.framework.util.FileUtil;
import com.lite.blackdream.framework.util.ZipUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.io.File;
import java.util.*;

/**
 * @author LaineyC
 */
@Service
public class GeneratorServiceImpl extends BaseService implements GeneratorService{

    @Autowired
    private GeneratorRepository generatorRepository;

    @Autowired
    private DynamicModelRepository dynamicModelRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateStrategyRepository templateStrategyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GeneratorElementConverter generatorElementConverter;

    @Autowired
    private DynamicModelElementConverter dynamicModelElementConverter;

    @Autowired
    private TemplateElementConverter templateElementConverter;

    @Autowired
    private TemplateStrategyElementConverter templateStrategyElementConverter;

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
            throw new AppException("生成器不存在");
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
        String keyword = StringUtils.hasText(request.getKeyword()) ? request.getKeyword() : null;
        String name = StringUtils.hasText(request.getName()) ? request.getName() : null;
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
            throw new AppException("生成器不存在");
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

    @Override
    public Generator export(GeneratorExportRequest request) {
        Long userId = request.getAuthentication().getUserId();
        Long id = request.getId();
        Generator generatorPersistence = generatorRepository.selectById(id);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }

        String exportPath = ConfigProperties.TEMPORARY_PATH + ConfigProperties.fileSeparator + userId + ConfigProperties.fileSeparator + generatorPersistence.getName() + "(" + id + ")";

        String generatorExportPath = exportPath + ConfigProperties.fileSeparator + "Database" + ConfigProperties.fileSeparator + "Generator";
        FileUtil.mkdirs(generatorExportPath);
        String dynamicModelExportPath = exportPath + ConfigProperties.fileSeparator + "Database" + ConfigProperties.fileSeparator + "DynamicModel";
        FileUtil.mkdirs(dynamicModelExportPath);
        String templateExportPath = exportPath + ConfigProperties.fileSeparator + "Database" + ConfigProperties.fileSeparator + "Template";
        FileUtil.mkdirs(templateExportPath);
        String templateStrategyExportPath = exportPath + ConfigProperties.fileSeparator + "Database" + ConfigProperties.fileSeparator + "TemplateStrategy";
        FileUtil.mkdirs(templateStrategyExportPath);
        String templateFileExportPath = exportPath + ConfigProperties.fileSeparator + "Filebase" + ConfigProperties.fileSeparator + "Template" + ConfigProperties.fileSeparator + id;
        FileUtil.mkdirs(templateFileExportPath);

        Generator generator = new Generator();
        generator.setId(id);
        generator.setName(generatorPersistence.getName());
        String generatorPath = ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + "Generator";
        FileUtil.copyFile(
            new File(generatorPath + ConfigProperties.fileSeparator + id + ".xml"),
            new File(generatorExportPath + ConfigProperties.fileSeparator + id + ".xml")
        );

        DynamicModel dynamicModelTemplate = new DynamicModel();
        dynamicModelTemplate.setIsDelete(false);
        dynamicModelTemplate.setGenerator(generator);
        List<DynamicModel> dynamicModels = dynamicModelRepository.selectList(dynamicModelTemplate);
        String dynamicModelPath = ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + "DynamicModel";
        dynamicModels.forEach(dynamicModel ->
            FileUtil.copyFile(
                new File(dynamicModelPath + ConfigProperties.fileSeparator + dynamicModel.getId() + ".xml"),
                new File(dynamicModelExportPath + ConfigProperties.fileSeparator + dynamicModel.getId() + ".xml")
            )
        );

        Template templateTemplate = new Template();
        templateTemplate.setIsDelete(false);
        templateTemplate.setGenerator(generator);
        List<Template> templates = templateRepository.selectList(templateTemplate);
        String templatePath = ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + "Template";
        templates.forEach(template -> {
            FileUtil.copyFile(
                new File(templatePath + ConfigProperties.fileSeparator + template.getId() + ".xml"),
                new File(templateExportPath + ConfigProperties.fileSeparator + template.getId() + ".xml")
            );
            FileUtil.copyFile(
                new File(ConfigProperties.FILEBASE_PATH + template.getUrl()),
                new File(exportPath + ConfigProperties.fileSeparator + "Filebase" + template.getUrl())
            );
        });

        TemplateStrategy templateStrategyTemplate = new TemplateStrategy();
        templateStrategyTemplate.setIsDelete(false);
        templateStrategyTemplate.setGenerator(generator);
        List<TemplateStrategy> templateStrategies = templateStrategyRepository.selectList(templateStrategyTemplate);
        String templateStrategyPath = ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + "TemplateStrategy";
        templateStrategies.forEach(templateStrategy ->
            FileUtil.copyFile(
                new File(templateStrategyPath + ConfigProperties.fileSeparator + templateStrategy.getId() + ".xml"),
                new File(templateStrategyExportPath + ConfigProperties.fileSeparator + templateStrategy.getId() + ".xml")
            )
        );
        File exportFolder = new File(exportPath);
        try {
            ZipUtil.compress(exportFolder);
        }
        catch (Exception e){
            throw new AppException(e,"压缩文件失败");
        }
        FileUtil.deleteFile(exportFolder);
        return generator;
    }

    @Override
    public Generator _import(GeneratorImportRequest request) {
        Long userId = request.getAuthentication().getUserId();

        Base64FileItem generatorFile = request.getGeneratorFile();
        String type = generatorFile.getName().substring(generatorFile.getName().lastIndexOf(".") + 1);
        if(!"zip".equals(type)){
            throw new AppException("格式不支持");
        }

        Long tempId = idWorker.nextId();
        String fileAbsolutePath = ConfigProperties.TEMPORARY_PATH + ConfigProperties.fileSeparator + userId + ConfigProperties.fileSeparator + tempId + ConfigProperties.fileSeparator  + tempId + "." + type;
        File zipFile = new File(fileAbsolutePath);
        try {
            FileUtil.writeBase64(generatorFile.getContent(), fileAbsolutePath);
        }
        catch (Exception e) {
            throw new AppException("上传失败");
        }
        try {
            ZipUtil.decompress(zipFile);
        }
        catch (Exception e) {
            throw new AppException("解压失败");
        }
        FileUtil.deleteFile(zipFile);
        File zipParentFolder = zipFile.getParentFile();
        File[]  zipParentFolderList = zipParentFolder.listFiles((dir, name) -> true);
        if(zipParentFolderList.length != 1){
            FileUtil.deleteFile(zipParentFolder);
            throw new AppException("格式不兼容");
        }

        File importFolder = zipParentFolderList[0];
        String importPath = importFolder.getAbsolutePath();

        String generatorImportPath = importPath + ConfigProperties.fileSeparator + "Database" + ConfigProperties.fileSeparator + "Generator";
        File generatorImportFolder = new File(generatorImportPath);
        File[] generatorImportFolderList = generatorImportFolder.listFiles((dir, name) -> name.endsWith(".xml"));
        if(generatorImportFolderList.length != 1){
            FileUtil.deleteFile(zipParentFolder);
            throw new AppException("格式不兼容");
        }

        File generatorImportFile = generatorImportFolderList[0];
        Generator generator;
        try {
            Document document = FileUtil.readXml(generatorImportFile.getAbsolutePath());
            Element element = document.getRootElement();
            generator = generatorElementConverter.fromElement(element);
            generator.setId(idWorker.nextId());
            generator.getDeveloper().setId(userId);
            generatorRepository.insert(generator);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

        String dynamicModelImportPath = importPath + ConfigProperties.fileSeparator + "Database" + ConfigProperties.fileSeparator + "DynamicModel";
        File dynamicModelImportFolder = new File(dynamicModelImportPath);
        File[] dynamicModelImportFolderList = dynamicModelImportFolder.listFiles((dir, name) -> name.endsWith(".xml"));
        if(dynamicModelImportFolderList != null) {
            for (File dynamicModelImportFile : dynamicModelImportFolderList) {
                try {
                    Document document = FileUtil.readXml(dynamicModelImportFile.getAbsolutePath());
                    Element element = document.getRootElement();
                    DynamicModel dynamicModel = dynamicModelElementConverter.fromElement(element);
                    dynamicModel.setId(idWorker.nextId());
                    dynamicModel.getGenerator().setId(generator.getId());
                    dynamicModelRepository.insert(dynamicModel);
                }
                catch (Exception e) {
                    //throw new RuntimeException(e);
                }
            }
        }

        Map<Long, Long> vmIdMap = new HashMap<>();
        String templateImportPath = importPath + ConfigProperties.fileSeparator + "Database" + ConfigProperties.fileSeparator + "Template";
        File templateImportFolder = new File(templateImportPath);
        File[] templateImportFolderList = templateImportFolder.listFiles((dir, name) -> name.endsWith(".xml"));
        if(templateImportFolderList != null) {
            for (File templateImportFile : templateImportFolderList) {
                try {
                    Document document = FileUtil.readXml(templateImportFile.getAbsolutePath());
                    Element element = document.getRootElement();
                    Template template = templateElementConverter.fromElement(element);
                    Long oldId = template.getId();
                    Long newId = idWorker.nextId();
                    vmIdMap.put(oldId, newId);
                    template.setId(newId);
                    template.getGenerator().setId(generator.getId());

                    String url = "/Template/" + generator.getId() + "/" + idWorker.nextId() + ".vm";
                    FileUtil.mkdirs(ConfigProperties.FILEBASE_PATH + "/Template/" + generator.getId());
                    FileUtil.copyFile(
                            new File(importPath + ConfigProperties.fileSeparator + "Filebase" + template.getUrl()),
                            new File(ConfigProperties.FILEBASE_PATH + url)
                    );
                    template.setUrl(url);

                    templateRepository.insert(template);
                }
                catch (Exception e) {
                    //throw new RuntimeException(e);
                }
            }
        }

        String templateStrategyImportPath = importPath + ConfigProperties.fileSeparator + "Database" + ConfigProperties.fileSeparator + "TemplateStrategy";
        File templateStrategyImportFolder = new File(templateStrategyImportPath);
        File[] templateStrategyImportFolderList = templateStrategyImportFolder.listFiles((dir, name) -> name.endsWith(".xml"));
        if(templateStrategyImportFolderList != null) {
            for (File templateStrategyImportFile : templateStrategyImportFolderList) {
                try {
                    Document document = FileUtil.readXml(templateStrategyImportFile.getAbsolutePath());
                    Element element = document.getRootElement();
                    TemplateStrategy templateStrategy = templateStrategyElementConverter.fromElement(element);
                    templateStrategy.setId(idWorker.nextId());
                    templateStrategy.getGenerator().setId(generator.getId());

                    LinkedList<Tag> stack = new LinkedList<>();
                    stack.push(templateStrategy);
                    while (!stack.isEmpty()) {
                        Tag tag = stack.pop();
                        if (tag instanceof com.lite.blackdream.business.domain.tag.File) {
                            com.lite.blackdream.business.domain.tag.File fileTag = (com.lite.blackdream.business.domain.tag.File) tag;
                            Long oldId = fileTag.getTemplate().getId();
                            fileTag.getTemplate().setId(vmIdMap.get(oldId));
                        }
                        tag.getChildren().forEach(stack::push);
                    }
                    templateStrategyRepository.insert(templateStrategy);
                }
                catch (Exception e) {
                    //throw new RuntimeException(e);
                }
            }
        }

        FileUtil.deleteFile(zipParentFolder);
        return generator;
    }

}
