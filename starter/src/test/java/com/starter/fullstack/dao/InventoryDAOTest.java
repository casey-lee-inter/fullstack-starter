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
    Assert.assertFalse(this.inventoryDAO.create(inventoryNull).getId() == null);

    Inventory inventoryEx = new Inventory();
    inventoryEx.setId("asdf");
    inventoryEx.setName(NAME);
    inventoryEx.setProductType(PRODUCT_TYPE);
    Assert.assertFalse(this.inventoryDAO.create(inventoryEx).getId().equals("asdf"));
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
  public void deleteInventory() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);

    String id = this.inventoryDAO.create(inventory).getId();
    Assert.assertEquals(2, this.inventoryDAO.findAll().size());
    Assert.assertEquals(id, this.inventoryDAO.delete(id).getId());
    Assert.assertEquals(1, this.inventoryDAO.findAll().size());
  }
}
