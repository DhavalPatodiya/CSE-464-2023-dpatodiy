import java.util.ArrayList;
import java.util.List;

public abstract class Path {
    public String path = null;
    List<String> pathFound = null;
    StringBuilder sb  = new StringBuilder();
    List<String> pathList = new ArrayList<>();
    int[] visited = new int[26];

    public Path() {

    }

    public abstract Path graphSearch(Graph g, String src, String dst, Algorithm algo) ;

    public Path(String path){
        this.path = path;
    }

    public void listToString(){
        if(pathFound!=null){
            for(String node : pathFound){
                sb.append(node + "->");
            }

            sb = sb.deleteCharAt(sb.length()-1);
            sb = sb.deleteCharAt(sb.length()-1);
        }
    }

    @Override
    public String toString(){
        return path;
    }
}
