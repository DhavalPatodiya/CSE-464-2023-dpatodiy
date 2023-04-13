import java.util.List;

public abstract class Search {
    public String path = null;

    public Search() {

    }

    public abstract Search graphSearch( Graph g, String src, String dst, Algorithm algo) ;

    public Search(String path){
        this.path = path;
    }

    @Override
    public String toString(){
        return path;
    }
}
