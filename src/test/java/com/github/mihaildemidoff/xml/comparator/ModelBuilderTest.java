package com.github.mihaildemidoff.xml.comparator;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author Mikhail Demidov
 */
public class ModelBuilderTest {


    private ModelBuilder modelBuilder;

    @Before
    public void setUp() throws Exception {
        modelBuilder = new ModelBuilder();
    }

    @Test
    public void testBuildModelByFilename() throws Exception {
        final String path = ModelBuilder.class.getClassLoader().getResource("simplemodel.xml").getPath();
        final XmlModel model = modelBuilder.buildModel(path);
        Assert.assertNotNull(model);
        Assert.assertEquals("root", model.getName());
        Assert.assertEquals("firstLevel1", model.getChildren().get(0).getName());
        Assert.assertEquals("firstLevel2", model.getChildren().get(1).getName());
        Assert.assertEquals("secondLevel11", model.getChildren().get(0).getChildren().get(0).getName());
        Assert.assertEquals("secondLevel21", model.getChildren().get(1).getChildren().get(0).getName());
        Assert.assertEquals("thirdLevel211", model.getChildren().get(1).getChildren().get(0).getChildren()
            .get(0).getName());
    }

    @Test
    public void testBuildModelByFilenameOnlyRoot() throws Exception {
        final String path = ModelBuilder.class.getClassLoader().getResource("simplemodel.xml").getPath();
        final XmlModel model = modelBuilder.buildModel(path);
        Assert.assertNotNull(model);
        Assert.assertEquals("root", model.getName());
    }


    @Test
    public void testBuildModelFromInputStream() throws Exception {
        InputStream is = null;
        try {
            is = ModelBuilderTest.class.getClassLoader().getResourceAsStream("simplemodel.xml");
            final XmlModel model = modelBuilder.buildModel(is);
            Assert.assertNotNull(model);
            Assert.assertEquals("root", model.getName());
            Assert.assertEquals("firstLevel1", model.getChildren().get(0).getName());
            Assert.assertEquals("firstLevel2", model.getChildren().get(1).getName());
            Assert.assertEquals("secondLevel11", model.getChildren().get(0).getChildren().get(0).getName());
            Assert.assertEquals("secondLevel21", model.getChildren().get(1).getChildren().get(0).getName());
            Assert.assertEquals("thirdLevel211", model.getChildren().get(1).getChildren().get(0).getChildren()
                .get(0).getName());
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    @Test
    public void testBuildModelFromInputStreamOnlyRoot() throws Exception {
        InputStream is = null;
        try {
            is = ModelBuilderTest.class.getClassLoader().getResourceAsStream("onlyroot.xml");
            final XmlModel model = modelBuilder.buildModel(is);
            Assert.assertNotNull(model);
            Assert.assertEquals("root", model.getName());
        } finally {
            IOUtils.closeQuietly(is);
        }
    }


}
