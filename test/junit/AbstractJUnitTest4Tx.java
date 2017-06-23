package junit;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {  "../WEB-INF/spring/appContext.xml", "../WEB-INF/spring/servlet/servlet-service.xml" })
public abstract class AbstractJUnitTest4Tx extends AbstractTransactionalJUnit4SpringContextTests
{
    
}
