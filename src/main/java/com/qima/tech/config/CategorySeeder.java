package com.qima.tech.config;

import com.qima.tech.entities.Category;
import com.qima.tech.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CategorySeeder {

    @Bean
    CommandLineRunner initDatabase(CategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                List<Category> categories = List.of(
                        new Category(null, "Electronics"),
                        new Category(null, "Books"),
                        new Category(null, "Clothing"),
                        new Category(null, "Home & Kitchen"),
                        new Category(null, "Sports"),
                        new Category(null, "Beauty & Health"),
                        new Category(null, "Toys"),
                        new Category(null, "Automotive"),
                        new Category(null, "Grocery"),
                        new Category(null, "Music")
                );

                categoryRepository.saveAll(categories);
                System.out.println("Inserted 10 categories.");
            }
        };
    }
}
