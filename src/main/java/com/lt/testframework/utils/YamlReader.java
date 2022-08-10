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
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class YamlReader {

    protected static final Logger logger = LoggerFactory.getLogger(YamlReader.class);

    private YamlReader() {
    }

    public static RequestMetadata readYaml(String fileName) throws IOException {
        logger.info("Read {}.yaml", fileName);
        InputStream inputStream = new FileInputStream(new File(requireNonNull(YamlReader.class
                .getClassLoader()
                .getResource("aut/api/"))
                .getPath() + fileName + ".yaml"));
        Yaml yaml = new Yaml();
        Map<String, Object> data = (Map<String, Object>) yaml.load(inputStream);
        return new RequestMetadata(data);
    }
}
