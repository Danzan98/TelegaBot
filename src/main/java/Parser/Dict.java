package Parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dict {
    public Dict() {}

    private HashMap<String, Integer> Items = new HashMap<>();

    public void addItem(String link, Integer price){
        Items.put(link, price);
    }

    public Integer getPriceOfItem(String link){
        return Items.get(link);
    }

    public void deleteItem(String link){
        Items.remove(link);
    }

    public Set<Map.Entry<String, Integer>> getAllItems(){
        return Items.entrySet();
    }

    public boolean existItem(String link){
        return Items.containsKey(link);
    }
}
