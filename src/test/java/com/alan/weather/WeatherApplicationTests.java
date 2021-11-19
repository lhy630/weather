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
 * ��Ԫ������
 * ��ʱ���ϵ�����¼���û����ɣ�
 * 1��û�ж��쳣�����󡢷��ؽ��з�װ
 * 2��û�ж���ز�������У��
 * 3��ע�ⲻȫ
 * ���£�
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
     * ��ȡ���²���
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
     * ��ȡ�����쳣���Բ���
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
     * ���½ӿ���������
     * @throws InterruptedException
     */
    @Test
    public void rateLimitTest() throws InterruptedException {
        //ģ��100������.tps���ܳ���100,���忴ִ��ʱ��
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
     * ʡ������ѯ����
     */
    @Test
    public void addressTest() {
        try {
            //ʡ��
            //String uri = "/data/city3jdata/province";
            //��
            //String uri = "/data/city3jdata/city/10119";
            //��/��
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
