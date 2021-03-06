/**
 * Hub Common
 *
 * Copyright (C) 2017 Black Duck Software, Inc.
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
 * under the License.*/
package com.synopsys.integration.blackduck.dataservice.report

import com.synopsys.integration.blackduck.rest.RestConnectionTestHelper
import com.synopsys.integration.blackduck.service.HubServicesFactory
import com.synopsys.integration.blackduck.service.ProjectService
import com.synopsys.integration.blackduck.service.ReportService
import com.synopsys.integration.blackduck.service.model.ProjectRequestBuilder
import com.synopsys.integration.log.IntLogger
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junitpioneer.jupiter.TempDirectory

import java.nio.file.Path

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue

@Tag("integration")
class RiskReportServiceTestIT {
    private static final RestConnectionTestHelper restConnectionTestHelper = new RestConnectionTestHelper()

    @BeforeAll
    public static void createProjectFirst() {
        final String testProjectName = restConnectionTestHelper.getProperty("TEST_PROJECT")
        final String testProjectVersionName = restConnectionTestHelper.getProperty("TEST_VERSION")
        final String testPhase = restConnectionTestHelper.getProperty("TEST_PHASE")
        final String testDistribution = restConnectionTestHelper.getProperty("TEST_DISTRIBUTION")

        ProjectRequestBuilder projectRequestBuilder = new ProjectRequestBuilder();
        projectRequestBuilder.setProjectName(testProjectName);
        projectRequestBuilder.setVersionName(testProjectVersionName);
        projectRequestBuilder.setPhase(testPhase);
        projectRequestBuilder.setDistribution(testDistribution);

        final HubServicesFactory hubServicesFactory = restConnectionTestHelper.createHubServicesFactory()
        final ProjectService projectService = hubServicesFactory.createProjectService();

        projectService.syncProjectAndVersion(projectRequestBuilder.build(), false);
    }

    @Test
    @ExtendWith(TempDirectory.class)
    public void createReportPdfFileTest(@TempDirectory.TempDir Path folderForReport) {
        final String testProjectName = restConnectionTestHelper.getProperty("TEST_PROJECT")
        final String testProjectVersionName = restConnectionTestHelper.getProperty("TEST_VERSION")

        final HubServicesFactory hubServicesFactory = restConnectionTestHelper.createHubServicesFactory()
        final IntLogger logger = hubServicesFactory.getRestConnection().logger
        ReportService riskReportService = hubServicesFactory.createReportService(30000)
        Optional<File> pdfFile = riskReportService.createReportPdfFile(folderForReport.toFile(), testProjectName, testProjectVersionName)
        assertTrue(pdfFile.isPresent())
        assertNotNull(pdfFile.get())
        assertTrue(pdfFile.get().exists())
    }

    @Test
    @ExtendWith(TempDirectory.class)
    public void createReportFilesTest(@TempDirectory.TempDir Path folderForReport) {
        final String testProjectName = restConnectionTestHelper.getProperty("TEST_PROJECT")
        final String testProjectVersionName = restConnectionTestHelper.getProperty("TEST_VERSION")

        final HubServicesFactory hubServicesFactory = restConnectionTestHelper.createHubServicesFactory()
        final IntLogger logger = hubServicesFactory.getRestConnection().logger
        ReportService riskReportService = hubServicesFactory.createReportService(30000)
        File reportFolder = folderForReport.toFile()
        riskReportService.createReportFiles(reportFolder, testProjectName, testProjectVersionName)

        File[] reportFiles = reportFolder.listFiles();
        assertNotNull(reportFiles)
        assertTrue(reportFiles.size() > 0)
        Map<String, File> reportFileMap = reportFiles.collectEntries {
            [it.getName(), it]
        }
        assertNotNull(reportFileMap.get('js'))
        assertNotNull(reportFileMap.get('css'))
        assertNotNull(reportFileMap.get('images'))
        assertNotNull(reportFileMap.get('riskreport.html'))
    }

    @Test
    @ExtendWith(TempDirectory.class)
    public void createNoticesReportFileTest(@TempDirectory.TempDir Path folderForReport) {
        final String testProjectName = restConnectionTestHelper.getProperty("TEST_PROJECT")
        final String testProjectVersionName = restConnectionTestHelper.getProperty("TEST_VERSION")

        final HubServicesFactory hubServicesFactory = restConnectionTestHelper.createHubServicesFactory()
        final IntLogger logger = hubServicesFactory.getRestConnection().logger
        ReportService riskReportService = hubServicesFactory.createReportService(30000)
        Optional<File> noticeReportFile = riskReportService.createNoticesReportFile(folderForReport.toFile(), testProjectName, testProjectVersionName);
        assertTrue(noticeReportFile.isPresent())
        assertNotNull(noticeReportFile.get())
        assertTrue(noticeReportFile.get().exists())
    }

}
