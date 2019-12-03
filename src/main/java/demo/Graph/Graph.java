package demo.Graph;

import java.util.HashMap;
import java.util.Iterator;

public interface Graph {
    public void add(String key, HashMap value);
    public void delete(String key);
    public int getSize();
    HashMap getTopGraph();
    Iterator getGraphIterator();
}
