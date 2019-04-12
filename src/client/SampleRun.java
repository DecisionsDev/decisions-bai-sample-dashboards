/*
* Copyright IBM Corp. 1987, 2019
* 
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
* 
* http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
* 
**/
package client;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import serviceGenerator.DecisionServiceGenerator;

/**
 *  The SampleRun class is the sample main entry point:
 *     - it decodes the parameters
 *     - it delegates to the DecisionServiceGenerator instance the generation of the  data.
 *     - it delegates to the JavaClient instance the ruleset execution and the properties settings.
 * 
 */
public class SampleRun {
	private static final String EXECUTE = "EXECUTE";
	private static final String GENERATE = "GENERATE";
	private static final String DEFAULTFILENAME = "data.json";
	private static final String USAGE = "USAGE";
	private static final String SETPROPERTIES = "SETPROPERTIES";
	private static final String RULEAPP1 = "test_deployment";
	private static final String RULESET1 = "loan_validation_with_score_and_grade";
	private static final String RULEAPP2 = "Shipment_Pricing_RuleApp";
	private static final String RULESET2 = "Shipment_Pricing";
	private String m_command = EXECUTE;
	private String m_fileName = null;
	private int m_executionCount = 50;

	private DecisionServiceGenerator m_service = null;
	private JavaClient m_client = null;

	/**
	 * This constructor decodes the provided parameters. 
	 * It initializes the JavaClient instance.
	 * 
	 * @param args
	 */
	public SampleRun(String[] args) {
		m_client = new JavaClient();
		initParams(args);
	}

	/**
	 * Executes the requested command
	 */
	public void run() {
		if (m_command.equals(USAGE)) {
			printUsage();
			return;
		}
		if (m_command.equalsIgnoreCase(SETPROPERTIES)) {
			setProperties();
			return;
		}
		if (m_command.equalsIgnoreCase(GENERATE)) {
			JSONArray params = generateData();
			if (!validFileName())
				m_fileName = DEFAULTFILENAME;
			writeParamsOnFile(params);
			return;
		}
		if (m_command.equalsIgnoreCase(EXECUTE)) {
			JSONArray params;
			if (validFileName()) {
				params = readParamsFromFile();
			} else {
				params = generateData();
				m_fileName = DEFAULTFILENAME;
				writeParamsOnFile(params);
			}
			execute(params);
		}
	}

	private void setProperties() {
		m_client.setBAIProperties(RULEAPP1, RULESET1);
		m_client.setBAIProperties(RULEAPP2, RULESET2);
	}

	private JSONArray generateData() {
		JSONArray params = new JSONArray(m_executionCount);
		for (int i = 0; i < m_executionCount; i++)
			params.add(m_service.generateRulesetParams());
		return params;
	}

	private void execute(JSONArray params) {
		for (int i = 0; i < m_executionCount; i++) {
			JSONObject rulesetParams = (JSONObject) params.get(i);
			try {
				System.out.println("** Execution " + rulesetParams.serialize());
			} catch (IOException e) {
				e.printStackTrace();
			}
			m_client.executeRuleSet(m_service.getRuleAppName(), m_service.getRuleSetName(), rulesetParams);
		}
	}

	/** Main */
	public static void main(String[] args) {
		// Initialization gives credentials
		SampleRun client = new SampleRun(args);
		client.run();
	}

	/*
	 * Utilities
	 */
	private void printUsage() {
		System.out.println("Usage: SampleRun COMMAND DECISIONSERVICE EXECUTION_SUCCESSFUL EXECUTION_FAILURE FILENAME");
		System.out.println("where COMMAND can be SETPROPERTIES, GENERATE, EXECUTE or USAGE  - default is EXECUTE");
		System.out.println("      for SETPROPERTIES no other parameters are required");
		System.out
				.println("      DECISIONSERVICE is one of LoanValidation, ShipmentPricing - default is LoanValidation");
		System.out.println("      EXECUTION_COUNT is the count of execution - Default is 50");
		System.out.println("      FILENAME in generate mode is the filename where to write  - Default is data.json");
		System.out.println("      FILENAME in execute mode is the filename where to read from ");
		System.out.println("              - Default is empty: data will be generated and saved in data.json");
		System.out.println("      GOODCASE when given change the quality of generated data -loan approved");
	}

	private JSONArray readParamsFromFile() {
		File file = new File(m_fileName);
		try {
			Reader reader = new FileReader(file);
			JSONArray params = (JSONArray) JSON.parse(reader);
			return params;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean validFileName() {
		if (m_fileName == null || m_fileName.isEmpty())
			return false;
		return true;
	}

	private void writeParamsOnFile(JSONArray params) {
		try (FileWriter file = new FileWriter(m_fileName)) {
			file.write(params.serialize(true));
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initParams(String[] args) {
		if (args.length > 5)
			printUsage();
		if (args.length > 0)
			m_command = args[0];
		if (args.length > 1) {
			m_service = DecisionServiceGenerator.CreateService(args[1]);
		}
		if (args.length > 2)
			m_executionCount = Integer.parseInt(args[2]);
		if (args.length > 3)
			m_fileName = args[3];
		if (args.length > 4)
			m_service.isGoodCase(true);
	}

}
