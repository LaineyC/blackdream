package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.framework.el.Context;

/**
 * @author LaineyC
 */
public class Continue extends Tag{

    public static class Exception extends RuntimeException{

    }

    public Continue(){

    }

    public Continue clone(){
        Continue continueTag = (Continue)super.clone();
        return continueTag;
    }

    @Override
    public void execute(Context context) {
        throw new Exception();
    }

}
