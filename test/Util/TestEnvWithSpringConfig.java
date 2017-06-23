package Util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public abstract class TestEnvWithSpringConfig extends TestEnvironment
{
    private final String springConfigFilePath = "/WebContent/WEB-INF/spring/";

    private String springConfigureXMLFile;
    private ApplicationContext ctx;

    protected abstract void testTask();

    public TestEnvWithSpringConfig()
    {
        this(null);
    }

    public TestEnvWithSpringConfig(String springConfigureXMLFile)
    {
        if (springConfigureXMLFile != null && springConfigureXMLFile.length() > 0)
        {
            this.springConfigureXMLFile = springConfigureXMLFile;
        }
        else
        {
            this.springConfigureXMLFile = "appContext.xml";
        }
        getApplicationContext();
    }

    protected ApplicationContext getApplicationContext()
    {
        if (ctx == null)
        {
            ctx = new FileSystemXmlApplicationContext(springConfigFilePath + springConfigureXMLFile);
        }
        return ctx;
    }
}
