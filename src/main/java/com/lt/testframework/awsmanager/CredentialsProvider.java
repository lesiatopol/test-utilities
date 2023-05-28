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

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfilesConfigFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class CredentialsProvider {
    protected static final Logger logger = LoggerFactory.getLogger(CredentialsProvider.class);

    private ProfilesConfigFile profilesConfigFile;
    private AWSCredentialsProvider awsCredentialsProvider;

    public CredentialsProvider() {
        profilesConfigFile = new ProfilesConfigFile("src/main/resources/credentials");
        awsCredentialsProvider = new ProfileCredentialsProvider(profilesConfigFile,"default");
        logger.info("AwsCredentialsProvider has been initialized with default profile");
    }

    public AWSCredentialsProvider getAwsCredentialsProvider(){
        return awsCredentialsProvider;
    }
}
