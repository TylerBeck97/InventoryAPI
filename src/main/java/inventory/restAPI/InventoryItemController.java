package inventory.restAPI;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InventoryItemController {
    private final InventoryItemRepository repository;

    InventoryItemController(InventoryItemRepository repository){
        this.repository = repository;
    }

    @GetMapping("/items")
    List<InventoryItem> all(){
        return repository.findAll();
    }

    @GetMapping("/items/{barcode}")
    InventoryItem getItem(@PathVariable String barcode) {return repository.findByBarcodeNumber(barcode);}

    @PostMapping("/items")
    InventoryItem newItem(@RequestBody InventoryItem newItem){
        if (newItem.getBarcodeNumber() == null)
            return null;

        var item = repository.findByBarcodeNumber(newItem.getBarcodeNumber());
        if (item != null){
            item.addToQuantityByX(newItem.getQuantity());

            //Removes item is its quantity is less than one
            if (item.getQuantity() <= 0) {
                repository.delete(item);
                return item;
            }

            //Update the description of the old item with the new item
            if (!item.getDescription().equals(newItem.getDescription()))
                item.setDescription(newItem.getDescription());

            return repository.save(item);
        }
        return repository.save(newItem);
    }

    @DeleteMapping("/items/{barcode}")
    InventoryItem deleteItem(@PathVariable String barcode){
        var item = repository.findByBarcodeNumber(barcode);
        if (item != null){
            repository.delete(item);
            return item;
        }
        return null;
    }

    @PutMapping("/items/{barcode}")
    InventoryItem modifyItem(@PathVariable String barcode, @RequestBody InventoryItem item){
        if (item.getQuantity() <= 0)
            repository.delete(item);
        else
            repository.save(item);
        return item;
    }
}
