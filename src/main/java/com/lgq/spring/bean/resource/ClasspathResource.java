package com.lgq.spring.bean.resource;

import java.io.InputStream;

/**
 * @author lgq
 * @date 2019-09-21 19:40
 **/
public class ClasspathResource implements Resource {

  private String path;

  public ClasspathResource(String path) {
    this.path = path;
  }

  public InputStream getInputStream() {
    if (path == null || "".equals(path)) {
      return null;
    }
    return this.getClass().getClassLoader().getResourceAsStream(path);
  }
}
