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

import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Parent class for data generation: defines the methods to generate string, int, boolean, date...
 *
 */
public class DataGenerator {
	private Random m_generator;
	
	public DataGenerator () {
		 m_generator = new Random();
	}
	
	public String generateString(List<String> values) {
		int index = m_generator.nextInt(values.size());
		return values.get(index);
	}

	public int generateInt(int min, int max) {
		int value = m_generator.nextInt(max - min);
		return value + min;
	}
	
	public int generateInt(int min, int max, int factor) {
		int value = generateInt(min, max);
		return value * factor;
	}


	public Calendar generateCalendar(int min, int max) {
		Calendar cal = Calendar.getInstance();
		int year = generateInt(min, max);
		int month = generateInt(1, 12);
		int day = generateInt(0, 28);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal;
	}

	public boolean generateBoolean() {
		int num = generateInt(0, 1);
		if (num == 0) return false;
		return true;
	}

}
