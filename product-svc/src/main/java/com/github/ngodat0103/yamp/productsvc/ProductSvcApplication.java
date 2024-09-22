package com.github.ngodat0103.yamp.productsvc;

import com.github.slugify.Slugify;
import java.util.Locale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductSvcApplication {

  @Bean
  Slugify slugify() {
    Locale locale = new Locale("vi", "VN");

    return Slugify.builder().locale(locale).lowerCase(true).build();
  }

  public static void main(String[] args) {
    SpringApplication.run(ProductSvcApplication.class, args);
  }
}
