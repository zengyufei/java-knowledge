import com.zyf.cloud.S52_U1_服务注册中心_启动器;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = S52_U1_服务注册中心_启动器.class)
@AutoConfigureMockMvc
public class S52_U1_服务注册中心_启动器Tests {

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        String uri = "/encrypt/status";
        String content = requestGetAndReturn(uri);
        JSONObject obj = (JSONObject) JSONParser.parseJSON(content);
        Assert.isTrue(obj.getString("status").equals("OK"), "返回值错误");
    }

    @Test
    public void 加密解密() throws Exception {
        String uri = "/encrypt";
        String param = "id=999&value=content";
        String content = requestPostAndReturn(uri, MediaType.APPLICATION_FORM_URLENCODED, param);

        uri = "/decrypt";
        content = requestPostAndReturn(uri, MediaType.APPLICATION_FORM_URLENCODED, content);
        Assert.isTrue(param.equals(content), "加密解密失败！");
    }

    private String requestGetAndReturn(String uri) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get(uri)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.isTrue(status == 200, "请求失败！");
        return mvcResult.getResponse().getContentAsString();
    }

    private String requestPostAndReturn(String uri, String param) throws Exception {
        return requestPostAndReturn(uri, null, param);
    }

    private String requestPostAndReturn(String uri, MediaType mediaType, String param) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(uri);
        builder.content(param.getBytes());
        if (mediaType != null) {
            builder.contentType(mediaType);
        }
        MvcResult mvcResult = mockMvc.perform(
                builder
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.isTrue(status == 200, "请求失败！");
        return mvcResult.getResponse().getContentAsString();
    }

}