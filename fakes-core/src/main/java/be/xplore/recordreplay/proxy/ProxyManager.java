package be.xplore.recordreplay.proxy;

import java.net.InetSocketAddress;
import java.net.ProxySelector;

public class ProxyManager {
    public static final String PROXY_HOST_PROP = "http.proxyHost";
    public static final String PROXY_PORT_PROP = "http.proxyPort";
    private final InetSocketAddress originalProxy;

    public ProxyManager() {
        this.originalProxy = getCurrentProxy();
    }


    public void activate(String host, int port) {
        var address = new InetSocketAddress(host, port);
        ProxySelector.setDefault(ProxySelector.of(address));
        System.setProperty(PROXY_HOST_PROP, address.getHostString());
        System.setProperty(PROXY_PORT_PROP, String.valueOf(address.getPort()));
    }

    public void deActivate() {
        setOriginalProxyProperty();
        ProxySelector.setDefault(ProxySelector.of(originalProxy));
    }

    private void setOriginalProxyProperty() {
        if (originalProxy == null) {
            System.clearProperty(PROXY_HOST_PROP);
            System.clearProperty(PROXY_PORT_PROP);
        } else {
            System.setProperty(PROXY_HOST_PROP, originalProxy.getHostName());
            System.setProperty(PROXY_PORT_PROP, String.valueOf(originalProxy.getPort()));
        }
    }

    private static InetSocketAddress getCurrentProxy() {
        String host = System.getProperty(PROXY_HOST_PROP);
        if (host == null) {
            return null;
        }
        String port = System.getProperty(PROXY_PORT_PROP);
        return new InetSocketAddress(host, Integer.parseInt(port));
    }
}
