/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.blackducksoftware.integration.hub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import com.blackducksoftware.integration.hub.ScanExecutor.Result;
import com.blackducksoftware.integration.hub.api.HubVersionRestService;
import com.blackducksoftware.integration.hub.exception.HubIntegrationException;
import com.blackducksoftware.integration.test.TestLogger;

public class ScanExecutorTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static final String FAKE_HUB_SERVER_URL = "http://www.google.com";

    private static final String FAKE_USERNAME = "Bugs Bunny";

    private static final String FAKE_PASSWORD = "Daffy Duck";

    private static final String FAKE_PROXY_HOST_BASIC = "www.yahoo.com";

    private static final String FAKE_PROXY_PORT_BASIC = "1234";

    private static final String FAKE_PROXY_USER_BASIC = "ramanujan";

    private static final String FAKE_PROXY_PASSWORD_BASIC = "euler";

    private HubSupportHelper getCheckedHubSupportHelper(final String hubVersion) throws Exception {
        final HubVersionRestService service = Mockito.mock(HubVersionRestService.class);
        Mockito.when(service.getHubVersion()).thenReturn(hubVersion);
        final HubSupportHelper supportHelper = new HubSupportHelper();
        supportHelper.checkHubSupport(service, new TestLogger());

        return supportHelper;
    }

    @Test
    public void testNoURL() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("No Hub URL provided.");
        new ScanExecutor(null, null, null, null, null, null) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
    }

    @Test
    public void testNoUsername() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("No Hub username provided.");
        new ScanExecutor(FAKE_HUB_SERVER_URL, null, null, null, null, null) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
    }

    @Test
    public void testNoPassword() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("No Hub password provided.");
        new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, null, null, null, null) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
    }

    @Test
    public void testNoTargets() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("No scan targets provided.");
        new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, null, null, null) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
    }

    @Test
    public void testNoBuildNumber() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("No build identifier provided.");

        final ArrayList<String> scanTargets = new ArrayList<>();
        scanTargets.add((new File("")).getAbsolutePath());

        new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, scanTargets, null, null) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
    }

    @Test
    public void testNoHubSupportHelper() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("No HubSupportHelper provided.");

        final ArrayList<String> scanTargets = new ArrayList<>();
        scanTargets.add((new File("")).getAbsolutePath());

        new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, scanTargets, "123", null) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
    }

    @Test
    public void testHubSupportHelperNotChecked() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("The HubSupportHelper has not been checked yet.");

        final ArrayList<String> scanTargets = new ArrayList<>();
        scanTargets.add((new File("")).getAbsolutePath());

        final HubSupportHelper supportHelper = new HubSupportHelper();

        new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, scanTargets, "123", supportHelper) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
    }

    @Test
    public void testValidConstructor() throws Exception {

        final ArrayList<String> scanTargets = new ArrayList<>();
        scanTargets.add((new File("")).getAbsolutePath());

        final HubSupportHelper supportHelper = getCheckedHubSupportHelper("3.0.0");

        new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, scanTargets, "123", supportHelper) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
    }

    @Test
    public void testSetupNoLogger() throws Exception {

        final ArrayList<String> scanTargets = new ArrayList<>();
        scanTargets.add((new File("")).getAbsolutePath());

        final HubSupportHelper supportHelper = getCheckedHubSupportHelper("3.0.0");

        final ScanExecutor executor = new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, scanTargets,
                "123", supportHelper) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
        final Result result = executor.setupAndRunScan(null, null, null);
        assertEquals(Result.FAILURE, result);
    }

    @Test
    public void testSetupNoScanExec() throws Exception {

        final ArrayList<String> scanTargets = new ArrayList<>();
        scanTargets.add((new File("")).getAbsolutePath());

        final HubSupportHelper supportHelper = getCheckedHubSupportHelper("3.0.0");

        final ScanExecutor executor = new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, scanTargets,
                "123", supportHelper) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
        final TestLogger logger = new TestLogger();
        executor.setLogger(logger);
        final Result result = executor.setupAndRunScan(null, null, null);
        assertEquals(Result.FAILURE, result);

        final String output = logger.getOutputString();
        assertTrue(output, output.contains("Please provide the Hub scan CLI."));
    }

    @Test
    public void testSetupScanExecDoesNotExist() throws Exception {

        final ArrayList<String> scanTargets = new ArrayList<>();
        scanTargets.add((new File("")).getAbsolutePath());

        final HubSupportHelper supportHelper = getCheckedHubSupportHelper("3.0.0");

        final ScanExecutor executor = new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, scanTargets,
                "123", supportHelper) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
        final TestLogger logger = new TestLogger();
        executor.setLogger(logger);
        final Result result = executor.setupAndRunScan("./Fake", null, null);
        assertEquals(Result.FAILURE, result);

        final String output = logger.getOutputString();
        assertTrue(output, output.contains("The Hub scan CLI provided does not exist."));
    }

    @Test
    public void testSetupNoOneJarFile() throws Exception {

        final ArrayList<String> scanTargets = new ArrayList<>();
        scanTargets.add((new File("")).getAbsolutePath());

        final HubSupportHelper supportHelper = getCheckedHubSupportHelper("3.0.0");

        final ScanExecutor executor = new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, scanTargets,
                "123", supportHelper) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
        final TestLogger logger = new TestLogger();
        executor.setLogger(logger);
        final Result result = executor.setupAndRunScan(".", null, null);
        assertEquals(Result.FAILURE, result);

        final String output = logger.getOutputString();
        assertTrue(output, output.contains("Please provide the path for the CLI cache."));
    }

    @Test
    public void testSetupNoJavaExec() throws Exception {

        final ArrayList<String> scanTargets = new ArrayList<>();
        scanTargets.add((new File("")).getAbsolutePath());
        final HubSupportHelper supportHelper = getCheckedHubSupportHelper("3.0.0");
        final ScanExecutor executor = new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, scanTargets,
                "123", supportHelper) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
        final TestLogger logger = new TestLogger();
        executor.setLogger(logger);
        final Result result = executor.setupAndRunScan(".", ".", null);
        assertEquals(Result.FAILURE, result);

        final String output = logger.getOutputString();
        assertTrue(output, output.contains("Please provide the java home directory."));
    }

    @Test
    public void testSetupJavaExecDoesNotExist() throws Exception {

        final ArrayList<String> scanTargets = new ArrayList<>();
        scanTargets.add((new File("")).getAbsolutePath());
        final HubSupportHelper supportHelper = getCheckedHubSupportHelper("3.0.0");
        final ScanExecutor executor = new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, scanTargets,
                "123", supportHelper) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return null;
            }

        };
        final TestLogger logger = new TestLogger();
        executor.setLogger(logger);
        final Result result = executor.setupAndRunScan(".", ".", "./Fake");
        assertEquals(Result.FAILURE, result);

        final String output = logger.getOutputString();
        assertTrue(output, output.contains("The Java executable provided does not exist at : "));
    }

    @Test
    public void testSetupNoMemoryProvided() throws Exception {

        final ArrayList<String> scanTargets = new ArrayList<>();
        scanTargets.add((new File("")).getAbsolutePath());
        final HubSupportHelper supportHelper = getCheckedHubSupportHelper("3.0.0");
        final ScanExecutor executor = new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, scanTargets,
                "123", supportHelper) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                return Result.SUCCESS;
            }

        };
        final TestLogger logger = new TestLogger();
        executor.setLogger(logger);
        final Result result = executor.setupAndRunScan(".", ".", ".");
        assertEquals(Result.SUCCESS, result);

        final String output = logger.getOutputString();
        assertTrue(output, output.contains("No memory set for the HUB CLI. Will use the default memory, "));
    }

    @Test
    public void testSetupValid() throws Exception {

        final ArrayList<String> scanTargets = new ArrayList<>();
        scanTargets.add((new File("")).getAbsolutePath());

        final List<String> cmdList = new ArrayList<>();
        final HubSupportHelper supportHelper = getCheckedHubSupportHelper("3.0.0");
        final ScanExecutor executor = new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, scanTargets,
                "123", supportHelper) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                cmdList.addAll(cmd);
                return Result.SUCCESS;
            }

        };
        final TestLogger logger = new TestLogger();
        executor.setLogger(logger);
        executor.setScanMemory(8192);

        final Result result = executor.setupAndRunScan(".", ".", ".");
        assertEquals(Result.SUCCESS, result);

        final String output = logger.getOutputString();

        assertTrue(output, !cmdList.isEmpty());

        final StringBuilder builder = new StringBuilder();
        for (final String currCmd : cmdList) {
            builder.append(currCmd);
            builder.append(" ");
        }
        final String actualCmd = builder.toString();
        assertTrue(actualCmd, StringUtils.isNotBlank(actualCmd));

        assertTrue(actualCmd, actualCmd.contains("-Done-jar.silent=true"));
        assertTrue(actualCmd, actualCmd.contains("-Done-jar.jar.path="));
        assertTrue(actualCmd, actualCmd.contains("-Xmx"));
        assertTrue(actualCmd, actualCmd.contains("-jar"));
        assertTrue(actualCmd, actualCmd.contains("--scheme"));
        assertTrue(actualCmd, actualCmd.contains("--host"));
        assertTrue(actualCmd, actualCmd.contains("--username"));
        assertTrue(actualCmd, actualCmd.contains("--password"));
        assertTrue(actualCmd, actualCmd.contains("--port"));
        assertTrue(actualCmd, actualCmd.contains("--logDir"));
        assertTrue(actualCmd, actualCmd.contains(scanTargets.get(0)));

    }

    @Test
    public void testSetupProxySettings() throws Exception {

        final ArrayList<String> scanTargets = new ArrayList<>();
        scanTargets.add((new File("")).getAbsolutePath());

        final List<String> cmdList = new ArrayList<>();
        final HubSupportHelper supportHelper = getCheckedHubSupportHelper("3.0.0");
        final ScanExecutor executor = new ScanExecutor(FAKE_HUB_SERVER_URL, FAKE_USERNAME, FAKE_PASSWORD, scanTargets,
                "123", supportHelper) {

            @Override
            protected Result executeScan(final List<String> cmd, final String logDirectory)
                    throws HubIntegrationException, InterruptedException {
                cmdList.addAll(cmd);
                return Result.SUCCESS;
            }

        };
        final TestLogger logger = new TestLogger();
        executor.setLogger(logger);

        final ArrayList<Pattern> noProxyHosts = new ArrayList<>();
        noProxyHosts.add(Pattern.compile("test"));
        executor.setProxyHost(FAKE_PROXY_HOST_BASIC);
        executor.setProxyPort(Integer.valueOf(FAKE_PROXY_PORT_BASIC));
        executor.setProxyUsername(FAKE_PROXY_USER_BASIC);
        executor.setProxyPassword(FAKE_PROXY_PASSWORD_BASIC);
        executor.setNoProxyHosts(noProxyHosts);

        final Result result = executor.setupAndRunScan(".", ".", ".");
        assertEquals(Result.SUCCESS, result);

        final String output = logger.getOutputString();

        assertTrue(output, !cmdList.isEmpty());

        final StringBuilder builder = new StringBuilder();
        for (final String currCmd : cmdList) {
            builder.append(currCmd);
            builder.append(" ");
        }
        final String actualCmd = builder.toString();
        assertTrue(actualCmd, StringUtils.isNotBlank(actualCmd));

        assertTrue(actualCmd, actualCmd.contains("-Done-jar.silent=true"));
        assertTrue(actualCmd, actualCmd.contains("-Done-jar.jar.path="));

        assertTrue(actualCmd, actualCmd.contains("-Dhttp.proxyHost=") || actualCmd.contains("-Dhttps.proxyHost="));
        assertTrue(actualCmd, actualCmd.contains("-Dhttp.proxyPort=") || actualCmd.contains("-Dhttps.proxyPort="));
        assertTrue(actualCmd, actualCmd.contains("-Dhttp.nonProxyHosts="));

        assertTrue(actualCmd, actualCmd.contains("-Dhttp.proxyUser=") || actualCmd.contains("-Dhttps.proxyUser="));
        assertTrue(actualCmd,
                actualCmd.contains("-Dhttp.proxyPassword=") || actualCmd.contains("-Dhttps.proxyPassword="));

        assertTrue(actualCmd, actualCmd.contains("-Xmx"));
        assertTrue(actualCmd, actualCmd.contains("-jar"));
        assertTrue(actualCmd, actualCmd.contains("--scheme"));
        assertTrue(actualCmd, actualCmd.contains("--host"));
        assertTrue(actualCmd, actualCmd.contains("--username"));
        assertTrue(actualCmd, actualCmd.contains("--password"));
        assertTrue(actualCmd, actualCmd.contains("--port"));
        assertTrue(actualCmd, actualCmd.contains("--logDir"));
        assertTrue(actualCmd, actualCmd.contains(scanTargets.get(0)));

    }

}
