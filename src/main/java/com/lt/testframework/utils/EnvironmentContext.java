/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * User: Lesia Topol
 * Date: 7/10/2022
 * All rights reserved
 */

package com.lt.testframework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.stream.Collectors;

public final class EnvironmentContext {
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentContext.class);
    private static String environment;

    private EnvironmentContext() {
        throw new UnsupportedOperationException("Constructor instantiation is unsupported.");
    }

    public static void setEnvironment() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();
        List<String> vmOpt = arguments.stream()
                            .filter(vmOpts -> vmOpts.contains("-Dconf"))
                            .collect(Collectors.toList());

        if (!vmOpt.isEmpty()) {
            environment = vmOpt.get(0).substring(vmOpt.get(0).lastIndexOf("=") + 1);
        } else {
            environment = "src/test/resources/env/stageconf/";
        }
        logger.info("Environment - {}", environment);
    }

    public static String getEnvironment() {
        return environment;
    }
}
