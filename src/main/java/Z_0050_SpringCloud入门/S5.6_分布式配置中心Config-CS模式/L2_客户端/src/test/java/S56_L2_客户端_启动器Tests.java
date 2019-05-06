import com.zyf.cloud.S56_L2_客户端_启动器;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = S56_L2_客户端_启动器.class)
@AutoConfigureMockMvc
public class S56_L2_客户端_启动器Tests {

    @Autowired
    protected MockMvc mockMvc;
    @Value("${spring.cloud.config.uri}")
    private String urlPrefix;

    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void contextLoads() throws Exception {
        String uri = "/actuator/info";
        String content = requestGetAndReturn(uri);
        JSONObject obj = (JSONObject) JSONParser.parseJSON(content);
        Assert.isTrue(obj.getString("profile").equals("default-git"), "返回值错误");
    }

    @Test
    public void 测试请求0() throws Exception {
        String uri = "/info";
        String content = requestGetAndReturn(uri);
        Assert.isTrue(content.equals("default-git"), "返回值错误");
    }

    @Test
    public void 测试请求1() throws Exception {
        String url = urlPrefix + "config-client/dev/master";
        String content = restTemplate.getForObject(url, String.class);
        JSONObject obj = (JSONObject) JSONParser.parseJSON(content);
        Assert.isTrue(obj.getJSONArray("propertySources").length() == 2, "返回值错误");
    }

    @Test
    public void 测试请求2() throws Exception {
        String url = urlPrefix + "config-client/default/master";
        String content = restTemplate.getForObject(url, String.class);
        JSONObject obj = (JSONObject) JSONParser.parseJSON(content);
        Assert.isTrue(obj.getJSONArray("propertySources").length() == 1, "返回值错误");
    }


    @Test
    public void 测试请求3() throws Exception {
        String url = urlPrefix + "config-client/master";
        String content = restTemplate.getForObject(url, String.class);
        JSONObject obj = (JSONObject) JSONParser.parseJSON(content);
        Assert.isTrue(obj.getJSONArray("propertySources").length() == 1, "返回值错误");
    }

    @Test
    public void 测试请求4() {
        String url = urlPrefix + "config-client-dev.yml";
        String content = restTemplate.getForObject(url, String.class);
        Assert.isTrue(content.contains("dev-git"), "返回值错误");
    }

    @Test
    public void 测试请求5() {
        String url = urlPrefix + "config-client.yml";
        String content = restTemplate.getForObject(url, String.class);
        Assert.isTrue(content.contains("default-git"), "返回值错误");
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