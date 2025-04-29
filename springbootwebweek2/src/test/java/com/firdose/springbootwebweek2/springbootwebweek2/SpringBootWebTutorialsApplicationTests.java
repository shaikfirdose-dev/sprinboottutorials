package com.firdose.springbootwebweek2.springbootwebweek2;

import com.firdose.springbootwebweek2.springbootwebweek2.entity.ProductEntity;
import com.firdose.springbootwebweek2.springbootwebweek2.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class SpringBootWebTutorialsApplicationTests {
	@Autowired
	ProductRepository productRepository;



    @Test
	void contextLoads() {
		System.out.println("Hello testing");
	}

	@Test
	void testFindProductByTitle(){
		Optional<ProductEntity> findProduct = productRepository.findByTitle("Pepsi");
		System.out.println(findProduct.get());

	}

	@Test
	void testMethod1(){
		List<ProductEntity> products = productRepository.findByPriceGreaterThan(15.0);
		System.out.println(products);
	}

	@Test
	void testMethod2(){
		Optional<ProductEntity> product = productRepository.findByTitleAndPrice("Mazza", 16.4);
		System.out.println(product);
	}


	@Test
	void testMethod3(){
		List<ProductEntity> product = productRepository.findByPriceBetween(15.0, 25.60);
		System.out.println(product);
	}

	@Test
	void testMethod4(){
		List<ProductEntity> products = productRepository.findByCreatedAtIsNull();
		System.out.println(products);
	}

	@Test
	void testMethod5(){
		List<ProductEntity> products = productRepository.findByTitleLike("%Cola%");
		System.out.println(products);
	}

	@Test
	void testMethod6(){
		List<ProductEntity> products = productRepository.findByTitleStartingWithIgnoreCase("PEp");
		System.out.println(products);
	}

	@Test
	void testMethod7(){
		List<ProductEntity> products = productRepository.findBySkuOrderByPrice("PARLE");
		System.out.println(products);
	}

	@Test
	void testMethod(){
		productRepository.updateAvailableStatusBySku(true, "PARLE");
	}



}
