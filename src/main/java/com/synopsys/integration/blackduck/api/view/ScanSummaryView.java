/**
 * hub-common
 *
 * Copyright (C) 2018 Black Duck Software, Inc.
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
 */
package com.synopsys.integration.blackduck.api.view;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.synopsys.integration.blackduck.api.core.HubView;
import com.synopsys.integration.blackduck.api.enumeration.ScanSummaryStatusType;
import com.synopsys.integration.blackduck.api.generated.view.CodeLocationView;

public class ScanSummaryView extends HubView {
    public static final Map<String, Type> links = new HashMap<>();

    public static final String CODELOCATION_LINK = "codelocation";

    static {
        links.put(CODELOCATION_LINK, CodeLocationView.class);
    }

    public ScanSummaryStatusType status;
    public String statusMessage;
    public Date createdAt;
    public Date updatedAt;

}
