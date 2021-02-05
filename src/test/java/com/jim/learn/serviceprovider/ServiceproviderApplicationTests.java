package com.jim.learn.serviceprovider;

import com.jim.learn.serviceprovider.po.User;
import org.drools.core.base.RuleNameEndsWithAgendaFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceproviderApplicationTests {

    @Autowired
    KieSession kieSession;

//    @Test
//    public void contextLoads() {
//    }

    @Test
    public void testHelloWord() {
        kieSession.fireAllRules();
    }

    @Test
    public void testUser() {
        User user=new User("张三",18);
        kieSession.insert(user);
        kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter("user"));
        System.err.println("规则执行完毕后张三变为了："+user.getName());
    }

    @Test
    public void testContains(){

        String name = "张三";
        User user=new User("张三",18);
        kieSession.insert(name);
        kieSession.insert(user);
        kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter("contains"));
    }

    @Test
    public void memberOf(){

        List<String> list = new ArrayList<String>();
        list.add("王五");
        list.add("李四");
        User user=new User("张三",18);
        kieSession.insert(list);
        kieSession.insert(user);
        kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter("numberOf"));
    }

    @Test
    public void matches(){

        User user=new User("张三",18);
        kieSession.insert(user);
        kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter("matches"));
    }

    @Test
    public void updates(){
        User user = new User("赵六", 12);
        kieSession.insert(user);
        kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter("test2"));

        System.out.println("the result is " + user.getName());

        User user1= new User("赵六一", 12);
        kieSession.insert(user1);
        kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter("test2"));
        System.out.println("the result is " + user1.getName());
    }

    @Test
    public void inserts(){

        kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter("test1"));
    }
}
