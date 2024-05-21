package de.cimtag.rateyourbooks.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/books.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class BookControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @Order(1)
  void testSearchAllBooks() throws Exception {
    mockMvc.perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(5)));
  }

  @Test
  @Order(1)
  void testSearchBooksByTitleBlank() throws Exception {
    mockMvc.perform(get("/api/books").param("title", " "))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(5)));
  }

  @Test
  @Order(1)
  void testSearchBooksByAuthorBlank() throws Exception {
    mockMvc.perform(get("/api/books").param("author", " "))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(5)));
  }

  @Test
  @Order(1)
  void testSearchBooksByTitle() throws Exception {
    mockMvc.perform(get("/api/books").param("title", "Utopien für Realisten"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title", is("Utopien für Realisten")))
        .andExpect(jsonPath("$[0].author", is("Rutger Bregman")));
  }

  @Test
  @Order(1)
  void testSearchBooksByAuthor() throws Exception {
    mockMvc.perform(get("/api/books").param("author", "Rutger Bregman"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title", is("Im Grunde Gut")))
        .andExpect(jsonPath("$[0].author", is("Rutger Bregman")))
        .andExpect(jsonPath("$[1].title", is("Utopien für Realisten")))
        .andExpect(jsonPath("$[1].author", is("Rutger Bregman")));
  }

  @Test
  @Order(1)
  void testSearchBooksByTitleAndAuthor() throws Exception {
    mockMvc.perform(get("/api/books").param("title", "Im Grunde Gut").param("author", "Rutger Bregman"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title", is("Im Grunde Gut")))
        .andExpect(jsonPath("$[0].author", is("Rutger Bregman")));
  }

  @Test
  @Order(1)
  void testFindBookById() throws Exception {
    mockMvc.perform(get("/api/books/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is("Fourth Wing")))
        .andExpect(jsonPath("$.author", is("Rebecca Yarros")));
  }

  @Test
  @Order(2)
  void testCreateNewBook() throws Exception {
    mockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"Co-Intelligence\", \"author\":\"Ethan Mollick\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(6)))
        .andExpect(jsonPath("$.title", is("Co-Intelligence")))
        .andExpect(jsonPath("$.author", is("Ethan Mollick")));
  }

  @Test
  @Order(3)
  void testUpdateBook() throws Exception {
    mockMvc.perform(put("/api/books/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"Updated Book\", \"author\":\"Updated Author\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is("Updated Book")))
        .andExpect(jsonPath("$.author", is("Updated Author")));
  }


  @Test
  @Order(4)
  void testDeleteBook() throws Exception {
    mockMvc.perform(delete("/api/books/{id}", 1L))
        .andExpect(status().isOk());

    assertThrows(Exception.class, () -> mockMvc
        .perform(get("/api/books/{id}", 1L))
    );
  }
}
