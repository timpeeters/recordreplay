package be.xplore.recordreplay.proxy;

import java.net.InetSocketAddress;
import java.net.ProxySelector;

public class ProxyManager {
    public static final String PROXY_HOST_PROP = "http.proxyHost";
    public static final String PROXY_PORT_PROP = "http.proxyPort";
    private InetSocketAddress originalProxy;

    public void activate(String host, int port) {
        originalProxy = getCurrentProxy();
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

    private InetSocketAddress getCurrentProxy() {
        String host = System.getProperty(PROXY_HOST_PROP);
        if (host == null) {
            return null;
        }
        String port = System.getProperty(PROXY_PORT_PROP);
        return InetSocketAddress.createUnresolved(host, parsePort(port));
    }

    private int parsePort(String port) {
        if (port == null){
            return 0;
        }
        return Integer.parseInt(port);
    }
}
