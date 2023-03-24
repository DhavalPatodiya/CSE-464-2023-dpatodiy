import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {

    public HashMap<String, List<String>> map;

    public Graph(){
        map = new HashMap<>();
    }

    public void parseGraph(String filepath) throws Exception{
        String g = null;
        InputStream dot = new FileInputStream(filepath);
        g = new Parser().read(dot).toString();
        dot.close();


        String[] gString = g.split("\n");

        for(int i=1; i<gString.length-1; i++){
            String[] nodes = gString[i].split("->");
            String src = nodes[0].trim().substring(1, 2);
            addNode(src);
            if(nodes.length > 1) {
                String dst = nodes[1].trim().substring(1, 2);
                addNode(dst);
                addEdges(src, dst);
            }
        }
    }

    public void addNode(String label){
        if(!map.containsKey(label)) {
            map.put(label, new ArrayList<>());
        }
    }

    public void removeNode(String label){
        if(map.containsKey(label)){
            map.remove(label);
        }

        for(String key : map.keySet()){
            if(map.get(key).contains(label)){
                map.get(key).remove(label);
            }
        }
    }

    public void addEdges(String src, String dst){
        if(map.containsKey(src) && map.containsKey(dst)){
            map.get(src).add(dst);
        }
    }

    public void removeEdges(String src, String dst){
        if(map.containsKey(src) && map.get(src).contains(dst)){
            map.get(src).remove(dst);
        }
    }

    public int nodesCount(){
        return map.size();
    }

    public int edgesCount(){
        int count = 0;

        for(String key : map.keySet()){
            count += map.get(key).size();
        }

        return count;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Number of nodes in a graph : " + String.valueOf(nodesCount()));
        sb.append("\n");
        sb.append("Number of edges in a graph : " + String.valueOf(edgesCount()));

        for(String key : map.keySet()){
            if(map.get(key).size() == 0){
                sb.append("\n");
                sb.append(key);
            }
            for(String dst : map.get(key)) {
                sb.append("\n");
                sb.append(key + " -> " + dst);
            }
        }
        return new String(sb);
    }

    public String outputDOTGraph(){
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {");


        for(String key : map.keySet()){
            if(map.get(key).size() == 0){
                sb.append("\n");
                sb.append(key);
            }
            for(String dst : map.get(key)) {
                sb.append("\n");
                sb.append(key + " -> " + dst);
            }
        }
        sb.append("\n");
        sb.append("}");
        return new String(sb);
    }

    public void outputGraphics(String path, String format) throws Exception{
        String sgraph = outputDOTGraph();
        MutableGraph graph = null;
        InputStream dot = new ByteArrayInputStream(sgraph.getBytes());
        graph = new Parser().read(dot);
        dot.close();

        if(format.equals("png")){
            Graphviz.fromGraph(graph).render(Format.PNG).toFile(new File(path));
        }else if(format.equals("jpg")){
            BufferedImage img = Graphviz.fromGraph(graph).render(Format.PNG).toImage();
            BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
            newImage.createGraphics().drawImage( img, 0, 0, Color.BLACK, null);

            File outputfile = new File(path);
            ImageIO.write(newImage, "jpg", outputfile);
        }else{
            throw new IOException("Unsupported format");
        }

    }

    public boolean containsNode(String label){
        return map.containsKey(label);
    }

    public boolean containsEdge(String src, String dst){
        return (map.containsKey(src) && map.get(src).contains(dst));
    }
}
