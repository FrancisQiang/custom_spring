package com.lgq.spring.bean.resource;

import java.io.InputStream;

/**
 * @author lgq
 * @date 2019-09-21 19:37
 **/
public interface Resource {

  /**
   * 获取不同资源位置下的配置文件信息
   * @return 配置文件信息
   */
  InputStream getInputStream();

}
