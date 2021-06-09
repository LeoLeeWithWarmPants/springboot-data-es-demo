package org.leolee.es.springdatademo;

import org.junit.jupiter.api.Test;
import org.leolee.es.springdatademo.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@SpringBootTest
public class SpringDataEsApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void contextLoads() {
        logger.info("111");
    }

    @Test
    public void createIndex() {
        //创建索引，spring-boot-starter-data-elasticsearch会扫描document实体中关联的索引，如果没有该索引，会自动创建
        logger.info("create index");
    }


    @Test
    public void delIndex() {
        boolean s = elasticsearchRestTemplate.deleteIndex(Product.class);
        logger.info("delete index by id:{}", s);
    }


}
