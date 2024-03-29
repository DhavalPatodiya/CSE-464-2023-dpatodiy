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
    GraphManager randomg;

    @Before
    public void setup() throws Exception {
        g = new GraphManager();
        randomg = new GraphManager();
        g.parseGraph("input.dot");
        randomg.parseGraph("randomsearch.dot");
        g.outputGraphics("expected.jpg", "jpg");
        g.outputGraphics("expected.png", "png");
    }

    // a-d poth exists
    @Test
    public void bfsPathFound() throws Exception{
        String expected = "a->b->c->d";
        Path actual = g.graphSearch("a", "d", Algorithm.BFS);
        Assert.assertTrue(expected.equals(actual.toString()));
    }

    // a-h poth exists
    @Test
    public void dfsPathFound() throws Exception{
        String expected = "a->b->c->d";
        Path actual = g.graphSearch("a", "d", Algorithm.DFS);
        Assert.assertTrue(expected.equals(actual.toString()));
    }

    // a-c poth exists
    @Test
    public void randomSearchPathFound() throws Exception{
        Path actual = randomg.graphSearch("a", "c", Algorithm.RANDOM);
        System.out.println(actual.toString());
    }

    //from a to a there is no path like a->b->a. there is no path to return to a once you leave a
    @Test
    public void bfsPathNotFound() throws Exception{
        Path actual = g.graphSearch("a", "a", Algorithm.BFS);
        Assert.assertNull(actual);
    }

    // a-f path doesn't exists as f doesn't exists.
    @Test
    public void bfsPathNotFoundNode() throws Exception{
        Path actual = g.graphSearch("a", "f", Algorithm.BFS);
        Assert.assertNull(actual);
    }

    //b-e path exists
    @Test
    public void dfs() throws Exception{
        String expected = "b->c->d->e";
        Path actual = g.graphSearch("b", "e", Algorithm.DFS);
        Assert.assertEquals(expected, actual.toString());
    }

    //from a to a there is no path like a->b->a. there is no path to return to a once you leave a
    @Test
    public void dfsPathNotFound() throws Exception{
        Path actual = g.graphSearch("a", "a", Algorithm.DFS);
        Assert.assertNull(actual);
    }

    // a-g path doesn't exists as g doesn't exists.
    @Test
    public void dfsPathNodeNotFound() throws Exception{
        Path actual = g.graphSearch("a", "g", Algorithm.DFS);
        Assert.assertNull(actual);
    }

    //IllegalArgumentException thrown when algo is other than BFS or DFS
    @Test(expected= IllegalArgumentException.class)
    public void invalidEnumName() throws Exception{
        g.graphSearch("b", "e", Algorithm.valueOf("DMS"));
    }

    //NullPointerException thrown when algo is null
    @Test(expected= NullPointerException.class)
    public void algoNameIsNull() throws Exception{
        g.graphSearch("b", "e", null);
    }

    // parseGraph() success
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

    // toString() success
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

    // outputGraph() success
    @Test
    public void testOutputGraph() throws Exception{
        String expectedFile = "expected.txt";
        String actualFile = "actual.txt";
        String expected = null, actual = null;

        g.addEdge("e", "a");
        g.outputGraph("actual.txt");

        expected = Files.readString(Paths.get(expectedFile));
        actual = Files.readString(Paths.get(actualFile));
        Assert.assertEquals(expected, actual);
    }

    // AddNode() success
    @Test
    public void testAddNode(){
        g.addNode("f");
        Assert.assertEquals(6, g.nodeSize());
        Assert.assertTrue(g.containsNode("f"));
    }

    // RemoveNode() success
    @Test
    public void testRemoveNode(){
        g.removeNode("e");
        Assert.assertEquals(4, g.nodeSize());
        Assert.assertFalse(g.containsNode("e"));
    }

    // AddNodes() success
    @Test
    public void testAddNodes(){
        String nodes[] = new String[]{"f", "g"};

        g.addNodes(nodes);
        Assert.assertEquals(7, g.nodeSize());
        Assert.assertTrue(g.containsNode("f"));
        Assert.assertTrue(g.containsNode("g"));
    }

    // RemoveNodes() success
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

    // AddEdge() success
    @Test
    public void testAddEdge(){
        g.addEdge("e", "a");
        Assert.assertEquals(5, g.nodeSize());
        Assert.assertTrue(g.containsEdge("e", "a"));
        Assert.assertEquals(6, g.edgeSize());
    }

    // RemoveEdge() success
    @Test
    public void testRemoveEdge(){
        g.addEdge("e", "a");
        Assert.assertEquals(6, g.edgeSize());
        Assert.assertTrue(g.containsEdge("e", "a"));

        g.removeEdge("e", "a");
        Assert.assertEquals(5, g.edgeSize());
        Assert.assertFalse(g.containsEdge("e", "a"));
    }

    // outputDOTGraph() success
    @Test
    public void testOutputDOTGraph() throws Exception{
        String expectedFile = "expected.dot";
        String actualFile = "actual.dot";
        String expected = null, actual = null;

        g.addNode("f");
        g.outputDOTGraph(actualFile);

        expected = Files.readString(Paths.get(expectedFile));
        actual = Files.readString(Paths.get(actualFile));
        Assert.assertEquals(expected, actual);
    }

    // outputDOTGraphicPNG() success
    @Test
    public void testOutputGraphicsPNG() throws Exception{
        String expectedFile = "expected.png";
        String actualFile = "actual.png";

        g.outputGraphics(actualFile, "png");

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

    // outputDOTGraphicJPG() success
    @Test
    public void testOutputGraphicsJPG() throws Exception {
        String expectedFile = "expected.jpg";
        String actualFile = "actual.jpg";

        g.outputGraphics(actualFile, "jpg");

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

    // outputDOTGraphics() thriws error.
    @Test(expected= IOException.class)
    public void testOutputGraphicsUnSupportedTypes() throws Exception {
        String actualFile = "actual.pdf";
        g.outputGraphics(actualFile, "pdf");
    }
}
