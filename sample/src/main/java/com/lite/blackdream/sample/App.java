package com.lite.blackdream.sample;

import com.lite.blackdream.sample.dao.ColumnsDao;
import com.lite.blackdream.sample.dao.DataModelDao;
import com.lite.blackdream.sample.domain.*;
import com.lite.blackdream.sample.po.ColumnsPo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.List;

/**
 * @author LaineyC
 */
public class App{

    private static final Log operationLogger = LogFactory.getLog("operation");

    private static final Log errorLogger = LogFactory.getLog("error");

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        //ID从界面获取
        DynamicModel projectDynamicModel = new DynamicModel();
        projectDynamicModel.setId(1862839232320L);

        DynamicModel moduleDynamicModel = new DynamicModel();
        moduleDynamicModel.setId(1862839232448L);

        DynamicModel entityDynamicModel = new DynamicModel();
        entityDynamicModel.setId(1862839234496L);

        DataModel root = new DataModel();
        root.setId(3459895916353L);

        String databaseName = "test1";
        ColumnsPo columnsPo = new ColumnsPo();
        columnsPo.setTABLE_SCHEMA(databaseName);

        ColumnsDao columnsDao = context.getBean(ColumnsDao.class);
        DataModelDao dataModelDao = context.getBean(DataModelDao.class);

        P p = new P("Sample", "com.lite.blackdream.sample", "示例项目", new M[]{
                new M("organization", "组织", new E[]{
                        new E("Employee", "employee", "员工")
                })
        });
        Project project = new Project();
        project.setDynamicModel(projectDynamicModel);
        project.setName(p.name);
        project.setParent(root);
        project.setIsExpand(false);
        Project.Properties projectProperties = new Project.Properties();
        projectProperties.setNamespace(p.namespace);
        projectProperties.setComment(p.comment);
        project.setProperties(projectProperties);
        for(M m : p.ms) {
            Module module = new Module();
            module.setDynamicModel(moduleDynamicModel);
            module.setName(m.name);
            module.setParent(project);
            module.setIsExpand(false);
            Module.Properties moduleProperties = new Module.Properties();
            moduleProperties.setComment(m.comment);
            module.setProperties(moduleProperties);
            project.getChildren().add(module);
            for (E e : m.es) {
                Entity entity = new Entity();
                entity.setDynamicModel(entityDynamicModel);
                entity.setName(e.name);
                entity.setParent(module);
                entity.setIsExpand(false);
                Entity.Properties entityProperties = new Entity.Properties();
                entityProperties.setTableName(e.tableName);
                entityProperties.setComment(e.comment);
                entity.setProperties(entityProperties);
                columnsPo.setTABLE_NAME(e.tableName);
                List<ColumnsPo> cols = columnsDao.selectList(columnsPo);
                module.getChildren().add(entity);
                for(ColumnsPo col : cols){
                    String name = col.getCOLUMN_NAME();
                    String dataType = col.getDATA_TYPE();
                    String comment = col.getCOLUMN_COMMENT();
                    String type = col.getCOLUMN_TYPE();
                    String key = col.getCOLUMN_KEY();
                    String extra = col.getEXTRA();
                    Entity.FieldType fieldType;
                    try {
                        fieldType = Entity.FieldType.valueOf(dataType.toUpperCase());
                    }
                    catch (Exception ex){
                        errorLogger.error("读取表信息失败，未匹配到类型：" + e.tableName + " - " + name + " - " + dataType, ex);
                        return;
                    }
                    Entity.Property property = new Entity.Property();
                    switch (fieldType){
                        case TINYINT:
                            property.setPropertyType(Entity.PropertyType.Boolean);
                            property.setJdbcType(Entity.JdbcType.BIT);
                            property.setFieldLength(1);
                            break;
                        case INT:
                            property.setPropertyType(Entity.PropertyType.Integer);
                            property.setJdbcType(Entity.JdbcType.INTEGER);
                            property.setFieldLength(11);
                            break;
                        case BIGINT:
                            property.setPropertyType(Entity.PropertyType.Long);
                            property.setJdbcType(Entity.JdbcType.BIGINT);
                            property.setFieldLength(20);
                            break;
                        case DECIMAL:
                            property.setPropertyType(Entity.PropertyType.Double);
                            property.setJdbcType(Entity.JdbcType.DECIMAL);
                            property.setFieldLength(10);
                            property.setDecimalLength(2);
                            break;
                        case VARCHAR:
                            property.setPropertyType(Entity.PropertyType.String);
                            property.setJdbcType(Entity.JdbcType.VARCHAR);
                            property.setFieldLength(100);
                            break;
                        case DATE:
                            property.setPropertyType(Entity.PropertyType.Date);
                            property.setJdbcType(Entity.JdbcType.DATE);
                            break;
                        case TIME:
                            property.setPropertyType(Entity.PropertyType.Date);
                            property.setJdbcType(Entity.JdbcType.TIME);
                            break;
                        case DATETIME:
                            property.setPropertyType(Entity.PropertyType.Date);
                            property.setJdbcType(Entity.JdbcType.TIMESTAMP);
                            break;
                    }
                    property.setFieldType(fieldType);
                    property.setPropertyName(name);
                    property.setPropertyComment(comment);
                    property.setFieldName(name);
                    property.setFieldComment(comment);
                    property.setIsPrimaryKey("PRI".equalsIgnoreCase(key));
                    property.setIsAutoIncrement("auto_increment".equalsIgnoreCase(extra));
                    property.setIsUnique("UNI".equalsIgnoreCase(key));
                    entity.getAssociation().add(property);
                }
                operationLogger.info("读取表信息成功：" + e.tableName);
            }
        }

        dataModelDao.insert(project, root);
        for(Module module : project.getChildren()){
            dataModelDao.insert(module, root);
            for(Entity entity : module.getChildren()){
                dataModelDao.insert(entity, root);
            }
        }
    }

    static class P{
        String name;
        String namespace;
        String comment;
        M[] ms;
        public P(String name, String namespace, String comment, M[] ms) {
            this.name = name;
            this.namespace = namespace;
            this.comment = comment;
            this.ms = ms;
        }
    }
    static class M{
        String name;
        String comment;
        E[] es;
        public M(String name, String comment, E[] es) {
            this.name = name;
            this.comment = comment;
            this.es = es;
        }
    }

    static class E{
        String name;
        String tableName;
        String comment;
        public E(String name, String tableName, String comment) {
            this.name = name;
            this.tableName = tableName;
            this.comment = comment;
        }
    }

}