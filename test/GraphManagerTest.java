import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GraphManagerTest {
    GraphManager g;

    @Before
    public void setup() throws Exception {
        g = new GraphManager();
        g.parseGraph("input.dot");
        g.outputGraphics("expected.jpg", "jpg");
        g.outputGraphics("expected.png", "png");
    }

    @Test
    public void bfsPathFound(){
        String expected = "a->b->c->d";
        String actual = g.graphSearch("a", "d");
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void bfsPathNotFound(){
        String actual = g.graphSearch("a", "a");
        Assert.assertNull(actual);
    }

    @Test
    public void bfsPathNotFoundNode(){
        String actual = g.graphSearch("a", "f");
        Assert.assertNull(actual);
    }

    @Test
    public void testParseGraph(){
        Assert.assertEquals(5, g.nodeSize());
        Assert.assertEquals(5, g.edgeSize());
        Assert.assertTrue(g.containsEdge("a", "b"));
        Assert.assertTrue(g.containsEdge("b", "c"));
        Assert.assertTrue(g.containsEdge("c", "d"));
        Assert.assertTrue(g.containsEdge("d", "e"));
        Assert.assertTrue(g.containsEdge("a", "e"));
    }

    @Test
    public void testToString(){
        String actual = g.toString();
        String expected = "Number of nodes in a graph : 5\n" +
                "Number of edges in a graph : 5\n" +
                "a -> b\n" +
                "a -> e\n" +
                "b -> c\n" +
                "c -> d\n" +
                "d -> e\n" +
                "e";

        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testOutputGraph() throws Exception{
        g.addEdge("e", "a");

        String expectedFile = "expected.txt";
        String actualFile = "actual.txt";

        g.outputGraph("actual.txt");

        String expected = Files.readString(Paths.get(expectedFile));
        String actual = Files.readString(Paths.get(actualFile));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAddNode(){
        g.addNode("f");
        Assert.assertEquals(6, g.nodeSize());
        Assert.assertTrue(g.containsNode("f"));
    }

    @Test
    public void testRemoveNode(){
        g.removeNode("e");
        Assert.assertEquals(4, g.nodeSize());
        Assert.assertFalse(g.containsNode("e"));
    }

    @Test
    public void testAddNodes(){
        String nodes[] = new String[]{"f", "g"};
        g.addNodes(nodes);
        Assert.assertEquals(7, g.nodeSize());
        Assert.assertTrue(g.containsNode("f"));
        Assert.assertTrue(g.containsNode("g"));
    }

    @Test
    public void testRemoveNodes(){
        String removenodes[] = new String[]{"f", "g", "a"};
        String nodes[] = new String[]{"f", "g"};
        g.addNodes(nodes);
        Assert.assertEquals(7, g.nodeSize());
        Assert.assertTrue(g.containsNode("f"));
        Assert.assertTrue(g.containsNode("g"));

        g.removeNodes(removenodes);
        Assert.assertEquals(4, g.nodeSize());
        Assert.assertEquals(3, g.edgeSize());
        Assert.assertFalse(g.containsNode("f"));
        Assert.assertFalse(g.containsNode("g"));
        Assert.assertFalse(g.containsNode("a"));
        Assert.assertFalse(g.containsEdge("a", "e"));
        Assert.assertFalse(g.containsEdge("a", "b"));
    }

    @Test
    public void testAddEdge(){
        g.addEdge("e", "a");
        Assert.assertEquals(5, g.nodeSize());
        Assert.assertTrue(g.containsEdge("e", "a"));
        Assert.assertEquals(6, g.edgeSize());
    }

    @Test
    public void testRemoveEdge(){
        g.addEdge("e", "a");
        Assert.assertEquals(6, g.edgeSize());
        Assert.assertTrue(g.containsEdge("e", "a"));
        g.removeEdge("e", "a");
        Assert.assertEquals(5, g.edgeSize());
        Assert.assertFalse(g.containsEdge("e", "a"));
    }

    @Test
    public void testOutputDOTGraph() throws Exception{
        String expectedFile = "expected.dot";
        String actualFile = "actual.dot";

        g.addNode("f");

        g.outputDOTGraph(actualFile);
        String expected = Files.readString(Paths.get(expectedFile));
        String actual = Files.readString(Paths.get(actualFile));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testOutputGraphicsPNG() throws Exception{
        String expectedFile = "expected.png";
        String actualFile = "actual.png";

        g.outputGraphics(actualFile, "png");
       // g.outputGraphics(expectedFile, "png");

        BufferedImage actualImage = ImageIO.read(new File(actualFile));
        DataBuffer actualDataBuffer = actualImage.getData().getDataBuffer();
        int actualImageSize = actualDataBuffer.getSize();

        BufferedImage expectedImage = ImageIO.read(new File(expectedFile));
        DataBuffer expectedDataBuffer = expectedImage.getData().getDataBuffer();
        int expectedImageSize = expectedDataBuffer.getSize();

        Assert.assertEquals(expectedImageSize, actualImageSize);
        if (actualImageSize == expectedImageSize) {
            for (int i = 0; i < actualImageSize; i++) {
                Assert.assertEquals(expectedDataBuffer.getElem(i), actualDataBuffer.getElem(i));
            }
        }
    }

    @Test
    public void testOutputGraphicsJPG() throws Exception {
        String expectedFile = "expected.jpg";
        String actualFile = "actual.jpg";

        g.outputGraphics(actualFile, "jpg");
      //  g.outputGraphics(expectedFile, "jpg");

        BufferedImage actualImage = ImageIO.read(new File(actualFile));
        DataBuffer actualDataBuffer = actualImage.getData().getDataBuffer();
        int actualImageSize = actualDataBuffer.getSize();

        BufferedImage expectedImage = ImageIO.read(new File(expectedFile));
        DataBuffer expectedDataBuffer = expectedImage.getData().getDataBuffer();
        int expectedImageSize = expectedDataBuffer.getSize();

        Assert.assertEquals(expectedImageSize, actualImageSize);
        if (actualImageSize == expectedImageSize) {
            for (int i = 0; i < actualImageSize; i++) {
                Assert.assertEquals(expectedDataBuffer.getElem(i), actualDataBuffer.getElem(i));
            }
        }
    }

    @Test(expected= IOException.class)
    public void testOutputGraphicsUnSupportedTypes() throws Exception {
        String actualFile = "actual.pdf";
        g.outputGraphics(actualFile, "pdf");
    }
}
