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

import java.util.Date;

/**
 * Class to generate a loan with consistent data
 */
public class DataLoanGenerator extends DataGenerator {
	private static final int MONTHLYMIN = 12;
	private static final int MONTHLYMAX = 360;
	private static final int AMOUNTMIN = 2;
	private static final int AMOUNTMAX = 10;
	private static final int AMOUNTFACTOR = 10000;
	private static final int YEARMIN = 2019;
	private static final int YEARMAX = 2030;
	private static final int VALUEMIN = 3;
	private static final int VALUEMAX = 7;

	private int numberOfMonthlyPayments;
	private Date startDate;
	private int amount;
	private double loanToValue;

	public DataLoanGenerator() {
		numberOfMonthlyPayments = generateInt(MONTHLYMIN, MONTHLYMAX);
		amount = generateInt(AMOUNTMIN, AMOUNTMAX, AMOUNTFACTOR);
		startDate = generateCalendar(YEARMIN, YEARMAX).getTime();
		loanToValue = generateInt(VALUEMIN, VALUEMAX) * 0.1;
	}

	public int getNumberOfMonthlyPayments() {
		return numberOfMonthlyPayments;
	}
	public Date getStartDate() {
		return startDate;
	}
	public int getAmount() {
		return amount;
	}
	public double getLoanToValue() {
		return loanToValue;
	}
	
	public String toString() {
		String result = "Loan: "  
		+ " amount: " + getAmount() + " duration: " + getNumberOfMonthlyPayments();
		return result;
	}

}
