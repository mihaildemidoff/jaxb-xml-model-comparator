package com.github.mihaildemidoff.xml.comparator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author Mikhail Demidov
 */
public class XmlModelComparatorTest {

    private XmlModelComparator comparator;

    @Before
    public void setUp() throws Exception {
        comparator = new XmlModelComparator();
    }

    @Test
    public void testEmptyModels() {
        final XmlModel originModel = new XmlModel();
        final XmlModel jaxbModel = new XmlModel();
        final List<XmlModel> response = comparator.findDifference(originModel, jaxbModel);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isEmpty());
    }

    @Test
    public void testDifferenceOnTheSameLevel() {
        final XmlModel firstTree = createModel(1, 1);
        firstTree.getChildren().add(createModel(2, 1, firstTree));
        firstTree.getChildren().add(createModel(2, 2, firstTree));

        final XmlModel secondTree = createModel(1, 1);
        secondTree.getChildren().add(createModel(2, 1, secondTree));

        final List<XmlModel> response = comparator.findDifference(firstTree, secondTree);
        Assert.assertNotNull(response);
        Assert.assertEquals(1, response.size());
        Assert.assertEquals(firstTree.getChildren().get(1), response.get(0));
    }

    @Test
    public void testDifferenceFirstLevel() {
        final XmlModel firstTree = createModel(1, 1, "f");
        final XmlModel secondTree = createModel(1, 1, "s");

        final List<XmlModel> response = comparator.findDifference(firstTree, secondTree);
        Assert.assertNotNull(response);
        Assert.assertEquals(1, response.size());
        Assert.assertEquals(firstTree, response.get(0));
    }

    @Test
    public void testDifferenceDifferentLevelAndAllNodes() {
        final XmlModel firstTree = createModel(1, 1, "f");
        firstTree.getChildren().add(createModel(2, 1, "f", firstTree));
        final XmlModel secondTree = createModel(1, 1, "s");

        final List<XmlModel> response = comparator.findDifference(firstTree, secondTree);
        Assert.assertNotNull(response);
        Assert.assertEquals(2, response.size());
        Assert.assertTrue(response.contains(firstTree));
        Assert.assertTrue(response.containsAll(firstTree.getChildren()));
    }

    @Test
    public void testEqualsSubtrees() {
        final XmlModel firstTree = createModel(1, 1);
        firstTree.getChildren().add(createModel(2, 1, firstTree));
        firstTree.getChildren().add(createModel(2, 2, firstTree));
        final XmlModel secondTree = createModel(1, 1);
        secondTree.getChildren().add(createModel(2, 1, secondTree));
        secondTree.getChildren().add(createModel(2, 2, secondTree));

        final List<XmlModel> response = comparator.findDifference(firstTree, secondTree);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isEmpty());
    }

    @Test
    public void testAbsentInside() {
        final XmlModel firstTree = createModel(1, 1);
        firstTree.getChildren().add(createModel(2, 1, firstTree));
        firstTree.getChildren().add(createModel(2, 2, firstTree));
        final XmlModel secondTree = createModel(1, 1);
        secondTree.getChildren().add(createModel(2, 2, secondTree));

        final List<XmlModel> response = comparator.findDifference(firstTree, secondTree);
        Assert.assertNotNull(response);
        Assert.assertEquals(1, response.size());
        Assert.assertEquals(firstTree.getChildren().get(0), response.get(0));
    }


    private XmlModel createModel(final int level, final int index) {
        return createModel(level, index, "");
    }

    private XmlModel createModel(final int level, final int index, final String suffix) {
        return createModel(level, index, suffix, null);
    }

    private XmlModel createModel(final int level, final int index, final XmlModel parent) {
        return createModel(level, index, "", parent);
    }

    private XmlModel createModel(final int level, final int index, final String suffix, final XmlModel parent) {
        final XmlModel output = new XmlModel();
        output.setName(generateName(level, index, suffix));
        output.setParent(parent);
        return output;
    }

    private String generateName(final int level, final int index, final String suffix) {
        return "Node" + level + index + suffix;
    }


}
