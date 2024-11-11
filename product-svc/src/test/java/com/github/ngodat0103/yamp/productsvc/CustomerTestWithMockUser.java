package com.github.ngodat0103.yamp.productsvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Test
@WithMockUser(username = "1a35d863-0cd9-4bc1-8cc4-f4cddca97721")
public @interface CustomerTestWithMockUser {}
