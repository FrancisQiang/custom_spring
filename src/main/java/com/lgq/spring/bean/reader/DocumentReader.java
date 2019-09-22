package com.lgq.spring.bean.reader;

import java.io.InputStream;
import org.dom4j.Document;

/**
 * @author lgq
 * @date 2019-09-21 19:48
 **/
public interface DocumentReader {

  /**
   * 使用SAX解析xml文件
   * @param inputStream 输入流
   * @return document对象
   */
  Document loadDocument(InputStream inputStream);

}
