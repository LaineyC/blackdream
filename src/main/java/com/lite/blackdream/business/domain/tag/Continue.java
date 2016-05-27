package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.framework.el.Context;

/**
 * @author LaineyC
 */
public class Continue extends Tag{

    public static class Exception extends RuntimeException{

        public Exception(String message) {
            super(message);
        }

    }

    public Continue(){

    }

    public Continue clone(){
        Continue continueTag = (Continue)super.clone();
        return continueTag;
    }

    @Override
    public void execute(Context context) {
        throw new Exception("Continue标签在Foreach标签之外");
    }

}
