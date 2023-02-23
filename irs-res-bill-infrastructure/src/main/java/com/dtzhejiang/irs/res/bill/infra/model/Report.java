package com.dtzhejiang.irs.res.bill.infra.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@TableName(value = "report",autoResultMap = true)
public class Report extends BaseDO{

    /**@Getter
     @Setter
     @TableName(value = "data_element",autoResultMap = true)
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.name
     *
     * @mbggenerated Thu Feb 23 10:59:20 CST 2023
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.application_id
     *
     * @mbggenerated Thu Feb 23 10:59:20 CST 2023
     */
    private String applicationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.application_status
     *
     * @mbggenerated Thu Feb 23 10:59:20 CST 2023
     */
    private String applicationStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.type
     *
     * @mbggenerated Thu Feb 23 10:59:20 CST 2023
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.field
     *
     * @mbggenerated Thu Feb 23 10:59:20 CST 2023
     */
    private String field;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.level
     *
     * @mbggenerated Thu Feb 23 10:59:20 CST 2023
     */
    private String level;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.sub_report_status
     *
     * @mbggenerated Thu Feb 23 10:59:20 CST 2023
     */
    private String subReportStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.success_sub_ids
     *
     * @mbggenerated Thu Feb 23 10:59:20 CST 2023
     */
    private String successSubIds;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.all_sub_ids
     *
     * @mbggenerated Thu Feb 23 10:59:20 CST 2023
     */
    private String allSubIds;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.version
     *
     * @mbggenerated Thu Feb 23 10:59:20 CST 2023
     */
    private String version;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.finish_time
     *
     * @mbggenerated Thu Feb 23 10:59:20 CST 2023
     */
    private Date finishTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report.link_project
     *
     * @mbggenerated Thu Feb 23 10:59:20 CST 2023
     */
    private String linkProject;



}