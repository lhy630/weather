package com.alan.weather;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 单元测试类
 * 因时间关系，以下几点没有完成：
 * 1、没有对异常、请求、返回进行封装
 * 2、没有对相关参数进行校验
 * 3、注解不全
 * 见谅！
 */
@Slf4j
@SpringBootTest
@EnableRetry
class WeatherApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * 获取气温测试
     */
    @Test
    public void tempTest() {
        try {
            String uri = "/data/sk/10119/04/01";
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
            int status = result.getResponse().getStatus();
            Assert.isTrue(status == 200, "status error");
            String temp = result.getResponse().getContentAsString();
            Assert.hasText(temp, "fail");
            log.info(Thread.currentThread().getName() + " : " + temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取气温异常重试测试
     */
    @Test
    public void retryTempTest() {
        try {
            String uri = "/data/sk/101192/04/01";
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
            int status = result.getResponse().getStatus();
            Assert.isTrue(status == 200, "status error");
            String temp = result.getResponse().getContentAsString();
            Assert.hasText(temp, "fail");
            log.info(Thread.currentThread().getName() + " : " + temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 气温接口限流测试
     * @throws InterruptedException
     */
    @Test
    public void rateLimitTest() throws InterruptedException {
        //模拟100个请求.tps可能超过100,具体看执行时间
        int reqNum = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch count = new CountDownLatch(reqNum);
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < reqNum; i++) {
            executorService.execute(() -> {
                tempTest();
                count.countDown();
            });
        }
        count.await();
        Long endTime = System.currentTimeMillis();
        log.info("totle time: {}", endTime - startTime);
        executorService.shutdown();
    }

    /**
     * 省市区查询测试
     */
    @Test
    public void addressTest() {
        try {
            //省份
            //String uri = "/data/city3jdata/province";
            //市
            //String uri = "/data/city3jdata/city/10119";
            //区/县
            String uri = "/data/city3jdata/county/04";
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
            int status = result.getResponse().getStatus();
            Assert.isTrue(status == 200, "status error");
            String temp = result.getResponse().getContentAsString();
            Assert.hasText(temp, "fail");
            log.info(Thread.currentThread().getName() + " : " + temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
