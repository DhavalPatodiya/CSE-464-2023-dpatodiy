import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BFS extends Path {

    public BFS(String path) {
        super(path);
    }

    public BFS() {
        super();
    }

    @Override
    public Path graphSearch(Graph g, String src, String dst, Algorithm algo) {
        System.out.println("BFS");
        if (!(g.map.containsKey(src) && g.map.containsKey(dst))){
            return null;
        }

        Queue<List<String>> q = new ArrayDeque<>();
        pathList.add(src);
        q.offer(pathList);

        while(!q.isEmpty()) {
            pathList = q.poll();
            String last = pathList.get(pathList.size() - 1);
            boolean found = false;

            for (String child : g.map.get(last)) {
                if (dst.equals(child)) {
                    pathList.add(child);
                    found = true;
                    pathFound = pathList;
                    break;
                }
                if (!(visited[child.charAt(0) - 'a'] == 1)) {
                    visited[child.charAt(0) - 'a'] = 1;
                    List<String> newPath = new ArrayList<>(pathList);
                    newPath.add(child);
                    q.offer(newPath);
                }
            }
            if (found) break;
        }

        listToString();
        return pathFound == null ? null : new BFS(sb.toString());
    }
}
