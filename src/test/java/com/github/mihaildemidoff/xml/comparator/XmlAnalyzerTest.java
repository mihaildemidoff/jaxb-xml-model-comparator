package com.github.mihaildemidoff.xml.comparator;

import com.github.mihaildemidoff.Sml;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author Mikhail Demidov
 */
public class XmlAnalyzerTest {

    private XmlAnalyzer xmlAnalyzer;

    @Before
    public void setUp() throws Exception {
        xmlAnalyzer = new XmlAnalyzer();
    }

    @Test
    public void testSmlWithoutAdditionalFields() throws Exception {
        final String path = ModelBuilder.class.getClassLoader().getResource("normal.sml").getPath();
        final List<XmlModel> output = xmlAnalyzer.findDifference(path, Sml.class);
        Assert.assertNotNull(output);
        Assert.assertTrue(output.isEmpty());
    }

    @Test
    public void testSmlWithAdditionalField() throws Exception {
        final String path = ModelBuilder.class.getClassLoader().getResource("newfield.sml").getPath();
        final List<XmlModel> output = xmlAnalyzer.findDifference(path, Sml.class);
        Assert.assertNotNull(output);
        Assert.assertEquals(1, output.size());
        Assert.assertEquals("TestField", output.get(0).getName());
        Assert.assertEquals("Sample", output.get(0).getParent().getName());
        Assert.assertEquals("Samples", output.get(0).getParent().getParent().getName());
        Assert.assertEquals("DeviceLog", output.get(0).getParent().getParent().getParent().getName());
        Assert.assertEquals("sml", output.get(0).getParent().getParent().getParent().getParent().getName());
    }


}
