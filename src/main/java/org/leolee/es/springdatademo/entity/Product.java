package org.leolee.es.springdatademo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @ClassName Product
 * @Description: TODO
 * @Author LeoLee
 * @Date 2021/6/9
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
/**
 * 〈shards分片数量，replicas副本数量〉
 */
@Document(indexName = "product", shards = 1, replicas = 1)
public class Product {

    @Id
    private Long id;

    /**
     * 〈analyzer为分词器〉
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    /**
     * 〈FieldType.Keyword代表不分词〉
     */
    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Keyword)
    private String img;
}
