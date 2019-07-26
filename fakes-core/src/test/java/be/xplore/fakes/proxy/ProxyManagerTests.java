package be.xplore.fakes.proxy;

import be.xplore.recordreplay.proxy.ProxyManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyManagerTests {
    private static final String HOST = "azertyuiop";
    private static final int PORT = 2000;
    private final ProxyManager proxyManager = new ProxyManager();

    @Before
    public void resetDefaults() {
        System.clearProperty(ProxyManager.PROXY_HOST_PROP);
        System.clearProperty(ProxyManager.PROXY_PORT_PROP);
    }

    @After
    public void deActivate() {
        proxyManager.deActivate();
    }

    @Test
    public void systemPropertySets() {
        proxyManager.activate(HOST, PORT);
        assertThat(System.getProperty(ProxyManager.PROXY_HOST_PROP)).isEqualTo(HOST);
        assertThat(System.getProperty(ProxyManager.PROXY_PORT_PROP)).isEqualTo(String.valueOf(PORT));
    }

    @Test
    public void systemPropertyResets(){
        proxyManager.activate(HOST, PORT);
        proxyManager.deActivate();
        assertThat(System.getProperty(ProxyManager.PROXY_HOST_PROP)).isNull();
        assertThat(System.getProperty(ProxyManager.PROXY_PORT_PROP)).isNull();
    }

    @Test
    @SuppressWarnings("checkstyle:executablestatementcount")
    public void systemPropertyResetsToPrevious(){
        System.setProperty(ProxyManager.PROXY_HOST_PROP, HOST);
        System.setProperty(ProxyManager.PROXY_PORT_PROP, String.valueOf(PORT));
        proxyManager.activate("azerty", 168);
        proxyManager.deActivate();
        assertThat(System.getProperty(ProxyManager.PROXY_HOST_PROP)).isEqualTo(HOST);
        assertThat(System.getProperty(ProxyManager.PROXY_PORT_PROP)).isEqualTo(String.valueOf(PORT));
    }

    @Test
    public void testNullPort(){
        System.setProperty(ProxyManager.PROXY_HOST_PROP, HOST);
        System.clearProperty(ProxyManager.PROXY_PORT_PROP);
        proxyManager.activate("azerty", 168);
        proxyManager.deActivate();
        assertThat(System.getProperty(ProxyManager.PROXY_PORT_PROP)).isEqualTo("0");
    }
}
