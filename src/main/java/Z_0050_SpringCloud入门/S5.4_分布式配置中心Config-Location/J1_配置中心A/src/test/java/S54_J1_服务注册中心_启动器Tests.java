import com.zyf.cloud.S54_J1_服务注册中心_启动器;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = S54_J1_服务注册中心_启动器.class)
@AutoConfigureMockMvc
public class S54_J1_服务注册中心_启动器Tests {

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        String uri = "/config-client/dev/master";
        String content = requestGetAndReturn(uri);
        JSONObject obj = (JSONObject) JSONParser.parseJSON(content);
        Assert.isTrue(obj.getJSONArray("propertySources").length() == 2, "返回值错误");
    }

    @Test
    public void 测试请求1() throws Exception {
        writeFile();

        String uri = "/config-client.yml";
        String content = requestGetAndReturn(uri);
        Assert.isTrue(content.contains("default-location"), "返回值错误");

        Thread.sleep(5000);
        writeFile2();

        uri = "/config-client.yml";
        content = requestGetAndReturn(uri);
        Assert.isTrue(content.contains("default-location2"), "返回值错误");
    }

    @Test
    public void 测试请求2() throws Exception {
        writeFile();

        String uri = "/config-client-dev.yml";
        String content = requestGetAndReturn(uri);
        Assert.isTrue(content.contains("dev-location"), "返回值错误");

        Thread.sleep(5000);
        writeFile2();

        uri = "/config-client-dev.yml";
        content = requestGetAndReturn(uri);
        Assert.isTrue(content.contains("dev-location2"), "返回值错误");
    }

    private String requestGetAndReturn(String uri) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.isTrue(status == 200, "请求失败！");
        return mvcResult.getResponse().getContentAsString();
    }

    private static void writeFile() throws IOException {
        String dirPath = "d:\\java";
        File file = new File(dirPath);
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
            if (!mkdir) {
                throw new IOException();
            }
        } else {
            String defaultPath = dirPath + "\\config-client.yml";
            String devPath = dirPath + "\\config-client-dev.yml";
            FileCopyUtils.copy("info:\n" +
                    "  profile: default-location", new FileWriter(defaultPath));
            FileCopyUtils.copy("info:\n" +
                    "  profile: dev-location", new FileWriter(devPath));
        }
    }


    private static void writeFile2() throws IOException {
        String dirPath = "d:\\java";
        File file = new File(dirPath);
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
            if (!mkdir) {
                throw new IOException();
            }
        } else {
            String defaultPath = dirPath + "\\config-client.yml";
            String devPath = dirPath + "\\config-client-dev.yml";
            FileCopyUtils.copy("info:\n" +
                    "  profile: default-location2", new FileWriter(defaultPath));
            FileCopyUtils.copy("info:\n" +
                    "  profile: dev-location2", new FileWriter(devPath));
        }
    }

}