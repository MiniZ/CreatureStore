package main.java.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class XMLUtils {

    private static final Map<Class, JAXBContext> contextsMap = new HashMap<>();

    public static <T> String toXML(T object) {
        if (object == null) {
            return null;
        }
        Class clazz = object.getClass();
        JAXBContext context = getContext(clazz);
        try {
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);
            return writer.toString();

        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static <T> T fromXML(String xml, Class clazz) {
        if (xml == null || clazz == null) {
            return null;
        }
        JAXBContext context = getContext(clazz);
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            //noinspection unchecked
            return (T) unmarshaller.unmarshal(reader);

        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static JAXBContext getContext(Class clazz) {
        JAXBContext context = contextsMap.get(clazz);
        if (context == null) {
            try {
                context = JAXBContext.newInstance(clazz);
                contextsMap.put(clazz, context);
            } catch (Exception ex) {
                String message = "Unable to create JAXB context for class: " + clazz.getName();
                throw new RuntimeException(message, ex);
            }
        }
        return context;
    }
}
