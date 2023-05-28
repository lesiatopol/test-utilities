/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * User: Lesia Topol
 * Date: 5/20/2023
 * All rights reserved
 */

package com.lt.testframework.awsmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

@Component
public class PostgresSQLManager {
    protected static final Logger logger = LoggerFactory.getLogger(PostgresSQLManager.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${aws.default.region}")
    private String awsRegion;

    private Map queryResults = new HashMap<String, String>();

    public PostgresSQLManager() throws SQLException {
    }

    public Map getPostgresSqlItemAsMap(String query) {
        queryResults = jdbcTemplate.queryForMap(query);
        return queryResults;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }
}
