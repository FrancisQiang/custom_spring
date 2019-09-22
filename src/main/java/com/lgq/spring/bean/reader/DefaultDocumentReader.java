package com.lgq.spring.bean.reader;

import java.io.InputStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * @author lgq
 * @date 2019-09-21 19:53
 **/
public class DefaultDocumentReader implements DocumentReader {

  public Document loadDocument(InputStream inputStream) {

    Document document = null;
    try {
      SAXReader saxReader = new SAXReader();
      document = saxReader.read(inputStream);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    return document;
  }

}
