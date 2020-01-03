package com.pan.love;


import com.pan.love.system.bean.vo.QueryUserInVo;
import com.pan.love.system.bean.vo.QueryUserOutVo;
import com.pan.love.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoveApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(LoveApplicationTests.class);
//
//    @Autowired
//    private RedisService redissonService;

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        System.out.println("测试开始");
    }

//    @Test
//    public void redisTest(){
//        String recordId="1";
//        RLock lock = redissonService.getRLock(recordId);
//        try {
//            boolean bs = lock.tryLock(5, 6, TimeUnit.SECONDS);
//            if (bs) {
//                // 业务代码
//                log.info("进入业务代码: " + recordId);
//
//                lock.unlock();
//            } else {
//                Thread.sleep(300);
//            }
//        } catch (Exception e) {
//            log.error("", e);
//            lock.unlock();
//        }
//    }

    @Test
    public void getUserListTest(){

        QueryUserInVo queryUserInVo=new QueryUserInVo();
        List<QueryUserOutVo> listUser=userService.queryUser(queryUserInVo);
        System.out.println(listUser);
    }

}
