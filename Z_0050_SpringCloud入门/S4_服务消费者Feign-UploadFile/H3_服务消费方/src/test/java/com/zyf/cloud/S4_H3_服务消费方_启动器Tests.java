package com.zyf.cloud;

import com.zyf.cloud.service.消费服务;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;
import java.io.OutputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class S4_H3_服务消费方_启动器Tests {

    @Autowired
    消费服务 消费;

    @Test
    public void contextLoads() {
        Integer result = 消费.add(1, 2);
        System.out.println(result);
        Assert.isTrue(result == 3, "服务调用失败！");
    }

    @Test
    public void testUpload() {
        DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory()
                .createItem(
                        "file",
                        MediaType.TEXT_PLAIN_VALUE,
                        true,
                        "上传文件.txt"
                );

        try (
                InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("./上传文件.txt");
                OutputStream os = fileItem.getOutputStream()
        ) {
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        MultipartFile multi = new CommonsMultipartFile(fileItem);
        String fileName = 消费.handleFileUpload(multi);
        System.out.println(fileName);
    }

}

