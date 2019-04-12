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
package dataGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to generate the shipment pricing input data.
 */
public class DataShipmentGenerator extends DataGenerator {
	private static final List<String> CUSTOMERS = new ArrayList<>(Arrays.asList("Bronze", "Silver", "Gold"));
	private static final List<String> SIZES = new ArrayList<>(Arrays.asList("Letter", "Pack1", "Pack2", "Pack3"));
	private static final List<String> METHODS = new ArrayList<>(Arrays.asList("Pickup", "Dropoff"));
	private static final List<String> TRANSPORTS = new ArrayList<>(Arrays.asList("Air", "Ground"));
	
	private String customer;
	private String size;
	private String pickup;
	private String transport;
	private double weight;
	private boolean nextDay;
	private int distance;
	
	public DataShipmentGenerator() {
		customer = generateString(CUSTOMERS);
		size = generateString(SIZES);
		pickup = generateString(METHODS);
		transport = generateString(TRANSPORTS);
		weight = generateInt(2, 9) * 0.1;
		nextDay = generateBoolean();
		distance = generateInt(1, 12, 100);
	}
	public String getCustomer() {
		return customer;
	}

	public double getWeight() {
		return weight;
	}

	public String getSize() {
		return size;
	}

	public String getPickup() {
		return pickup;
	}

	public String getTransport() {
		return transport;
	}

	public boolean getNextDay() {
		return nextDay;
	}

	public int getDistance() {
		return distance;
	}

}
