package com.lite.blackdream.sample.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LaineyC
 */
public class Entity extends DataModel{

    private Properties properties;

    private List<Property> association = new ArrayList<>();

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public List<Property> getAssociation() {
        return association;
    }

    public void setAssociation(List<Property> association) {
        this.association = association;
    }

    public static class Properties{

        private String tableName;

        private String comment;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    public enum PropertyType{
        Boolean,
        Integer,
        Long,
        Double,
        String,
        Date
    }

    public static enum JdbcType{
        BIT,
        INTEGER,
        BIGINT,
        DECIMAL,
        VARCHAR,
        DATE,
        TIME,
        TIMESTAMP
    }

    public static enum FieldType{
        TINYINT,
        INT,
        BIGINT,
        DECIMAL,
        VARCHAR,
        DATE,
        TIME,
        DATETIME
    }

    public static class Property{

        private String propertyName;

        private PropertyType propertyType;

        private String propertyComment;

        private JdbcType jdbcType;

        private String fieldName;

        private FieldType fieldType;

        private String fieldComment;

        private Integer fieldLength;

        private Integer decimalLength;

        private Boolean isPrimaryKey;

        private Boolean isAutoIncrement;

        private Boolean isUnique;

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public PropertyType getPropertyType() {
            return propertyType;
        }

        public void setPropertyType(PropertyType propertyType) {
            this.propertyType = propertyType;
        }

        public String getPropertyComment() {
            return propertyComment;
        }

        public void setPropertyComment(String propertyComment) {
            this.propertyComment = propertyComment;
        }

        public JdbcType getJdbcType() {
            return jdbcType;
        }

        public void setJdbcType(JdbcType jdbcType) {
            this.jdbcType = jdbcType;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public FieldType getFieldType() {
            return fieldType;
        }

        public void setFieldType(FieldType fieldType) {
            this.fieldType = fieldType;
        }

        public String getFieldComment() {
            return fieldComment;
        }

        public void setFieldComment(String fieldComment) {
            this.fieldComment = fieldComment;
        }

        public Integer getFieldLength() {
            return fieldLength;
        }

        public void setFieldLength(Integer fieldLength) {
            this.fieldLength = fieldLength;
        }

        public Integer getDecimalLength() {
            return decimalLength;
        }

        public void setDecimalLength(Integer decimalLength) {
            this.decimalLength = decimalLength;
        }

        public Boolean getIsPrimaryKey() {
            return isPrimaryKey;
        }

        public void setIsPrimaryKey(Boolean isPrimaryKey) {
            this.isPrimaryKey = isPrimaryKey;
        }

        public Boolean getIsAutoIncrement() {
            return isAutoIncrement;
        }

        public void setIsAutoIncrement(Boolean isAutoIncrement) {
            this.isAutoIncrement = isAutoIncrement;
        }

        public Boolean getIsUnique() {
            return isUnique;
        }

        public void setIsUnique(Boolean isUnique) {
            this.isUnique = isUnique;
        }
    }

}
