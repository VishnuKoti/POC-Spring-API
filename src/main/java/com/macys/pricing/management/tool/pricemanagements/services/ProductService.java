package com.macys.pricing.management.tool.pricemanagements.services;

import java.util.Optional;

import com.macys.pricing.management.tool.pricemanagements.entities.Product;

public interface ProductService {

    Iterable<Product> listAllProducts();

    Product getProductById(Integer id);

    Product saveProduct(Product product);

    boolean deleteProduct(Integer id);

	Product updateProduct(Product product);

}
