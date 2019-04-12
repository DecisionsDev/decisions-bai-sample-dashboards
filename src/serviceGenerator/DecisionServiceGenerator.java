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
package serviceGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.ibm.json.java.JSONObject;

/**
 * Parent class for Decision service input parameters generation: 
 * the fields are the decision service ruleset and ruleapp names and the quality of the generation.
 *
 */
public abstract class DecisionServiceGenerator {
	private static final String MINILOAN = "Miniloan";
	private static final String LOANVAL = "LoanValidation";
	private static final String SHIPMENTPRICING = "ShipmentPricing";
	protected String m_name;
	protected String m_ruleAppName;
	protected String m_rulesetName;
	protected boolean m_goodCase = false;
	
	/**
	 * This static method creates the appropriate DecisionService generator instance according to a name.
	 */
	public static DecisionServiceGenerator CreateService(String name) {
		if (name == null)
			name = LOANVAL;
		if (name.equals(LOANVAL)) return new LoanValidationGenerator(name);
		if (name.equals(MINILOAN)) return new MiniloanGenerator(name);
		if (name.equals(SHIPMENTPRICING)) 
			return new ShipmentPricingGenerator(name);
		return null;
	}
	
	public DecisionServiceGenerator(String name, String ruleappName, String rulesetName) {
		m_name = name;
		m_ruleAppName = ruleappName;
		m_rulesetName = rulesetName;

	}
	
	/**
	 *  Abstract method for the parameters generation.
	 * @return
	 */
	public abstract JSONObject generateRulesetParams();
	
	/**
	 *  Utilities.
	 */
	public  String getRuleAppName() {
		return m_ruleAppName;
	}

	public  String getRuleSetName(){
		return m_rulesetName;
	}

	public void isGoodCase(boolean param) {
		m_goodCase = param;
	}

	protected String makeDateObject(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return formatter.format(date); 
	}

}
