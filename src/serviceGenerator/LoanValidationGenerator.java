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

import dataGenerator.DataBorrowerGenerator;
import dataGenerator.DataLoanGenerator;

/**
 *  Loan Validation Service input parameters generator:
 *  it delegates to the appropriate data generator each parameter creation.
 *
 */
public class LoanValidationGenerator extends DecisionServiceGenerator {
	private static final String LOANVALIDATIONRULEAPP = "test_deployment";
	private static final String LOANVALIDATIONRULESET = "loan_validation_with_score_and_grade";

	public  LoanValidationGenerator(String name) {
		super(name, LOANVALIDATIONRULEAPP, LOANVALIDATIONRULESET);
	}
	@Override
	public JSONObject generateRulesetParams() {
		JSONObject rulesetParams = new JSONObject();
		rulesetParams.put("loan", generateLoan());
		rulesetParams.put("borrower", generateBorrower());
		return rulesetParams;
	}
	protected JSONObject generateBorrower() {
		DataBorrowerGenerator borrower = new DataBorrowerGenerator(m_goodCase);
		JSONObject borrowerParams = new JSONObject();
		borrowerParams.put("firstName", borrower.getFirstName());
		borrowerParams.put("lastName", borrower.getLastName());
		borrowerParams.put("birth", makeDateObject(borrower.getBirthDate().getTime()));
		borrowerParams.put("creditScore", borrower.getCreditScore());
		borrowerParams.put("yearlyIncome", borrower.getYearlyIncome());
		borrowerParams.put("zipCode", borrower.getZipCode());
		JSONObject SSNParams = new JSONObject();
		SSNParams.put("areaNumber", borrower.getSSNArea());
		SSNParams.put("groupCode", borrower.getSSNGroup());
		SSNParams.put("serialNumber", borrower.getSSNSerial());
		borrowerParams.put("SSN", SSNParams);
		borrowerParams.put("latestBankruptcy", null);
		borrowerParams.put("spouse", null);
		return borrowerParams;
	}
	protected JSONObject generateLoan() {
		DataLoanGenerator loan = new DataLoanGenerator();
		JSONObject loanParams = new JSONObject();
		loanParams.put("numberOfMonthlyPayments", loan.getNumberOfMonthlyPayments());
		loanParams.put("amount", loan.getAmount());
		loanParams.put("startDate", makeDateObject(loan.getStartDate()));
		loanParams.put("loanToValue", loan.getLoanToValue());
		return loanParams;
	}

}
