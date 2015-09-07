package com.github.mihaildemidoff.xml.comparator;

import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Main class of the comparator.
 *
 * @author Mikhail Demidov
 */
public class XmlAnalyzer {

    public XmlAnalyzer() {
    }

    public List<XmlModel> findDifference(final String originalFile, final Class jaxbModelClass)
        throws JAXBException, IOException, SAXException, ParserConfigurationException {
        InputStream xmlStream = null;
        try {
            xmlStream = new FileInputStream(new File(originalFile));
            return findDifference(xmlStream, jaxbModelClass);
        } finally {
            IOUtils.closeQuietly(xmlStream);
        }
    }

    public List<XmlModel> findDifference(final InputStream originalFile, final Class jaxbModelClass)
        throws IOException, SAXException, ParserConfigurationException, JAXBException {
        final byte[] bytes = IOUtils.toByteArray(originalFile);
        final ModelBuilder modelBuilder = new ModelBuilder();
        final XmlModel originXmlModel = modelBuilder.buildModel(new ByteArrayInputStream(bytes));
        final JAXBXmlModelBuilder jaxbXmlModelBuilder = new JAXBXmlModelBuilder();
        final XmlModel jaxbXmlModel =
            jaxbXmlModelBuilder.buildJaxbXmlModel(new ByteArrayInputStream(bytes), modelBuilder, jaxbModelClass);
        final XmlModelComparator comparator = new XmlModelComparator();
        return comparator.findDifference(originXmlModel, jaxbXmlModel);
    }

}
