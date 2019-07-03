package com.macys.pricing.management.tool.pricemanagements.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.macys.pricing.management.tool.pricemanagements.entities.Product;
import com.macys.pricing.management.tool.pricemanagements.repositories.ProductRepository;

/**
 * Product service implement.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) throws NoSuchElementException{
        return productRepository.findById(id).get();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    
    @Override
    public Product updateProduct(Product product) throws IllegalArgumentException,EmptyResultDataAccessException {
    	Product productUpdate = null;
    		
		    	productUpdate = productRepository.findById(product.getId()).get();
		    	productUpdate.setAnalyticsInfo(product.getAnalyticsInfo());
		    	productUpdate.setItemName(product.getItemName());
		    	productUpdate.setItemNum(product.getItemNum());
		    	productUpdate.setItemPrice(product.getItemPrice());
		    	productUpdate.setLocation(product.getLocation());
		    	productUpdate.setMerchantDecision(product.getMerchantDecision());
		    	productUpdate.setVersion(product.getVersion());
    		
    		return productRepository.save(productUpdate);
    }

    @Override
    public boolean deleteProduct(Integer id) throws IllegalArgumentException,EmptyResultDataAccessException  {
    	boolean deleteStatus =  false;
    	if(null!=id)  
    	{
    		try    		
        	{
    			productRepository.deleteById(id);
    			deleteStatus =true;
        	}
    		catch(EmptyResultDataAccessException emptyResult)
    		{    			
    			deleteStatus =false;
    			throw emptyResult;
    		}
    		
        	
        	
    	}
    	else
    		throw new IllegalArgumentException();
    	return deleteStatus;
    }

}
