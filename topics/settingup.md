
# Setting up the sample

You set up this sample by using the Samples Console and Kibana.

You do the following work to set up the sample:

-   Define the Business Automation Insights configuration properties.
-   Run an automatic setup to  enable the Business Automation Insights events emitter for two rulesets.
-   Enable input and output data for these rulesets in Kibana.
-   Import the sample dashboards into Kibana.

## Step 1: Defining the Business Automation Insights properties

You define the connection to your Business Automation Insights instance. The settings are defined in the property file *config.properties*, which you must complete with the values that correspond to your Business Automation Insights installation (see [Configuring the ODM event emitter](https://www.ibm.com/support/knowledgecenter/SSQP76_8.10.x/com.ibm.odm.distrib.config/topics/con_config_bai.html)). 

1.   Open *SampleInstall*/data/config.properties.

2.   Enter the following values:

-   *com.ibm.rules.bai.plugin.topic*: Enter the name of the Kafka topic with the ingress role as defined during the prerequisite Business Automation Insights installation.
-   *com.ibm.rules.bai.plugin.kafka.bootstrap.servers*: Enter the Kafka bootstrap servers of the Kafka server configured during the prerequisite.

3.   If security is enabled on your Business Automation Insights installation, set the property values for the security settings (see [Securing ODM emitter communications to Kafka](https://www.ibm.com/support/knowledgecenter/en/SSYHZ8_18.0.x/com.ibm.dba.bai/topics/tsk_bai_security_comm_top_odm_emitter_to_kafka.html)). If not, keep only the properties set in substep 2.

4.   Save your changes.

## Step 2: Setting up the DecisionService application to enable the Business Automation Insights events emitter.

You use a setup command to prepare the sample.

1.   Open the Samples Console (see [Running samples from the Samples Console](https://www.ibm.com/support/knowledgecenter/SSQP76_8.10.x/com.ibm.odm.distrib.samples/smp_console_topics/tsk_smpcons_running.html)).
2.   Switch to the Samples Commands tab.
3.   Expand **Samples Commands > Rule Execution Server > BAI custom dashboards**.
4.   Double-click the **setup** command. It does the following tasks: 
    
-   Creates a new samples server profile. 
-   Stops the server.
-   Uses the **resetup** command to repackage the DecisionService application to enable the Business Automation Insights events emitter.
-   Starts the server.
-   Deploys the Loan Validation Service and Shipment Pricing projects from Decision Center to Rule Execution Server.
-   Sets the  required properties on the deployed rulesets to enable Business Automation Insights event emission, and include the input and output data in the events.
-   Runs each ruleset once.
    
5.   Wait for the following message before continuing. It might take some time to complete.
 
    Completed: setup
    BUILD SUCCESSFUL

## Step 3 (Optional): Checking that the automatic setup ran correctly

You check the results from running the setup command.

1.   Navigate to *LibertyInstall*/usr/servers/odm81010/apps, which contains all the sample web applications for ODM.
 The **DecisionService.war** was modified after the others applications because it was repackaged.
2.   Open *LibertyInstall*/usr/servers/odm81010/logs/console.log.
3.   Search for the string DecisionService to find the following message:

    CWWKZ0001I: Application DecisionService started 
 
 The lines before this message provide the details about starting Kafka:
 
    INFO org.apache.kafka.common.security.authenticator.AbstractLogin - Successfully logged in.

**Note:** If you run your Liberty server on a non-English local, the messages are translated.

 4.   Log into the Rule Execution Server console at http://localhost:9090/res, and use **odmAdmin** as the user name and password. The Explorer tab contains two RuleApps:
 
 -    Shipment_Pricing_RuleApp
 -    test_deployment
 
5.   Open the */test_deployment/1.0/loan_validation_with_score_and_grade/1.0* ruleset.
6.   Check that the following properties are all set to true:
  
- bai.emitter.enabled
- bai.emitter.input
- bai.emitter.output

## Step 4: Enabling input and output

You make sure that the input and output data for the Loan Validation Service and Shipment Pricing rulesets is ready to be displayed in Kibana.

**Note:** Another user might already have set up this operation in doing this sample. This is a mandatory step that must be done to see the input and output data of the rulesets in Kibana.

1.   In the main Kibana menu, click **Management**.
2.   Click **Index Patterns** and select *odm-timeseries* in the Pattern list. Make sure that you are in the Fields tab.
3.   Enter "Shipment" in the Filter field. 
4.   Click the **Refresh field list** button. You should get several fields that contain the string Shipment.  

## Step 5: Importing the sample dashboards into Kibana

You import the loan and shipment dashboards into in Kibana.

**Note:** First check whether the dashboards are already in Kibana. In the Kibana menu, click **Dashboard** and check the list of dashboards for the Sample Decisions Loan Dashboard and the Sample Decisions Shipment Dashboard. If you find the dashboards in Kibana, you can skip this step.

1.   In a browser, open Kibana. 
2.   In the main Kibana menu, click **Management**, and then **Saved Objects**.
3.   Click **Import**.
4.   In the Import saved objects panel, click the **File upload** button and browse to *SampleInstall*/dasboards/loanDashboard.json.
5.   Click the **Import** button at the bottom of the page.
6.   Close the Import saved objects panel.
7.   Do the same for shipmentDashboard.json.
8.   In the Kibana menu, click **Dashboard** and check the list of dashboards for the Sample Decisions Loan Dashboard and the Sample Decisions Shipment Dashboard. 

## Next

In the next task, you run the rulesets several times and look at the emitted events in the dashboards.

[**Next page**](../topics/running.md)

[**Main page**](../README.md)

Version 1.2.1
