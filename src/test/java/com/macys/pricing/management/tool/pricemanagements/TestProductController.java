package com.macys.pricing.management.tool.pricemanagements;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macys.pricing.management.tool.pricemanagements.controllers.ProductController;
import com.macys.pricing.management.tool.pricemanagements.entities.Product;
import com.macys.pricing.management.tool.pricemanagements.services.ProductService;
import com.macys.pricing.management.tool.pricemanagements.services.ProductServiceImpl;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class TestProductController {

	@Autowired
	private MockMvc mvc;

	@MockBean
	ProductService productService;

	List<Product> productList = null;
	List<Product> emptyProductList = null;
	Product product = null;

	@Before
	public void setUp() {
		// Product product = new Product(1,100,"product",10,1,"yes","done",0);
		Product product = new Product();
		product.setId(1);
		product.setItemNum(100);
		product.setLocation(100);
		product.setVersion(11);
		product.setItemName("product");
		product.setAnalyticsInfo("successAnalytics");
		product.setMerchantDecision("Yes");
		productList = new ArrayList<Product>();
		productList.add(product);

	}

	@Before
	public void setUpEmptyProductList() {
		List<Product> emptyProductList = null;

		Mockito.when(productService.listAllProducts()).thenReturn(emptyProductList);
	}

	@Before
	public void setUpEmptyProduct() {
		List<Product> emptyProductList = null;

		Mockito.when(productService.listAllProducts()).thenReturn(emptyProductList);
	}

	@Test
	public void test_getList() throws Exception {
		Mockito.when(productService.listAllProducts()).thenReturn(productList);
		mvc.perform(get("/items/{location}/", 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists());
		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());

		System.out.println("Status---->" + content().toString());
		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
	}

	@Test
	public void test_getEmptyList() throws Exception {
		Mockito.when(productService.listAllProducts()).thenReturn(new ArrayList<Product>());
		mvc.perform(get("/items/{location}/", 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isNoContent()).andExpect(jsonPath("$.[*]").doesNotExist());
	}

	@Test
	public void test_fetchProduct() throws Exception {
		Mockito.when(productService.getProductById(1)).thenReturn(setUpProductDetail(1));
		//Pass test condition
		mvc.perform(get("/items/{location}/fetch/{id}", 2, 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists());
		//Fail test condition
		//mvc.perform(get("/items/{location}/fetch/{id}", 2, 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
				//.andExpect(status().is5xxServerError()).andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists());
		System.out.println("Status---->" + content().toString());
	}

	@Test
	public void test_fetchEmptyProduct() throws Exception {
		/*
		 * Mockito.when(productService.getProductById(1)).thenReturn(new Product());
		 * mvc.perform( get("/items/{location}/fetch/{id}",2,1)
		 * .accept(MediaType.APPLICATION_JSON)) .andDo(print())
		 * .andExpect(status().isOk()).
		 * andExpect(MockMvcResultMatchers.jsonPath("$.[*]").doesNotExist());
		 */

		Mockito.when(productService.getProductById(1)).thenThrow(new NoSuchElementException());
		mvc.perform(get("/items/{location}/fetch/{id}", 2, 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isNoContent()).andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[*].location").isEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[*].itemNumber").isEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[*].version").isEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[*].itemName").isEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[*].analyticsInfo").isEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[*].merchantDecision").isEmpty());
		System.out.println("Status---->" + content().toString());
	}

	@Test
	public void test_delete() throws Exception {
		Mockito.when(productService.deleteProduct(1)).thenReturn(true);
		mvc.perform(post("/items/{location}/delete/{id}", 2, 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());

		System.out.println("Status---->" + content().toString());
		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test_deleteFailed() throws Exception {
		Mockito.when(productService.deleteProduct(Integer.valueOf(1))).thenThrow(EmptyResultDataAccessException.class);
		mvc.perform(post("/items/{location}/delete/{id}", 2, 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isNoContent());
		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());

		System.out.println("Status---->" + content().toString());
		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
	}

	@Test
	public void test_saveProduct() throws Exception {
		product = new Product();
		product.setItemNum(100);
		product.setLocation(100);
		product.setVersion(11);
		product.setItemName("product");
		product.setAnalyticsInfo("successAnalytics");
		product.setMerchantDecision("Yes");
		Mockito.when(productService.saveProduct(product)).thenReturn(setUpProductDetail(1));
		ObjectMapper mapper = new ObjectMapper();
		String productJson = mapper.writeValueAsString(product);
		mvc.perform(post("/items/{location}/save/", 1).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(productJson)).andExpect(status().isCreated())
				.andDo(print());

		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());

		System.out.println("Status---->" + content().toString());
		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
	}

	@Test
	public void test_updateProduct() throws Exception {
		product = new Product();
		product.setId(1);
		product.setItemNum(100);
		product.setLocation(100);
		product.setVersion(11);
		product.setItemName("product");
		product.setAnalyticsInfo("successAnalytics");
		product.setMerchantDecision("Yes");
		Mockito.when(productService.saveProduct(product)).thenReturn(setUpProductDetail(1));
		ObjectMapper mapper = new ObjectMapper();
		String productJson = mapper.writeValueAsString(product);
		mvc.perform(put("/items/{location}/update/", 1).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(productJson)).andExpect(status().is2xxSuccessful())
				.andDo(print());

		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());

		System.out.println("Status---->" + content().toString());
		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
		// .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
	}

	/*
	 * @Test public void test_updateProductFailed() throws Exception {
	 *
	 *
	 * product = new Product(); product.setId(null); product.setItemNum(null);
	 * product.setLocation(100); product.setVersion(11);
	 * product.setItemName("product"); product.setAnalyticsInfo("successAnalytics");
	 * product.setMerchantDecision("Yes");
	 *
	 *
	 * Mockito.when(productService.updateProduct((Product)
	 * any(Product.class))).thenThrow(EmptyResultDataAccessException.class);
	 * ObjectMapper mapper = new ObjectMapper(); String productJson =
	 * mapper.writeValueAsString(product);
	 * mvc.perform(put("/items/{location}/update/",1).
	 * accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).
	 * content(productJson)) .andExpect(status().isNotModified()) .andDo(print());
	 *
	 * //.andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").
	 * isNotEmpty());
	 *
	 * System.out.println("Status---->"+content().toString());
	 * //.andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
	 * //.andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").
	 * isNotEmpty()); }
	 */

	private Product setUpProductDetail(Integer id) {
		// Product product = new Product(1,100,"product",10,1,"yes","done",0);
		Product productNew = new Product();
		productNew.setId(1);
		productNew.setItemNum(100);
		productNew.setLocation(100);
		productNew.setVersion(11);
		productNew.setItemName("product");
		productNew.setAnalyticsInfo("successAnalytics");
		productNew.setMerchantDecision("Yes");
		return productNew;
	}

}
