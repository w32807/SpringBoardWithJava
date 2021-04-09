package testConfig;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zerock.config.RootConfig;
import com.zerock.config.ServletConfig;
import com.zerock.mapper.BoardMapper;


@RunWith(SpringJUnit4ClassRunner.class)
//������ Ŭ������ ���ڿ��� �̿��Ͽ� �ʿ��� ��ü���� ������ ���� ��ü�� ����ϰ� �� (Bean���� ����ϰ� ��)
//�Ʒ��� ���� classpath Ȥ�� file �� ����� �� ����
//@ContextConfiguration(locations = "classpath:src/main/webapp/WEB-INF/spring/root-context.xml")
//�ٵ� ����. webapp�� classPath�� �ƴϱ� ������, classpath�� ����Ͽ� ��ġ ������ �� �� �� ����.
//�׷��Ƿ� file�� ����ϰ�, �ش� configueration�� ��ӹ޾� ����� �� �ֵ��� ����
//https://hightin.tistory.com/42 �����ϱ�
// �׽�Ʈ���� ServletConfig.class (@EnableWebMvc�� ������ ����) ��� ���� MVC ���� bean�� ���ԵǸ� junt���� ���ܸ� ������. 
@ContextConfiguration(classes = {RootConfig.class, ServletConfig.class})
@WebAppConfiguration
public abstract class ApplicationContextTest {
    @Autowired 
    protected ApplicationContext context;
    
    @Autowired
    protected BoardMapper mapper;

    @Autowired
    protected WebApplicationContext ctx;
    
    protected MockMvc mockMvc;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }
}
