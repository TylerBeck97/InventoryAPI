package inventory.restAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/items")
    InventoryItem newItem(@RequestBody InventoryItem newItem){
        var barcodeRepository = repository.findByBarcodeNumber(newItem.getBarcodeNumber());
        if (!barcodeRepository.isEmpty()){
            for (InventoryItem item: barcodeRepository) {
                item.addToQuantityByX(newItem.getQuantity());

                //Update the description of the old item with the new item
                if (!item.getDescription().equals(newItem.getDescription()))
                    item.setDescription(newItem.getDescription());

                return repository.save(item);
            }
        }
        return repository.save(newItem);
    }
}
