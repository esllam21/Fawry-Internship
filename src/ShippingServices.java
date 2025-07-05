import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class ShippingServices implements Shippable {
    private List<Shippable> items;
    private String name;
    private double weight;
    
    public ShippingServices() {
        this.items = new ArrayList<>();
    }
    
    public ShippingServices(String name, double weight) {
        this.items = new ArrayList<>();
        this.name = name;
        this.weight = weight;
    }
    
    public void addItem(Shippable item) {
        if (item != null) {
            this.items.add(item);
        }
    }
    public void addItems(List<Shippable> items) {
        if (items != null) {
            this.items.addAll(items);
        }
    }
    public List<Shippable> getItems() {
        return new ArrayList<>(items);
    }
    public void ship() {
        if (items.isEmpty()) {
            System.out.println("No items to ship.");
            return;
        }
        Map<String, ItemData> itemMap = new LinkedHashMap<>();
        double totalWeight = 0;
        for (Shippable item : items) {
            String name = item.getName();
            double itemWeight = item.getWeight();
            if (!itemMap.containsKey(name)) {
                itemMap.put(name, new ItemData(itemWeight, 1));
            } else {
                itemMap.get(name).count++;
            }
            totalWeight += itemWeight;
        }
        System.out.println("\n===== SHIPPING NOTICE =====");
        System.out.println("---------------------------------------------");
        System.out.printf("%-5s %-20s %-15s\n", "Qty", "Item", "Weight");
        System.out.println("---------------------------------------------");
        for (Map.Entry<String, ItemData> entry : itemMap.entrySet()) {
            String name = entry.getKey();
            int count = entry.getValue().count;
            double itemWeight = entry.getValue().weight;
            System.out.printf("%-5d %-20s %-15.0fg\n", 
                    count, name, count * itemWeight * 1000);
        }
        System.out.println("---------------------------------------------");
        System.out.printf("Total package weight: %.2fkg\n", totalWeight);
        System.out.println("=============================================\n");
        items.clear();
    }
    public double getTotalWeight() {
        double totalWeight = 0;
        for (Shippable item : items) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public double getWeight() {
        return weight;
    }

    private static class ItemData {
        double weight;
        int count;
        
        ItemData(double weight, int count) {
            this.weight = weight;
            this.count = count;
        }
    }
}
