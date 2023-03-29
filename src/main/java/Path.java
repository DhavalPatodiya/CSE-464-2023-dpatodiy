import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Path {
    public String path = null;

    public Path(){

    }

    public Path(String path){
        this.path = path;
    }

    @Override
    public String toString(){
        return path;
    }

    public Path graphSearch( Graph g, String src, String dst){
        if(!(g.map.containsKey(src) && g.map.containsKey(dst)))
            return null;

        List<String> path = new ArrayList<>();
        path.add(src);

        int[] visited = new int[26];

        List<String> pathFound = dfs(g, src, dst, visited, path);

        StringBuilder sb  = new StringBuilder();


        if(pathFound!=null){
            for(String node : pathFound){
                sb.append(node + "->");
            }

            sb = sb.deleteCharAt(sb.length()-1);
            sb = sb.deleteCharAt(sb.length()-1);
        }

        return pathFound == null ? null : new Path(sb.toString());

    }

    public List<String> dfs(Graph g,String curr, String dst, int[] visited, List<String> path){

        for(String child : g.map.get(curr)){
            if(dst.equals(child)){
                path.add(child);
                return path;
            }
            if(!(visited[child.charAt(0) - 'a'] == 1)){
                visited[child.charAt(0) - 'a'] = 1;
                List<String> newPath = new ArrayList<>(path);
                newPath.add(child);
                return dfs(g, child, dst, visited, newPath);
            }
        }

        return null;
    }
}
