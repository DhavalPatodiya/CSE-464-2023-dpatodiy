import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class GraphManager {

    Graph g;
    public GraphManager (){
        g = new Graph();
    }

    //Feature 1
    public void parseGraph(String filepath) throws Exception{
        g.parseGraph(filepath);
    }

    @Override
    public String toString(){
        return g.toString();
    }

    // only works for txt file
    public void outputGraph(String filepath) throws Exception {
        String text = g.toString();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write(text);
        writer.close();
    }

    //Feature 2:

    public void addNode(String label){
        g.addNode(label);
    }

    public void removeNode(String label){
        g.removeNode(label);
    }

    public void addNodes(String[] labels){
        for(String label : labels){
            g.addNode(label);
        }
    }

    public void removeNodes(String[] labels){
        for(String label : labels){
            g.removeNode(label);
        }
    }

    // feature 3
    public void addEdge(String srcLabel, String dstLabel){
        g.addEdges(srcLabel, dstLabel);
    }

    public void removeEdge(String srcLabel, String dstLabel){
        g.removeEdges(srcLabel, dstLabel);
    }

    //feature 4
    public void outputDOTGraph(String filepath) throws Exception{
        String text = g.outputDOTGraph();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write(text);
        writer.close();
    }

    public void outputGraphics(String path, String format) throws Exception{
        g.outputGraphics(path, format);
    }

    public int nodeSize(){
        return g.nodesCount();
    }

    public int edgeSize(){
        return g.edgesCount();
    }

    public boolean containsNode(String label){
        return g.containsNode(label);
    }

    public boolean containsEdge(String src, String dst){
        return g.containsEdge(src, dst);
    }

//    public static void main(String args[]) {
//        GraphManager g = new GraphManager();
//        g.parseGraph("input.dot");
//        System.out.println(g.toString());
//        g.outputGraph("text.txt");
//        g.addNode("f");
//        g.outputDOTGraph("expected.dot");
//        g.outputGraphics("expected.png", "png");
//    }

}
