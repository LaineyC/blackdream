package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.business.domain.Generator;
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
        VAR_MAP.put("alternator",new AlternatorTool());
        VAR_MAP.put("class",new ClassTool());
        VAR_MAP.put("context",new ContextTool());
        VAR_MAP.put("conversion",new ConversionTool());
        VAR_MAP.put("date",new ComparisonDateTool());
        VAR_MAP.put("display",new DisplayTool());
        VAR_MAP.put("escape",new EscapeTool());
        VAR_MAP.put("field",new FieldTool());
        VAR_MAP.put("loop",new LoopTool());
        VAR_MAP.put("link",new LinkTool());
        VAR_MAP.put("list",new ListTool());
        VAR_MAP.put("math",new MathTool());
        VAR_MAP.put("number",new NumberTool());
        VAR_MAP.put("render",new RenderTool());
        VAR_MAP.put("resource",new ResourceTool());
        VAR_MAP.put("sort",new SortTool());
        VAR_MAP.put("xml",new XmlTool());
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
        Generator generator = global.getGenerator();
        Map<Long, Template> templateCache = global.getTemplateCache();
        String generatePath = ConfigProperties.TEMPORARY_PATH + ConfigProperties.fileSeparator + userId + ConfigProperties.fileSeparator + generator.getName() + "(" + generateId + ")";
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
