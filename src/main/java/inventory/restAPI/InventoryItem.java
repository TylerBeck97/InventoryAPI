package inventory.restAPI;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class InventoryItem {

    private @Id @GeneratedValue Long id;
    private String barcodeNumber;
    private String description;
    private Integer quantity;

    InventoryItem() {}

    InventoryItem(String barcodeNumber, String description, Integer quantity){
        this.barcodeNumber = barcodeNumber;
        this.description = description;
        this.quantity = quantity;
    }

    //Returns a boolean to let controller know when to delete an entry from the table
    public boolean addToQuantityByX(Integer x){
        quantity += x;
        return quantity > 0;
    }

    public Long getId() {
        return id;
    }

    public boolean setBarcodeNumber(String barcodeNumber){
        if (barcodeNumber.matches("[0-9]+") && barcodeNumber.length() == 12){
            this.barcodeNumber = barcodeNumber;
            return true;
        }
        return false;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public String getBarcodeNumber(){
        return barcodeNumber;
    }

    public String getDescription() {
        return description;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
