package com.crtxr.mongodbdemo;

import com.crtxr.mongodbdemo.document.Account;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class MongodbDemoApplicationTests {
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 初始化用户信息
     * 循环添加
     *
     * @author txr
     */
    public void initAccount() {
        for (int i = 1; i <= 5; i++) {
            Account account = new Account();
            account.setName("画玖" + i);
            account.setAge(18 + i);
            account.setWeight(43 + i);
            account.setBirthday(new Date(System.currentTimeMillis() + 2000 * i));
            mongoTemplate.save(account);
        }
    }

    /**
     * 初始化用户信息
     * 批量添加
     *
     * @author txr
     */
    public void initAccountBatch() {
        List<Account> accounts = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Account account = new Account();
            account.setName("七爷" + i);
            account.setAge(19 + i);
            account.setWeight(58 + i);
            account.setBirthday(new Date(System.currentTimeMillis() + 5000 * i));
            accounts.add(account);
        }
        mongoTemplate.insert(accounts, Account.class);
    }

    /**
     * 初始化用户信息
     */
    @Test
    void inits() {
        initAccount();
        initAccountBatch();
    }


    /**
     * 查找用户
     */
    @Test
    void findAccount() {
        Query query = Query.query(Criteria.where("name").is("画玖1"));
        Account account = mongoTemplate.findOne(query, Account.class);
        System.out.println(String.format("通用查询account结果======>>>>>>>>>>%s", account));
        Account byId = mongoTemplate.findById(account.getId(), Account.class);
        System.out.println(String.format("根据Id查询account结果======>>>>>>>>>>%s", byId));
    }

    /**
     * 分页查询用户信息
     */
    @Test
    void findAccountByLimit() {
        Query query = Query.query(Criteria.where("name").regex("七爷"));
        query.skip(2);
        query.limit(2);
        List<Account> accounts = mongoTemplate.find(query, Account.class);
        for (Account account : accounts) {
            System.out.println(String.format("分页结果======>>>>>>>>>>%s", account));
        }
    }

    /**
     * 修改用户信息用户
     */
    @Test
    void updateAccount() {
        //修改第一条     多条请使用updateMulti
        Query query = Query.query(Criteria.where("name").is("画玖1"));
        Update update = new Update();
        //loves集合中如果不存在就往集合中添加元素
        update.addToSet("loves").each("喜欢七爷", "喜欢女扮男装", "喜欢打抱不平", "画玖好像变胖了😒")
                //设置weight为49
                .set("weight", 49);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Account.class);
        System.out.println(String.format("update-result结果======>>>>>>>>>>%s", result));
        Account updateAfter = mongoTemplate.findOne(query, Account.class);
        System.out.println(String.format("修改后的结果======>>>>>>>>>>%s", updateAfter));


    }

    /**
     * 条件删除集合
     */
    @Test
    void deleteByWhere() {
        //找到并删除weight大于等于22并且loves为null的第一条
        //(这里要注意虽然这里实际文档中不存在loves属性,但是{loves:null}的条件是成立的)
        // 删除多条使用findAllAndRemove
        mongoTemplate.findAndRemove(new Query(Criteria.where("weight").gte(22).and("loves").is(null)), Account.class);
        //删除所有
        // mongoTemplate.remove(new Query(), Account.class);
        //mongoTemplate.dropCollection(Account.class);
        //mongoTemplate.dropCollection("txr");
    }
}
