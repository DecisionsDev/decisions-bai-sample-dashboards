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

import com.ibm.json.java.JSONObject;

import dataGenerator.DataShipmentGenerator;
/**
 *  Shipment Pricing input parameters generator:
 *  it delegates to the appropriate data generator each parameter creation.
 *
 */
public class ShipmentPricingGenerator extends DecisionServiceGenerator {
	private static final String SHIPMENTRULEAPP = "Shipment_Pricing_RuleApp";
	private static final String SHIPMENTRULESET = "Shipment_Pricing";

	public ShipmentPricingGenerator(String name) {
		super(name, SHIPMENTRULEAPP, SHIPMENTRULESET);
	}
	@Override
	public JSONObject generateRulesetParams() {
		JSONObject rulesetParams = new JSONObject();
		DataShipmentGenerator shipmentFull = new DataShipmentGenerator();
		rulesetParams.put("Customer", shipmentFull.getCustomer());
		JSONObject shipment = new JSONObject();
		shipment.put("weight", shipmentFull.getWeight());
		shipment.put("size", shipmentFull.getSize());
		rulesetParams.put("Shipment", shipment);
		JSONObject method = new JSONObject();
		method.put("pickup", shipmentFull.getPickup());
		method.put("transport", shipmentFull.getTransport());
		method.put("nextDay", shipmentFull.getNextDay());
		rulesetParams.put("Method", method);
		rulesetParams.put("Distance", shipmentFull.getDistance());		
		return rulesetParams;
	}

}
