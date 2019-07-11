# Sample: Business Automation Insights event emission to Kibana dashboards 

## Description

In this sample, you use custom dashboards to monitor decisions in the Loan Validation Service decision service and the Shipment Pricing decision service model.

You do the following operations:

-   Use an automatic setup to enable Operational Decision Manager to use an event emitter in the configuration properties of a Business Automation Insights installation.
-   Use an automatic setup to deploy rulesets and set their properties.
-   Use an automatic setup to execute the rulesets and emit events.
-   Display information from the emitted events in custom Kibana dashboards.

## Audience

This sample is for technical and business users who want to use Kibana custom dashboards to display information emitted from a decision service or a decision service model.

## Time required

15 minutes

## Prerequisites
You must do the following tasks before you can work on this sample:
-   Install the version of WebSphere Application Server Liberty that is included in the Operational Decision Manager installation files. Decompress the **wlp** file in your downloaded images to a separate directory on your computer. You must install Liberty before you install Operational Decision Manager. In this sample, the Liberty directory is referred to as *LibertyInstall*.
-   Install Operational Decision Manager 8.10.1 or later (see [Installing Operational Decision Manager](https://www.ibm.com/support/knowledgecenter/SSQP76_8.10.x/com.ibm.odm.distrib.install/topics/odm_distrib_install.html)). Keep the options for samples and tutorials selected when you do the installation. In this sample, the installation directory is referred to as *ODMInstall*.
-   Install Business Automation Insights 18.0.2 or later. See [Getting started with IBM Business Automation Insights](https://www.ibm.com/support/knowledgecenter/SSYHZ8_18.0.x/com.ibm.dba.bai/topics/tut_getting_started.html) to install Business Automation Insights and IBM Cloud Private, and [Installing IBM Business Automation Insights on Certified Kubernetes](https://github.com/dbamc/cert-kubernetes/tree/master/BAI ) for installation instructions for other Certified Kubernetes platforms in the [Certified Kubernetes Conformance Program](https://landscape.cncf.io/category=platform). During the installation, note the following information for steps in this sample:

    -   The Kafka bootstrap server
    -   The ingress topic name
    -   Security enablement (If security is enabled, you need its configuration and a key file.)
    
-   Download the sample files in this ODMDev GitHub repository. Click **Clone or download**, and then click **Download ZIP**. This operation downloads the entire contents of the GitHub repository. Decompress the contents of the downloaded file to *ODMInstall*/executionserver/samples. This creates a directory that is named *decisions-bai-sample-dashboards-master*, which is referred to in this sample as *SampleInstall*. The compressed files include a sample property file that you edit in the sample.

**Note:** This sample is based on the on-premises version of Operational Decision Manager.

## Table of contents

-   [Setting up the sample](topics/settingup.md)
-   [Sample details](topics/running.md)

## Licensing information

Copyright IBM Corp. 1987, 2019. Licensed to the Apache Software Foundation \(ASF\) under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 \(the "License"\); you may not use this file except in compliance with the License. You may obtain a copy of the License at [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0). Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

[**Next page**](topics/settingup.md)

Version 1.2.1
