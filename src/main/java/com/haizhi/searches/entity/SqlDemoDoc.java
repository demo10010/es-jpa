package com.haizhi.searches.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.frameworkset.orm.annotation.Column;
import com.frameworkset.orm.annotation.ESId;
import lombok.Data;
import org.frameworkset.elasticsearch.entity.ESBaseData;

import java.util.Date;

@Data
public class SqlDemoDoc extends ESBaseData {
    private Object dynamicPriceTemplate;
    //设定文档标识字段
    @ESId(persistent = false)
    private String demoId;

    private String contentbody;
    /**
     * 当在mapping定义中指定了日期格式时，则需要指定以下两个注解,例如
     * <p>
     * "agentStarttime": {
     * "type": "date",###指定多个日期格式
     * "format":"yyyy-MM-dd HH:mm:ss.SSS||yyyy-MM-dd'T'HH:mm:ss.SSS||yyyy-MM-dd HH:mm:ss||epoch_millis"
     * }
     *
     * @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
     * @Column(dataformat = "yyyy-MM-dd HH:mm:ss.SSS")
     */
    protected Date agentStarttime;

    public Date getAgentStarttimezh() {
        return agentStarttimezh;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(dataformat = "yyyy-MM-dd HH:mm:ss.SSS")
    protected Date agentStarttimezh;

    private String applicationName;

    private String orderId;

    private int contrastStatus;

    private String name;

}
