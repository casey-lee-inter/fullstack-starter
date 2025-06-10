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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

  private InventoryDAO inventorydao;
  private Inventory inventory;

  @Before
  public void setup() throws Throwable {
    this.inventorydao = new InventoryDAO(this.mongoTemplate);
  }

  @After
  public void tearDown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }

  @Test
  public void create() throws Throwable {
    this.inventory = new Inventory();
    this.inventory.setName("Test");
    this.inventory.setProductType("TestProduct");
    this.mockMvc.perform(post("/inventory/create")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(this.inventory)))
      .andExpect(status().isOk());
    int i = inventorydao.findAll().size();
    Assert.assertEquals(1, inventorydao.findAll().size());
  }

  @Test
  public void delete_test() throws Throwable {
    this.inventory = new Inventory();
    this.inventory.setName("Test");
    this.inventory.setProductType("TestProduct");
    int i = inventorydao.findAll().size();
    String id = this.inventorydao.create(this.inventory).getId();
    int size = inventorydao.findAll().size();
    this.mockMvc.perform(delete("/inventory/delete")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .param("id", id))
      .andExpect(status().isOk());

    Assert.assertEquals(size - 1, inventorydao.findAll().size());
  }
}
