package org.leolee.es.springdatademo;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.jupiter.api.Test;
import org.leolee.es.springdatademo.dao.ProductDao;
import org.leolee.es.springdatademo.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.Optional;

@SpringBootTest
public class SpringDataEsApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ProductDao productDao;

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

    @Test
    public void createDocument() {
        Product product = new Product(10000L, "iphone12", "手机", 8000.00, "http://www.baidu.com/iphone12.jpg");
        Product save = productDao.save(product);
        logger.info("{}", save.toString());

        /*List<Product> productList = new ArrayList<>();
        Iterable<Product> products = productDao.saveAll(productList);*/
    }


    @Test
    public void updateDocumentById() {
        //如果document的id相同，则为update操作
        Product product = new Product(10000L, "iphone11", "手机", 5000.00, "http://www.baidu.com/iphone11.jpg");
        Product save = productDao.save(product);
        logger.info("{}", save.toString());
    }

    @Test
    public void getDocumentById() {
        Optional<Product> obj = productDao.findById(10000L);
        logger.info("{}", obj.get());
    }

    @Test
    public void getAllDocument() {
        Iterable<Product> all = productDao.findAll();
        for (Product product : all) {
            logger.info("{}", product);
        }
    }

    @Test
    public void existDocument() {
        boolean b = productDao.existsById(10000L);
        logger.info("exist this document:{}", b);
    }

    @Test
    public void delDocument() {
        //productDao.deleteAll();
        productDao.deleteById(10000L);
    }

    @Test
    public void searchDocumentByPage() {
        //分页&排序
        Sort idDesc = Sort.by(Sort.Direction.DESC, "id");
        //page-0(页码从0开始) pagesize-5
        PageRequest pageRequest = PageRequest.of(0, 5, idDesc);
        Page<Product> all = productDao.findAll(pageRequest);
        for (Product product : all.getContent()) {
            logger.info("{}", product);
        }
    }

    /**
     * 功能描述: <br>
     * 〈文档搜索〉
     */
    @Test
    public void termQuery() {
        TermQueryBuilder titleQuery = QueryBuilders.termQuery("title", "iphone11");
        Iterable<Product> search = productDao.search(titleQuery);
        for (Product product : search) {
            logger.info("{}", product);
        }

        //分页&排序
        Sort idDesc = Sort.by(Sort.Direction.DESC, "id");
        //page-0(页码从0开始) pagesize-5
        PageRequest pageRequest = PageRequest.of(0, 5, idDesc);
        Page<Product> products = productDao.search(titleQuery, pageRequest);
        for (Product p : products.getContent()) {
            logger.info("{}", p);
        }

        Product product = new Product();
        product.setId(10000L);
        product.setCategory("手");
        Page<Product> products1 = productDao.searchSimilar(product, new String[]{"category"}, pageRequest);
        for (Product p : products1.getContent()) {
            logger.info("{}", p);
        }
    }
}
