package junit;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {  "../WEB-INF/spring/appContext.xml", "../WEB-INF/spring/servlet/servlet-service.xml" })  

public abstract class AbstractJUnitTest extends AbstractJUnit4SpringContextTests
{
    protected Logger logger = Logger.getLogger(AbstractJUnitTest.class);
}
