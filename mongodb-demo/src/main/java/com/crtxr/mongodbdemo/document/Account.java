package com.crtxr.mongodbdemo.document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * 把一个java类声明为mongodb的文档，可以通过collection参数指定这个类对应的文档。
 * 若未加 @Document ，该 bean save 到 mongo 的 txr collection
 * 若添加 @Document ，则 save 到 account collection
 */
@Document(collection = "txr")
@Getter
@Setter
@ToString
public class Account {
    /**
     * @Id 主键，不可重复，自带索引，可以在定义的列名上标注，需要自己生成并维护不重复的约束。
     * 如果自己不设置@Id主键，mongo会自动生成一个唯一主键，并且插入时效率远高于自己设置主键。原因可参考上一篇mongo和mysql的性能对比。
     * 在实际业务中不建议自己设置主键，应交给mongo自己生成，自己可以设置一个业务id，如int型字段，用自己设置的业务id来维护相关联的表。
     */
    @Id
    private String id;
    /**
     * @Field 代表一个字段，可以不加，不加的话默认以参数名为列名。
     */
    @Field("name")
    private String name;
    @Field("age")
    private int age;
    @Field("weight")
    private double weight;
    @Field("loves")
    private List<String> loves;
    @Field("birthday")
    private Date birthday;
}
