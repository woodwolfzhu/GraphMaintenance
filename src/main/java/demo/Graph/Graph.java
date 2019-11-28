package demo.Graph;

import java.util.HashMap;

public interface Graph {
    public void add(String key, HashMap value);
    public void delete(String key);
    public int getSize();
}
