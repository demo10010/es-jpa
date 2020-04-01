package com.haizhi.searches.util;

import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.springframework.beans.factory.annotation.Autowired;

public class BbossClientUtil {

    @Autowired
    private BBossESStarter bbossESStarter;
    private String mappath = "esmapper/demo.xml";

    private void test(){
        //Create a client tool to load configuration files, single instance multithreaded security
        ClientInterface configClientUtil = bbossESStarter.getConfigRestClient(mappath);
        //Build a create/modify/get/delete document client object, single instance multi-thread security
        ClientInterface clientUtil = bbossESStarter.getRestClient();

    }
}
