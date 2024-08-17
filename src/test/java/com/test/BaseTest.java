package com.test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.framework.Configuration;
import com.framework.WebAutomator;

//@TestInstance(Lifecycle.PER_CLASS)
public class BaseTest {
    private static final Logger Log = LogManager.getLogger(BaseTest.class);
    public Configuration conf = Configuration.INSTANCE;
    private WebAutomator automator;

    protected Configuration getConf() {
        Log.info("Getting configuration object");
        return conf;
    }
    public WebAutomator getAutomator() {
        Log.info("Inside getter of WebAutomator");
        return this.automator;
    }
    public void setAutomator(WebAutomator automator) {
        Log.info("Inside setter of WebAutomator");
        this.automator = automator;
    }
}
