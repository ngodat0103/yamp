//package com.github.ngodat0103.yamp.productsvc.unit_test;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.ngodat0103.yamp.productsvc.controller.CategoryController;
//import com.github.ngodat0103.yamp.productsvc.dto.CategoryDto;
//import com.github.ngodat0103.yamp.productsvc.exception.ConflictException;
//import com.github.ngodat0103.yamp.productsvc.service.CategoryService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.UUID;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(value =  CategoryController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@ActiveProfiles("test")
//public class CategoryControllerTest {
//
//    @MockBean
//    private  CategoryService categoryService;
//    @Autowired
//    private MockMvc mockMvc;
//    private final ObjectMapper objectMapper= new ObjectMapper();
//    private CategoryDto categoryDto;
//    @BeforeEach
//    public void setUp() {
//        categoryDto = new CategoryDto();
//        categoryDto.setName("Test Category");
//        categoryDto.setParentCategoryUuid(null);
//    }
//
//    @Test
//    public void testCreateNewCategoryWhenNotConflict() throws Exception {
//        UUID randomUUID = UUID.randomUUID();
//        categoryDto.setUuid(randomUUID);
//        when(categoryService.createCategory(categoryDto, randomUUID)).thenReturn(categoryDto);
//        mockMvc.perform(post("/category")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(categoryDto)))
//                .andExpect(status().isCreated())
////                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value(categoryDto.getName()))
//                .andExpect(jsonPath("$.uuid").exists())
//                .andExpect(jsonPath("$.parentCategoryUuid").exists());
//
//        categoryDto.setUuid(randomUUID);
//    }
//    @Test
//    public void testCreateNewCategoryWhenExist() throws Exception {
//        UUID randomUUID = UUID.randomUUID();
//        when(categoryService.createCategory(categoryDto, randomUUID)).thenThrow(new ConflictException("Category already exists"));
//        mockMvc.perform(post("/category")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(categoryDto)))
//                .andExpect(status().isConflict())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.title").value("Already exists"))
//                .andExpect(jsonPath("$.status").value(409))
//                .andExpect(jsonPath("$.detail").value("Category already exists"));
//
//    }
//}
