package Network;

import Classes.Bateau;
import Classes.BateauPeche;
import Classes.BateauPlaisance;
import Classes.CapitainerieSavable;
import Classes.Fonctions;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Florent
 */
public class XMLFormatter {

    public static String toXML(Object object) throws Exception {
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

            // Remove XML header (À retirer si ça bug)
            xml = xml.substring(xml.indexOf(">") + 1);

            return xml;
        } catch (JAXBException e) {
            throw e;
        }
    }

    public static Object fromXML(String xml) throws Exception {
        Object objectToReturn = null;
        try {
            Class<?> objectClass;
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
                case "capitainerieSavable":
                    objectClass = CapitainerieSavable.class;
                    break;
                default:
                    System.err.println("[XMLFormatter | Error] Type \"" + type + "\" isn't supported.");
                    return objectToReturn;
            }

            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(objectClass);

            //Create Marshaller
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            StringReader sr = new StringReader(xml);

            objectToReturn = jaxbUnmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            throw e;
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

    public static void saveXML(String filename, Object object) {
        try {
            String xml = toXML(object);
            filename = Fonctions.getFilePath(filename + ".xml");
            
            FileWriter writer = new FileWriter(filename, false);
            writer.write(xml);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object loadXML(String filename) {
        try {
            filename = Fonctions.getFilePath(filename + ".xml");
            byte[] encoded = Files.readAllBytes(Paths.get(filename));
            String xml = new String(encoded, StandardCharsets.UTF_8);
            return (!xml.equals("")) ? fromXML(xml) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
