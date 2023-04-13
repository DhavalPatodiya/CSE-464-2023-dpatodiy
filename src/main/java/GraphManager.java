import java.io.*;

public class GraphManager {

    Graph graph;
    Path path;
    public GraphManager (){
        graph = new Graph();
        path = new Path();
    }

    //region Feature1
    public void parseGraph(String filepath) throws Exception{
        graph.parseGraph(filepath);
    }

    @Override
    public String toString(){
        return graph.toString();
    }

    // only works for txt file
    public void outputGraph(String filepath) throws Exception {
        String graphText = graph.toString();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write(graphText);
        writer.close();
    }
    //endregion

    //region Feature2
    public void addNode(String label){
        graph.addNode(label);
    }

    public void removeNode(String label){
        graph.removeNode(label);
    }

    public void addNodes(String[] labels){
        for(String label : labels){
            graph.addNode(label);
        }
    }

    public void removeNodes(String[] labels){
        for(String label : labels){
            graph.removeNode(label);
        }
    }
    //endregion

    //region Feature3
    public void addEdge(String srcLabel, String dstLabel){
        graph.addEdges(srcLabel, dstLabel);
    }

    public void removeEdge(String srcLabel, String dstLabel){
        graph.removeEdges(srcLabel, dstLabel);
    }
    //endregion

    //region Feature4
    public void outputDOTGraph(String filepath) throws Exception{
        String graphText = graph.outputDOTGraph();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write(graphText);
        writer.close();
    }

    public void outputGraphics(String path, String format) throws Exception{
        graph.outputGraphics(path, format);
    }
    //endregion

    //region SupportedMethods
    public int nodeSize(){
        return graph.nodesCount();
    }

    public int edgeSize(){
        return graph.edgesCount();
    }

    public boolean containsNode(String label){
        return graph.containsNode(label);
    }

    public boolean containsEdge(String src, String dst){
        return graph.containsEdge(src, dst);
    }
    //endregion

    //region GraphSearch
    public Path graphSearch(String src, String dst, Algorithm algo) throws Exception{
        /*Search path = null;

        if(algo.toString().equals("BFS")){
            path = new BFS();
        }else if(algo.toString().equals("DFS")){
            path = new DFS();
        }else{
            return null;
        }*/
        return path.graphSearch(graph, src, dst, algo);
    }
    //endregion
}
