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
package com.synopsys.integration.blackduck.codelocation;

import java.util.Set;
import java.util.stream.Collectors;

import com.synopsys.integration.blackduck.codelocation.bdioupload.UploadBatch;
import com.synopsys.integration.blackduck.codelocation.bdioupload.UploadBatchOutput;
import com.synopsys.integration.blackduck.codelocation.bdioupload.UploadRunner;
import com.synopsys.integration.blackduck.codelocation.bdioupload.UploadTarget;
import com.synopsys.integration.blackduck.exception.HubIntegrationException;

public class BdioUploadCodeLocationCreationRequest extends CodeLocationCreationRequest<UploadBatchOutput> {
    private final UploadRunner uploadRunner;
    private final UploadBatch uploadBatch;

    public BdioUploadCodeLocationCreationRequest(final UploadRunner uploadRunner, final UploadBatch uploadBatch) {
        this.uploadRunner = uploadRunner;
        this.uploadBatch = uploadBatch;
    }

    @Override
    public Set<String> getCodeLocationNames() {
        return uploadBatch.getUploadTargets()
                       .stream()
                       .map(UploadTarget::getCodeLocationName)
                       .collect(Collectors.toSet());
    }

    @Override
    public UploadBatchOutput executeRequest() throws HubIntegrationException {
        return uploadRunner.executeUploads(uploadBatch);
    }

}
