package com.github.mihaildemidoff.xml.comparator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing xml structure where each XmlModel is a tag.
 *
 * @author Mikhail Demidov
 */
public class XmlModel {

    private String name;

    private List<XmlModel> children = new ArrayList<XmlModel>();

    private XmlModel parent;

    /**
     * Default constructor.
     */
    public XmlModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<XmlModel> getChildren() {
        return children;
    }

    public void setChildren(final List<XmlModel> children) {
        this.children = children;
    }

    public XmlModel getParent() {
        return parent;
    }

    public void setParent(final XmlModel parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("name", name)
            .append("children", children)
            .append("parent", parent)
            .toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final XmlModel xmlModel = (XmlModel) o;

        return new EqualsBuilder()
            .append(name, xmlModel.name)
            .append(children, xmlModel.children)
            .append(parent, xmlModel.parent)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(name)
            .append(parent)
            .toHashCode();
    }
}
