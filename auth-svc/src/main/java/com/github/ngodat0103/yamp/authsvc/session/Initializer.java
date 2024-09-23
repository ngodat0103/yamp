package com.github.ngodat0103.yamp.authsvc.session;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class Initializer extends AbstractHttpSessionApplicationInitializer {
  public Initializer() {
    super(SessionConfiguration.class);
  }
}
