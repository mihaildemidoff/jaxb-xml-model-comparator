package com.github.mihaildemidoff.xml.comparator;

import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * XmlModel builder. Builds XmlModel from input XML.
 *
 * @author Mikhail Demidov
 */
public class ModelBuilder {


    /**
     * Default constructor.
     */
    public ModelBuilder() {
    }

    /**
     * Build XmlModel from original XML.
     *
     * @param pathToFile path to XML file.
     * @return XmlModel builded from the original XML.
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public XmlModel buildModel(final String pathToFile) throws IOException, SAXException, ParserConfigurationException {
        InputStream xmlStream = null;
        try {
            xmlStream = new FileInputStream(pathToFile);
            return buildModel(xmlStream);
        } finally {
            IOUtils.closeQuietly(xmlStream);
        }
    }

    /**
     * Build XmlModel from original XML.
     *
     * @param xmlInputStream xml input stream.
     * @return XmlModel builded from the original XML.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public XmlModel buildModel(final InputStream xmlInputStream)
        throws ParserConfigurationException, SAXException, IOException {
        final SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        final SAXParser parser = parserFactory.newSAXParser();
        final XmlModelSaxHandler handler = new XmlModelSaxHandler();
        parser.parse(xmlInputStream, handler);
        return handler.getRoot();
    }


}
