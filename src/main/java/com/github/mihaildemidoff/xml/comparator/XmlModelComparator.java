package com.github.mihaildemidoff.xml.comparator;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * Class for XmlModel comparing.
 *
 * @author Mikhail Demidov
 */
public class XmlModelComparator {

    /**
     * Default constructor.
     */
    public XmlModelComparator() {
    }

    /**
     * Find difference between two models: original and actual jaxb model.
     *
     * @param originalModel Original xml model.
     * @param jaxbModel     Model generated via JAXB.
     * @return XmlModel list where each element is node of the original xml tree.
     */
    public List<XmlModel> findDifference(final XmlModel originalModel, final XmlModel jaxbModel) {
        final List<XmlModel> output = new ArrayList<XmlModel>();
        final Stack<XmlModel> originalStack = new Stack<XmlModel>();
        final Stack<XmlModel> jaxbStack = new Stack<XmlModel>();
        originalStack.add(originalModel);
        jaxbStack.add(jaxbModel);
        while (!originalStack.isEmpty()) {
            final XmlModel originalNode = originalStack.pop();
            final XmlModel jaxbNode = jaxbStack.pop();
            if (jaxbNode == null) {
                output.add(originalNode);
                continue;
            } else if (!StringUtils.equals(originalNode.getName(), jaxbNode.getName())) {
                output.add(originalNode);
            }
            Collections.sort(originalNode.getChildren(), new XmlModelNameComparator());
            Collections.sort(jaxbNode.getChildren(), new XmlModelNameComparator());
            int jaxbIndex = 0;
            for (int i = 0; i < originalNode.getChildren().size(); ++i) {
                final XmlModel originalChild = originalNode.getChildren().get(i);
                originalStack.push(originalChild);
                if (jaxbIndex >= jaxbNode.getChildren().size()) {
                    jaxbStack.push(null);
                    continue;
                }
                final XmlModel jaxbChild = jaxbNode.getChildren().get(jaxbIndex);

                if (!StringUtils.equals(originalChild.getName(), jaxbChild.getName())) {
                    jaxbStack.push(null);
                } else {
                    jaxbStack.push(jaxbChild);
                    ++jaxbIndex;
                }
            }
        }
        return output;
    }

    /**
     * XmlModel name comparator.
     */
    private class XmlModelNameComparator implements Comparator<XmlModel> {
        @Override
        public int compare(final XmlModel a, final XmlModel b) {
            return a.getName().compareTo(b.getName());
        }
    }


}
