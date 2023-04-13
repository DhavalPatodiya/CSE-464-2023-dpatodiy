import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BFS extends Search{

    public BFS(String path) {
        super(path);
    }

    public BFS() {
        super();
    }

    @Override
    public Search graphSearch(Graph g, String src, String dst, Algorithm algo) {
        List<String> pathFound = null;
        StringBuilder sb  = new StringBuilder();
        Queue<List<String>> q = new ArrayDeque<>();
        List<String> path = new ArrayList<>();
        int[] visited = new int[26];

        if (!(g.map.containsKey(src) && g.map.containsKey(dst))){
            return null;
        }

        path.add(src);
        q.offer(path);

        while(!q.isEmpty()) {
            path = q.poll();
            String last = path.get(path.size() - 1);
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

        if(pathFound!=null){
            for(String node : pathFound){
                sb.append(node + "->");
            }

            sb = sb.deleteCharAt(sb.length()-1);
            sb = sb.deleteCharAt(sb.length()-1);
        }
        return pathFound == null ? null : new BFS(sb.toString());

    }

}
