package com.haizhi.searches.entity.qo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.frameworkset.orm.annotation.Column;
import com.frameworkset.orm.annotation.ESId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SqlDemoDocQo{
//    private Object dynamicPriceTemplate;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(dataformat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(example = "2020-01-01 12:18:48")
    protected Date agentStarttime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(dataformat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(example = "2020-01-01 12:18:48")
    protected Date agentStarttimezh;

    private String applicationName;

    private String orderId;

    private int contrastStatus;

    private String name;

}
