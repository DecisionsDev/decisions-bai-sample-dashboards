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
import java.util.Calendar;
import java.util.List;

/**
 * Class to generate a borrower with consistent data
 * A parameter goodCase can be provided to generate a borrower with valid SSN and zipcode.
 *
 */
public class DataBorrowerGenerator extends DataGenerator {
	private static final int INCOMEMIN = 8;
	private static final int INCOMEMAX = 80;
	private static final int INCOMEFACTOR = 1000;
	private static final int CREDITMIN = 1;
	private static final int CREDITMAX = 6;
	private static final int CREDITFACTOR = 100;
	private static final int BIRTHYEARMIN = 1850;
	private static final int BIRTHYEARMAX = 2000;
	private static final List<String> FIRSTNAMES = new ArrayList<>(Arrays.asList("John", "Betty", "Paul", "Linda"));
	private static final List<String> LASTNAMES = new ArrayList<>(Arrays.asList("Doe", "Smith", "Wilson", "Dupont"));
	private static final List<String> SSNAREAS = new ArrayList<>(Arrays.asList("123", "89", "456", "587", "918"));
	private static final List<String> SSNGROUPS = new ArrayList<>(Arrays.asList("12", "8", "45", "58", "91"));
	private static final List<String> SSNSERIALS = new ArrayList<>(Arrays.asList("1234", "896", "4569", "5817", "9138"));
	private static final List<String> ZIPCODES = new ArrayList<>(Arrays.asList("12345", "12", "45695", "12587", "74162", "85461"));
	private static final List<String> ZIPCODESOK = new ArrayList<>(Arrays.asList("12345", "12284", "45695", "12587", "74162", "85461"));

	private static final List<String> SSNAREASOK = new ArrayList<>(Arrays.asList("123", "892", "456", "587", "918"));
	private static final List<String> SSNGROUPSOK = new ArrayList<>(Arrays.asList("12", "81", "45", "58", "91"));
	private static final List<String> SSNSERIALSOK = new ArrayList<>(Arrays.asList("1234", "8696", "4569", "5817", "9138"));

	private String   firstName;
	private String   lastName;
	private Calendar birth;
	private String   ssnArea;
	private String   ssnGroup;
	private String   ssnSerial;
	private int      yearlyIncome;
	private String   zipCode ;
	private int      creditScore;
	
	public DataBorrowerGenerator(boolean goodCase) {
		 firstName = generateString(FIRSTNAMES);
		 lastName = generateString(LASTNAMES);
		 ssnArea = generateString(goodCase ? SSNAREASOK : SSNAREAS);
		 ssnGroup = generateString(goodCase ? SSNGROUPSOK : SSNGROUPS);
		 ssnSerial = generateString(goodCase ? SSNSERIALSOK: SSNSERIALS);
		 zipCode = generateString(goodCase ? ZIPCODESOK : ZIPCODES);
		 yearlyIncome = generateInt(INCOMEMIN, INCOMEMAX, INCOMEFACTOR);
		 creditScore = generateInt(CREDITMIN, CREDITMAX, CREDITFACTOR);
		 birth = generateCalendar(BIRTHYEARMIN, BIRTHYEARMAX);
	}



	public String    getFirstName() {
		return firstName;
	}
	
	public String   getLastName() {
		return lastName;
	}
	
	public Calendar getBirthDate() {
		return birth;
	}
	
	public int      getYearlyIncome() {
		return yearlyIncome;
	}
	
	public String   getZipCode() {
		return zipCode;
	}
	
	public int  getCreditScore() {
		return creditScore;
	}

	public Object getSSNArea() {
		return ssnArea;
	}
	public String   getSSNGroup() {
		return ssnGroup;
	}
	public String   getSSNSerial() {
		return ssnSerial;
	}

	public String toString() {
		String result = "Borrower: " + getFirstName() + " " + getLastName() 
		+ " creditScore: " + getCreditScore() + " yearlyIncome: " + getYearlyIncome();
		return result;
	}
	
}
