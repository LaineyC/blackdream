package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.business.domain.GeneratorInstance;
import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.framework.el.Context;
import com.lite.blackdream.framework.el.Parser;
import com.lite.blackdream.framework.util.ConfigProperties;
import com.lite.blackdream.framework.util.FileUtil;
import com.lite.blackdream.framework.util.VelocityUtil;
import org.apache.velocity.tools.generic.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LaineyC
 */
public class File extends Tag {

    private static final Map<String, Object> VAR_MAP = new HashMap<>();

    static{
        VAR_MAP.put("alternatorTool",new AlternatorTool());
        VAR_MAP.put("classTool",new ClassTool());
        VAR_MAP.put("contextTool",new ContextTool());
        VAR_MAP.put("conversionTool",new ConversionTool());
        VAR_MAP.put("dateTool",new ComparisonDateTool());
        VAR_MAP.put("displayTool",new DisplayTool());
        VAR_MAP.put("escapeTool",new EscapeTool());
        VAR_MAP.put("fieldTool",new FieldTool());
        VAR_MAP.put("loopTool",new LoopTool());
        VAR_MAP.put("linkTool",new LinkTool());
        VAR_MAP.put("listTool",new ListTool());
        VAR_MAP.put("mathTool",new MathTool());
        VAR_MAP.put("numberTool",new NumberTool());
        VAR_MAP.put("renderTool",new RenderTool());
        VAR_MAP.put("resourceTool",new ResourceTool());
        VAR_MAP.put("sortTool",new SortTool());
        VAR_MAP.put("xmlTool",new XmlTool());
    }

    private Template template;

    private String name;

    public File(){

    }

    public File clone(){
        File file = (File)super.clone();
        file.setName(this.getName());
        Template template = new Template();
        template.setId(this.template.getId());
        file.setTemplate(template);
        return file;
    }

    public void execute(Context context){
        Context exeContext = new Context();
        exeContext.mergeVariable(context);
        Global global = (Global)context.getVariable("global");
        Long generateId = global.getGenerateId();
        Long userId = global.getUser().getId();
        GeneratorInstance generatorInstance = global.getGeneratorInstance();
        Map<Long, Template> templateCache = global.getTemplateCache();
        String generatePath = ConfigProperties.TEMPORARY_PATH + ConfigProperties.fileSeparator + userId + ConfigProperties.fileSeparator + generatorInstance.getName() + "(" + generateId + ")";
        String outFile = generatePath + ConfigProperties.fileSeparator + this.getName();
        outFile = Parser.parseString(outFile.replace(ConfigProperties.fileSeparator, "/"), exeContext);
        String templateFile = templateCache.get(template.getId()).getUrl();
        templateFile = templateFile.replace(ConfigProperties.fileSeparator, "/");
        Map<String,Object> varMap = new HashMap<>();
        this.getChildren().forEach(child -> {
            child.setParent(this);
            Map.Entry<String, Object> entry = ((TemplateContext) child).getTemplateContext(exeContext);
            varMap.put(entry.getKey(), entry.getValue());
        });
        VAR_MAP.forEach(varMap::put);
        java.io.File file = new java.io.File(outFile);
        FileUtil.mkdirs(file.getParent());
        VelocityUtil.mergeWrite(templateFile, outFile, varMap);
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
