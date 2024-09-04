import com.zyf.cloud.S53_W1_服务注册中心_启动器;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = S53_W1_服务注册中心_启动器.class)
@AutoConfigureMockMvc
public class S53_W1_服务注册中心_启动器Tests {

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
        String uri = "/config-client.yml";
        String content = requestGetAndReturn(uri);
        Assert.isTrue(content.contains("default-git"), "返回值错误");
    }

    @Test
    public void 测试请求2() throws Exception {
        String uri = "/config-client-dev.yml";
        String content = requestGetAndReturn(uri);
        Assert.isTrue(content.contains("dev-git"), "返回值错误");
    }

    private String requestGetAndReturn(String uri) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.isTrue(status == 200, "请求失败！");
        return mvcResult.getResponse().getContentAsString();
    }

}