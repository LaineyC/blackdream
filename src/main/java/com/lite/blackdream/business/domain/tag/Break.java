package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.framework.el.Context;

/**
 * @author LaineyC
 */
public class Break extends Tag {

    public static class Exception extends RuntimeException{

        public Exception(String message) {
            super(message);
        }

    }

    public Break(){

    }

    public Break clone(){
        Break breakTag = (Break)super.clone();
        return breakTag;
    }

    @Override
    public void execute(Context context) {
        throw new Exception("Break标签在Foreach标签之外");
    }

}
