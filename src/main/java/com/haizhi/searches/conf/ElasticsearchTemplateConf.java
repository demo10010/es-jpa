package com.haizhi.searches.conf;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@Slf4j
public class ElasticsearchTemplateConf {

    @Value("${spring.elasticsearch.bboss.elasticsearch.rest.hostNames}")
    private String hosts;

    @Value("${elasticsearch.cluster-name}")
    private String clusterName;

    @Value("${elasticsearch.tcpPort}")
    private int tcpPort;

    @Autowired
    private BBossESStarter bbossESStarter;

//    private static final int tcpPort = 9300;

    @Bean
    public TransportClient getTcClient() {
        Assert.hasLength(hosts, "无效的es连接");
        Settings settings = Settings.builder().put("cluster.name", clusterName).build();
        TransportClient transportClient = new PreBuiltTransportClient(settings);
        String[] ips = hosts.split(",");
        try {
            for (int i = 0; i < ips.length; i++) {
                String ip = ips[i].split(":")[0];
                transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName(ip), tcpPort));
            }
        } catch (UnknownHostException e) {
            log.error(e.getMessage());
        }
        return transportClient;
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() {
        Client client = getTcClient();
        ElasticsearchTemplate template = new ElasticsearchTemplate(client);
        return template;
    }

    @Bean
    public ClientInterface bossRestClient() {
        //创建创建/修改/获取/删除文档的客户端对象，单实例多线程安全
        return bbossESStarter.getRestClient();
    }

}
