package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.framework.model.Base64FileItem;
import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class GeneratorImportRequest extends Request {

    private Base64FileItem generatorFile;

    public GeneratorImportRequest(){

    }

    public Base64FileItem getGeneratorFile() {
        return generatorFile;
    }

    public void setGeneratorFile(Base64FileItem generatorFile) {
        this.generatorFile = generatorFile;
    }

}
