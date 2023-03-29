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

    public Path graphSearch( Graph g, String src, String dst, Algorithm algo){
        if(!(g.map.containsKey(src) && g.map.containsKey(dst)))
            return null;
        List<String> pathFound = null;
        if(algo.BFS.toString().equals("BFS")) {
            pathFound = bfs(g, src, dst);
        }
        else if(algo.DFS.toString().equals("DFS")){
            pathFound = dfs(g, src, dst);
        }else{
            return null;
        }

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

    public List<String> bfs(Graph g, String src, String dst){
        Queue<List<String>> q = new ArrayDeque<>();
        List<String> pathFound = null;
        List<String> path = new ArrayList<>();
        path.add(src);
        q.offer(path);
        int[] visited = new int[26];

        while(!q.isEmpty()) {
            path = q.poll();
            String last = path.get(path.size() - 1);
            // System.out.println(last+" "+ dst);
            boolean found = false;

            for (String child : g.map.get(last)) {
                if (dst.equals(child)) {
                    path.add(child);
                    found = true;
                    pathFound = path;
                    break;
                }
                if (!(visited[child.charAt(0) - 'a'] == 1)) {
                    visited[child.charAt(0) - 'a'] = 1;
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(child);
                    q.offer(newPath);
                }

            }

            if (found) break;
        }
        return pathFound;
    }

    public List<String> dfs(Graph g, String src, String dst){
        List<String> path = new ArrayList<>();
        path.add(src);

        int[] visited = new int[26];

        return dfs(g, src, dst, visited, path);
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
