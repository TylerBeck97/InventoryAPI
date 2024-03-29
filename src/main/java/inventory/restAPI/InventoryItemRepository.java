package inventory.restAPI;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    InventoryItem findByBarcodeNumber(String barcodeNumber);
}
