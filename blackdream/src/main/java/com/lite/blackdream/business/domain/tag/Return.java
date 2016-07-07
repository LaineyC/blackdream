package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.framework.el.Context;

/**
 * @author LaineyC
 */
public class Return extends Tag {

    public static class Exception extends RuntimeException{

        public Exception(String message) {
            super(message);
        }

    }

    public Return(){

    }

    public Return clone(){
        Return returnTag = (Return)super.clone();
        return returnTag;
    }

    @Override
    public void execute(Context context) {
        throw new Exception("Return标签在Function标签之外");
    }

}
