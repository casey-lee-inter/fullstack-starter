package com.starter.fullstack.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class InventoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  private InventoryDAO inventoryDAO;
  private Inventory inventory;

  private static final String NAME = "TEST";
  private static final String PRODUCT_TYPE = "TESTPRODUCT";
  private static final String ID = "id";

  @Before
  public void setup() throws Throwable {
    this.inventoryDAO = new InventoryDAO(this.mongoTemplate);
  }

  @After
  public void tearDown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }

  @Test
  public void create() throws Throwable {
    this.inventory = new Inventory();
    this.inventory.setName(NAME);
    this.inventory.setProductType(PRODUCT_TYPE);
    this.mockMvc.perform(post("/inventory")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(this.inventory)))
      .andExpect(status().isOk());
    int i = inventoryDAO.findAll().size();
    Assert.assertEquals(1, inventoryDAO.findAll().size());
  }

  @Test
  public void delete_test() throws Throwable {
    this.inventory = new Inventory();
    this.inventory.setName(NAME);
    this.inventory.setProductType(PRODUCT_TYPE);
    String id = this.inventoryDAO.create(this.inventory).getId();
    int size = inventoryDAO.findAll().size();
    this.mockMvc.perform(delete("/inventory")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .param(ID, id))
      .andExpect(status().isOk());

    Assert.assertEquals(size - 1, inventoryDAO.findAll().size());
  }
}
