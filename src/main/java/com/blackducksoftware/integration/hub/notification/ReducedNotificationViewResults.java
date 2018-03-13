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
package com.blackducksoftware.integration.hub.notification;

import java.util.Date;
import java.util.List;

import com.blackducksoftware.integration.hub.api.view.ReducedNotificationView;

public class ReducedNotificationViewResults {
    private final List<ReducedNotificationView> notificationViews;
    private final Date latestNotificationCreatedAtDate;
    private final String latestNotificationCreatedAtString;

    public ReducedNotificationViewResults(final List<ReducedNotificationView> notificationViews, final Date latestNotificationCreatedAtDate, final String latestNotificationCreatedAtString) {
        this.notificationViews = notificationViews;
        this.latestNotificationCreatedAtDate = latestNotificationCreatedAtDate;
        this.latestNotificationCreatedAtString = latestNotificationCreatedAtString;
    }

    public List<ReducedNotificationView> getNotificationViews() {
        return notificationViews;
    }

    public Date getLatestNotificationCreatedAtDate() {
        return latestNotificationCreatedAtDate;
    }

    public String getLatestNotificationCreatedAtString() {
        return latestNotificationCreatedAtString;
    }

}
