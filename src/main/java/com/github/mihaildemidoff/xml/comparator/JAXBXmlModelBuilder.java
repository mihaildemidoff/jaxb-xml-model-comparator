package com.github.mihaildemidoff.xml.comparator;

import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author Mikhail Demidov
 */
public class JAXBXmlModelBuilder {

    public XmlModel buildJaxbXmlModel(final InputStream xmlInputStream,
                                      final ModelBuilder modelBuilder,
                                      final Class jaxbModelClass)
        throws JAXBException, IOException, ParserConfigurationException, SAXException {
        PipedInputStream pin = null;
        PipedOutputStream pout = null;
        try {
            pin = new PipedInputStream();
            pout = new PipedOutputStream(pin);
            final JAXBContext jaxbContext = JAXBContext.newInstance(jaxbModelClass);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            final Object jaxbObject = unmarshaller.unmarshal(xmlInputStream);
            final Marshaller marshaller = jaxbContext.createMarshaller();
            final PipedOutputStream finalPout = pout;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        marshaller.marshal(jaxbObject, finalPout);
                        finalPout.close();
                    } catch (JAXBException e) {
                        //TODO: add logger
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return modelBuilder.buildModel(pin);
        } finally {
            IOUtils.closeQuietly(pin);
            IOUtils.closeQuietly(pout);
        }
    }

}
