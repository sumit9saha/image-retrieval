/*
 * FeatureExtractionFactory.java
 *
 *
 *  Copyright (C) 2007 COMP5425 Multimedia Storage, Retrieval and Delivery
 *  The School of Information Technology
 *  The University of Sydney
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package usyd.comp5425.image;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Yuezhong Zhang  SID:305275631
 */
public class FeatureModuleFactory {
    private  static FeatureModuleFactory factory;
    private Hashtable<String,FeatureModule> features = new Hashtable<String,FeatureModule>();
    private Properties props = new Properties();
    private FeatureModuleFactory() {
        
        props.setProperty("AverageRGB","usyd.comp5425.image.AverageRGBModule");
        props.setProperty("Color_Moments","usyd.comp5425.image.ColorMomentFeatureModule");
        props.setProperty("Cooccurence","usyd.comp5425.image.CooccurenceModule");
        props.setProperty("Local_Color_Histogram","usyd.comp5425.image.LocalColorHistogram");
        props.setProperty("Global_Color_Histogram","usyd.comp5425.image.GlobalColorHistogram");
        props.setProperty("Geometric_Moment","usyd.comp5425.image.GeometricMoment");
        
    }
    public static FeatureModuleFactory getInstance(){
        if(factory == null){
            factory = new FeatureModuleFactory();
        }
        return factory;
    }
    public FeatureModule getFeatureModule(String name){
        FeatureModule module = null;
        String className = props.getProperty(name);
        if(className == null){
            return null;
        }
        module = features.get(className);
        if(module == null){
            try {
                Class c =  Class.forName(className);
                module = (FeatureModule) c.newInstance();
                features.put(className,module);
            } catch (Exception ex) {
                return null;
            }
        }
        return  module;
    }
    public Enumeration getModulesName(){
        return props.propertyNames();
    }
    public List<String> getModulesNameList(){
        List<String> list = new ArrayList<String>();
        for(Enumeration e = this.getModulesName(); e.hasMoreElements();){
            list.add((String)e.nextElement());
        }
        return list;
    }
    public int getNumberOfMoudle(){
        return props.size();
    }
    
}
