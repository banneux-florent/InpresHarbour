/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import Classes.Bateau;
import Classes.BateauPeche;
import Classes.BateauPlaisance;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author flore
 */
public class XMLFormatter {
    
    public static String toXML(Object object) 
    {
        try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
            //Print XML String to Console
            StringWriter sw = new StringWriter();
             
            //Write XML to StringWriter
            jaxbMarshaller.marshal(object, sw);
            
            String xml = sw.toString();
            
            // Remove spaces at start of a new line
            xml = xml.replaceAll("\n[ ]+<", "\n<");
            
            // Remove new lines
            xml = xml.replace("\n", "").replace("\r", "");
            
            // Remove XML header (? retirer si ça bug)
            xml = xml.substring(xml.indexOf(">") + 1);
             
            return xml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static Object fromXML(String xml) 
    {
        Object objectToReturn = null;
        try {
            //Create JAXB Context
            JAXBContext jaxbContext = null;
            
            Class<?> objectClass = null;
            String type = XMLFormatter.getType(xml);
            switch (type) {
                case "bateauPlaisance":
                    objectClass = BateauPlaisance.class;
                    break;
                case "bateauPeche":
                    objectClass = BateauPeche.class;
                    break;
                case "bateau":
                    objectClass = Bateau.class;
                    break;
                case "frame":
                    objectClass = Frame.class;
                    break;
                default:
                    System.err.println("[XMLFormatter | Error] Type \"" + type + "\" isn't supported.");
                    return objectToReturn;
            }
            
            // Assign JAXB Context
            jaxbContext = JAXBContext.newInstance(objectClass);
            
            //Create Marshaller
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            
            StringReader sr = new StringReader(xml);
            
            objectToReturn = jaxbUnmarshaller.unmarshal(sr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectToReturn;
    }
    
    public static String getType(String xml) {
        Pattern p = Pattern.compile("^<[\\w]+>.*");//. represents single character
        Matcher m = p.matcher(xml);
        return m.matches() ? xml.substring(1, xml.indexOf(">")) : "";
    }
    
    public static String decode(String xml) {
        return xml.replace("&lt;", "<").replace("&gt;", ">");
    }
    
}
