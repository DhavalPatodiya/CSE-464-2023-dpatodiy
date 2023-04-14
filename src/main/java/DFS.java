import java.util.ArrayList;
import java.util.List;

public class DFS extends Path {
    public DFS(String path) {
        super(path);
    }

    public DFS() {
        super();
    }

    @Override
    public Path graphSearch(Graph g, String src, String dst, Algorithm algo) {
        System.out.println("DFS");
        if (!(g.map.containsKey(src) && g.map.containsKey(dst))){
            return null;
        }

        pathList.add(src);
        pathFound = algo(g, src, dst, visited, pathList);

        listToString();
        return pathFound == null ? null : new DFS(sb.toString());
    }

    public List<String> algo(Graph g,String curr, String dst, int[] visited, List<String> path){
        for(String child : g.map.get(curr)){
            if(dst.equals(child)){
                path.add(child);
                return path;
            }
            if(!(visited[child.charAt(0) - 'a'] == 1)){
                visited[child.charAt(0) - 'a'] = 1;
                List<String> newPath = new ArrayList<>(path);
                newPath.add(child);

                List<String> cpath = algo(g, child, dst, visited, newPath);
                if(cpath!=null){
                    return cpath;
                }
            }
        }

        return null;
    }
}
