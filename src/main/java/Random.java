import java.util.*;

public class Random extends DFS{
    public Random(String path) {
        super(path);
    }

    public Random() {
        super();
    }

    @Override
    public List<String> algo(Graph g,String curr, String dst, int[] visited, List<String> path){
        System.out.println(path.toString());
        if(dst.equals(curr)){
            return path;
        }

        List<String> unvisitedChild = new ArrayList<>();
        for(String child : g.map.get(curr)){
            if(!(visited[child.charAt(0) - 'a'] == 1)){
                visited[child.charAt(0) - 'a'] = 1;
                unvisitedChild.add(child);
            }
        }

        while(!unvisitedChild.isEmpty()) {
            int size = unvisitedChild.size();
            double i = Math.random() * size;
            String last = unvisitedChild.remove((int)i);
            List<String> newPath = new ArrayList<>(path);
            newPath.add(last);

            List<String> cpath = algo(g, last, dst, visited, newPath);
            if(cpath!=null){
                return cpath;
            }
        }

        return null;
    }
}
