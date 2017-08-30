package org.qianshengqian;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Created by chen on 2017/4/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml","classpath:spring-mvc.xml"})
@WebAppConfiguration
public class BaseTest extends WebMvcConfigurationSupport {
    public MockMvc mvc;

    private MediaType APPLICATION_JSON_UTF_8= MediaType.valueOf("application/json;charset=UTF-8");

    @Autowired
    public WebApplicationContext wac;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(new MockMvcConfigurer() {
                    @Override
                    public void afterConfigurerAdded(ConfigurableMockMvcBuilder<?> builder) {

                    }

                    @Override
                    public RequestPostProcessor beforeMockMvcCreated(ConfigurableMockMvcBuilder<?> builder,
                            WebApplicationContext context) {
                        return null;
                    }
                })
                .build();
    }
}
