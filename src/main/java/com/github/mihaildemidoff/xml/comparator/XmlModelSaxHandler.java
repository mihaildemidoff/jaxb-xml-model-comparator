package com.github.mihaildemidoff.xml.comparator;


import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX handler that builds XmlModel.
 *
 * @author Mikhail Demidov
 */
public class XmlModelSaxHandler extends DefaultHandler {

    private XmlModel root;

    private XmlModel currentVertex;

    /**
     * Default constructor.
     */
    public XmlModelSaxHandler() {
        root = new XmlModel();
        currentVertex = root;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void startElement(final String s,
                             final String s1,
                             final String s2,
                             final Attributes attributes) throws SAXException {
        if (StringUtils.isEmpty(root.getName())) {
            root.setName(s2);
        } else {
            final XmlModel newVertex = new XmlModel();
            newVertex.setParent(currentVertex);
            newVertex.setName(s2);
            currentVertex.getChildren().add(newVertex);
            currentVertex = newVertex;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void endElement(final String s,
                           final String s1,
                           final String s2) throws SAXException {
        currentVertex = currentVertex.getParent();
    }

    /**
     * Return root XmlModel.
     *
     * @return root XmlModel.
     */
    public XmlModel getRoot() {
        return root;
    }
}
