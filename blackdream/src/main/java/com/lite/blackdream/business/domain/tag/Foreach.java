package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.framework.el.Context;
import com.lite.blackdream.framework.el.Parser;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author LaineyC
 */
public class Foreach extends Tag{

    private String item;

    private String items;

    private String status;

    public Foreach clone(){
        Foreach foreach = (Foreach)super.clone();
        foreach.setItem(this.getItem());
        foreach.setItems(this.getItems());
        foreach.setStatus(this.getStatus());
        return foreach;
    }

    public void execute(Context context){
        Object itemsObject = Parser.parseObject(items, context);
        Context exeContext = new Context();
        exeContext.setVariable(item,"item");
        exeContext.mergeVariable(context);
        int index = 0;
        if(itemsObject instanceof Collection){
            Collection<?> items = (Collection<?>)itemsObject;
            for(Object t : items){
                exeContext.setVariable(item,t);
                if(status != null && !"".equals(status)){
                    Status s = new Status();
                    s.setFirst(index == 0);
                    s.setLast(index == items.size() - 1);
                    s.setIndex(index);
                    s.setCount(index + 1);
                    exeContext.setVariable(status, s);
                    index++;
                }
                for(Tag child : this.getChildren()){
                    child.setParent(this);
                    try{
                        child.execute(exeContext);
                    }
                    catch(Break.Exception breakException){
                        return;
                    }
                    catch(Continue.Exception continueException){

                    }
                }
            }
        }
        else if(itemsObject instanceof Map){
            Map<String, ?> items = (Map<String, ?>)itemsObject;
            for(Map.Entry<String, ?> t : items.entrySet()){
                exeContext.setVariable(item,t);
                if(status != null && !"".equals(status)){
                    Status s = new Status();
                    s.setFirst(index == 0);
                    s.setLast(index == items.size() - 1);
                    s.setIndex(index);
                    s.setCount(index + 1);
                    exeContext.setVariable(status, s);
                    index++;
                }
                for(Tag child : this.getChildren()){
                    child.setParent(this);
                    try{
                        child.execute(exeContext);
                    }
                    catch(Break.Exception breakException){
                        return;
                    }
                    catch(Continue.Exception continueException){

                    }
                }
            }
        }
        else if(itemsObject.getClass().isArray()){
            int length = Array.getLength(itemsObject);
            for(int i = 0 ; i < length ; i++){
                Object t = Array.get(itemsObject, i);
                exeContext.setVariable(item,t);
                if(status != null && !"".equals(status)){
                     Status s = new Status();
                     s.setFirst(index == 0);
                     s.setLast(index == length - 1);
                     s.setIndex(index);
                     s.setCount(index + 1);
                     exeContext.setVariable(status, s);
                     index++;
                 }
                for(Tag child : this.getChildren()){
                    child.setParent(this);
                    try{
                        child.execute(exeContext);
                    }
                    catch(Break.Exception breakException){
                        return;
                    }
                    catch(Continue.Exception continueException){

                    }
                }
            }
        }
        else if ((itemsObject instanceof Iterator)) {
            Iterator items = (Iterator)itemsObject;
            while (items.hasNext()) {
                Object t = items.next();
                exeContext.setVariable(item,t);
                if(status != null && !"".equals(status)){
                    Status s = new Status();
                    s.setFirst(index == 0);
                    s.setLast(!items.hasNext());
                    s.setIndex(index);
                    s.setCount(index + 1);
                    exeContext.setVariable(status, s);
                    index++;
                }
                for(Tag child : this.getChildren()){
                    child.setParent(this);
                    try{
                        child.execute(exeContext);
                    }
                    catch(Break.Exception breakException){
                        return;
                    }
                    catch(Continue.Exception continueException){

                    }
                }
            }
        }
        else if ((itemsObject instanceof Enumeration)) {
            Enumeration items = (Enumeration)itemsObject;
            while (items.hasMoreElements()) {
                Object t = items.nextElement();
                exeContext.setVariable(item,t);
                if(status != null && !"".equals(status)){
                    Status s = new Status();
                    s.setFirst(index == 0);
                    s.setLast(!items.hasMoreElements());
                    s.setIndex(index);
                    s.setCount(index + 1);
                    exeContext.setVariable(status, s);
                    index++;
                }
                for(Tag child : this.getChildren()){
                    child.setParent(this);
                    try{
                        child.execute(exeContext);
                    }
                    catch(Break.Exception breakException){
                        return;
                    }
                    catch(Continue.Exception continueException){

                    }
                }
            }
        }
        else{
            throw new RuntimeException(items + "不能遍历");
        }
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Status{

         private boolean first;

         private boolean last;

         private int index;

         private int count;

         public Status(){

         }

         public boolean isFirst() {
             return first;
         }

         public void setFirst(boolean first) {
             this.first = first;
         }

         public boolean isLast() {
             return last;
         }

         public void setLast(boolean last) {
             this.last = last;
         }

         public int getIndex() {
             return index;
         }

         public void setIndex(int index) {
             this.index = index;
         }

         public int getCount() {
             return count;
         }

         public void setCount(int count) {
             this.count = count;
         }

    }

}
