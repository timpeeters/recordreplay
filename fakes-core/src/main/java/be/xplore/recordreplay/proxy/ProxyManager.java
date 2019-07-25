package be.xplore.recordreplay.proxy;

import java.net.InetSocketAddress;

public class ProxyManager {
    private static final String PROXY_HOST = "http.proxyHost";
    private static final String PROXY_PORT = "http.proxyPort";
    private final InetSocketAddress originalProxy;
//    private final Set<String> originalNonProxyHosts;

    public ProxyManager() {
        this.originalProxy = getCurrentProxy();
        //  this.originalNonProxyHosts = getCurrentNonProxyHosts();
    }


    public void activate(String host, int port) {
        System.setProperty(PROXY_HOST, new InetSocketAddress(host, port).getHostName());
        System.setProperty(PROXY_PORT, String.valueOf(port));
    }


    public void deActivate() {
        if (originalProxy == null) {
            System.clearProperty(PROXY_HOST);
            System.clearProperty(PROXY_PORT);
        } else {
            System.setProperty(PROXY_HOST, originalProxy.getHostName());
            System.setProperty(PROXY_PORT, String.valueOf(originalProxy.getPort()));
        }
    }

    private static InetSocketAddress getCurrentProxy() {
        String host = System.getProperty(PROXY_HOST);
        String port = System.getProperty(PROXY_PORT);
        if (host == null) {
            return null;
        }
        return new InetSocketAddress(host, Integer.parseInt(port));
    }

   /* private static Set<String> getCurrentNonProxyHosts() {
        String hosts = System.getProperty("http.nonProxyHosts");
        if (hosts == null) {
            return Collections.emptySet();
        }
        Set<String> hostSet = new HashSet<>();
        Collections.addAll(hostSet, hosts.split("|"));
        return hostSet;

    }
*/
}
