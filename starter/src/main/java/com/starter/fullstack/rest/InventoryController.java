package com.starter.fullstack.rest;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



/**
 * Inventory Controller.
 */
@RestController
public class InventoryController {
  private final InventoryDAO inventoryDAO;

  /**
   * Default Constructor.
   * @param inventoryDAO inventoryDAO.
   */
  public InventoryController(InventoryDAO inventoryDAO) {
    Assert.notNull(inventoryDAO, "Inventory DAO must not be null.");
    this.inventoryDAO = inventoryDAO;
  }

  @PostMapping("/inventory")
  public Inventory create(@RequestBody Inventory inventory) {
    return this.inventoryDAO.create(inventory);
  }

  @DeleteMapping("/inventory")
  public List<Optional<Inventory>> delete(@RequestBody List<String> ids) {
    List<Optional<Inventory>> deletedIds = new ArrayList<Optional<Inventory>>();
    for (String id : ids) {
      deletedIds.add(this.inventoryDAO.delete(id));
    }
    return deletedIds;
  }

  /**
   * Find Products.
   * @return List of Product.
   */
  @GetMapping("/inventory")
  public List<Inventory> findInventories() {
    return this.inventoryDAO.findAll();
  }
}

