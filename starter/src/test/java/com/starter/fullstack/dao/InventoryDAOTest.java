package com.starter.fullstack.dao;

import com.starter.fullstack.api.Inventory;
import java.util.List;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Test Inventory DAO.
 */
@DataMongoTest
@RunWith(SpringRunner.class)
public class InventoryDAOTest {
  @ClassRule
  public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

  @Resource
  private MongoTemplate mongoTemplate;
  private InventoryDAO inventoryDAO;
  private static final String NAME = "Amber";
  private static final String PRODUCT_TYPE = "hops";
  private static final String ID = "ASDF";
  private static final String NEWNAME = "Bob";
  private static final String NEW_PT = "wine";

  @Before
  public void setup() {
    this.inventoryDAO = new InventoryDAO(this.mongoTemplate);
  }

  @After
  public void tearDown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }


  @Test
  public void createInventory() {
    Inventory inventoryNull = new Inventory();
    inventoryNull.setName(NAME);
    inventoryNull.setProductType(PRODUCT_TYPE);
    Assert.assertNotEquals(null, this.inventoryDAO.create(inventoryNull).getId());

    Inventory inventoryEx = new Inventory();
    inventoryEx.setId(ID);
    inventoryEx.setName(NAME);
    inventoryEx.setProductType(PRODUCT_TYPE);
    Assert.assertNotEquals(ID, this.inventoryDAO.create(inventoryEx).getId());
  }
  /**
   * Test Find All method.
   */
  @Test
  public void findAll() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.mongoTemplate.save(inventory);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertFalse(actualInventory.isEmpty());
  }

  @Test
  public void updateInventory() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    Inventory update_inventory = new Inventory();
    update_inventory.setName(NEWNAME);
    update_inventory.setProductType(NEW_PT);
    String id = this.inventoryDAO.create(inventory).getId();
    this.inventoryDAO.update(id, update_inventory)
            .ifPresent(invName -> Assert.assertEquals(NEWNAME, invName.getName()));

  }

  @Test
  public void deleteInventory() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);

    String id = this.inventoryDAO.create(inventory).getId();
    int size = this.inventoryDAO.findAll().size();
    this.inventoryDAO.delete(id).ifPresent(invID -> Assert.assertEquals(invID.getId(), id));
    Assert.assertEquals(size - 1, this.inventoryDAO.findAll().size());
  }
}
