package com.ecommerce.api.bootstrap;

import com.ecommerce.api.model.Category;
import com.ecommerce.api.model.Product;
import com.ecommerce.api.model.SubCategory;
import com.ecommerce.api.repository.CategoryRepository;
import com.ecommerce.api.repository.ProductRepository;
import com.ecommerce.api.repository.SubCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductRepository productRepository;

    private final Random random = new Random();

    // Curated Unsplash Image URLs by category theme to ensure they work and look good
    private final Map<String, List<String>> categoryImages = new HashMap<>();

    public DataSeeder(CategoryRepository categoryRepository, 
                      SubCategoryRepository subCategoryRepository, 
                      ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.productRepository = productRepository;
        initializeImages();
    }

    private void initializeImages() {
        // Electronics
        categoryImages.put("Electronics", Arrays.asList(
            "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?auto=format&fit=crop&w=800&q=80", // Laptop
            "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=800&q=80", // Headphones
            "https://images.unsplash.com/photo-1526738549149-8e07eca6c147?auto=format&fit=crop&w=800&q=80", // Smartwatch
            "https://images.unsplash.com/photo-1585565804112-f201f68c48b4?auto=format&fit=crop&w=800&q=80", // Smartphone
            "https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=800&q=80"  // Watch
        ));

        // Fashion
        categoryImages.put("Fashion", Arrays.asList(
            "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?auto=format&fit=crop&w=800&q=80", // Woman fashion
            "https://images.unsplash.com/photo-1552374196-1ab2a1c593e8?auto=format&fit=crop&w=800&q=80", // Mens fashion
            "https://images.unsplash.com/photo-1483985988355-763728e1935b?auto=format&fit=crop&w=800&q=80", // Shopping
            "https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=800&q=80", // Red shoes
            "https://images.unsplash.com/photo-1469334031218-e382a71b716b?auto=format&fit=crop&w=800&q=80"  // Clothing
        ));

        // Home
        categoryImages.put("Home & Kitchen", Arrays.asList(
            "https://images.unsplash.com/photo-1556911220-e15b29be8c8f?auto=format&fit=crop&w=800&q=80", // Kitchen
            "https://images.unsplash.com/photo-1513694203232-719a280e022f?auto=format&fit=crop&w=800&q=80", // Living room
            "https://images.unsplash.com/photo-1583847661884-db8e516fc694?auto=format&fit=crop&w=800&q=80", // Furniture
            "https://images.unsplash.com/photo-1522758971460-1d21eed7dc1d?auto=format&fit=crop&w=800&q=80", // Decor
            "https://images.unsplash.com/photo-1517649763962-0c623066013b?auto=format&fit=crop&w=800&q=80"  // Kitchen tools
        ));

        // Books
        categoryImages.put("Books", Arrays.asList(
            "https://images.unsplash.com/photo-1495446815901-a7297e633e8d?auto=format&fit=crop&w=800&q=80", // Books
            "https://images.unsplash.com/photo-1544947950-fa07a98d237f?auto=format&fit=crop&w=800&q=80", // Reading
            "https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=800&q=80", // Book cover
            "https://images.unsplash.com/photo-1589829085413-56de8ae18c73?auto=format&fit=crop&w=800&q=80", // Open book
            "https://images.unsplash.com/photo-1491841550275-ad7854e35ca6?auto=format&fit=crop&w=800&q=80"  // Library
        ));
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            seedCategories();
        }
    }

    private void seedCategories() {
        createCategoryWithSubCategories("Electronics", Arrays.asList("Laptops", "Smartphones", "Cameras", "Audio", "Accessories", "Gaming"));
        createCategoryWithSubCategories("Fashion", Arrays.asList("Men's Clothing", "Women's Clothing", "Kids", "Shoes", "Watches", "Jewelry"));
        createCategoryWithSubCategories("Home & Kitchen", Arrays.asList("Furniture", "Decor", "Kitchenware", "Bedding", "Lighting", "Storage"));
        createCategoryWithSubCategories("Books", Arrays.asList("Fiction", "Non-fiction", "Sci-Fi", "Biography", "Children", "Mystery"));
    }

    private void createCategoryWithSubCategories(String categoryName, List<String> subCategoryNames) {
        Category category = new Category();
        category.setName(categoryName);
        category = categoryRepository.save(category);

        for (String subName : subCategoryNames) {
            SubCategory subCategory = new SubCategory();
            subCategory.setName(subName);
            subCategory.setCategory(category);
            subCategory = subCategoryRepository.save(subCategory);

            generateProductsForSubCategory(subCategory, categoryName);
        }
    }

    private void generateProductsForSubCategory(SubCategory subCategory, String parentCategoryName) {
        int numProducts = 8 + random.nextInt(3); // 8 to 10 products

        for (int i = 1; i <= numProducts; i++) {
            Product product = new Product();
            product.setTitle(subCategory.getName() + " " + i + " - Premium Edition");
            product.setDescription("Experience the best quality with our " + subCategory.getName() + " " + i + ". This product features state-of-the-art design and premium materials suitable for all your needs. " +
                    "Perfect for daily use and highly durable. Order now and enjoy the excellence of " + parentCategoryName + ".");
            
            double price = 20.0 + (500.0 - 20.0) * random.nextDouble();
            product.setPrice(BigDecimal.valueOf(price).setScale(2, BigDecimal.ROUND_HALF_UP));
            
            if (random.nextBoolean()) {
                double discount = price * 0.85;
                product.setDiscountPrice(BigDecimal.valueOf(discount).setScale(2, BigDecimal.ROUND_HALF_UP));
            }

            product.setSubCategory(subCategory);
            product.setImages(getRandomImages(parentCategoryName, 3)); // 3 images per product

            productRepository.save(product);
        }
    }

    private List<String> getRandomImages(String categoryName, int count) {
        List<String> availableImages = categoryImages.getOrDefault(categoryName, categoryImages.get("Electronics"));
        List<String> selectedImages = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            selectedImages.add(availableImages.get(random.nextInt(availableImages.size())));
        }
        return selectedImages;
    }
}

