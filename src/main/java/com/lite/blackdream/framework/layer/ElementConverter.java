package com.lite.blackdream.framework.layer;

import com.lite.blackdream.framework.model.Domain;
import org.dom4j.Element;

/**
 * @author LaineyC
 */
public interface ElementConverter<E extends Domain> {

    Element toElement(E entity);

    E fromElement(Element element);

}
