/*
 * Copyright 2005-2014 The Kuali Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.s2s.generator.impl;

import gov.grants.apply.forms.phs398Checklist13V13.PHS398Checklist13Document;
import gov.grants.apply.forms.phs398Checklist13V13.PHS398Checklist13Document.PHS398Checklist13;
import gov.grants.apply.forms.phs398Checklist13V13.PHS398Checklist13Document.PHS398Checklist13.ApplicationType;
import gov.grants.apply.forms.phs398Checklist13V13.PHS398Checklist13Document.PHS398Checklist13.IncomeBudgetPeriod;
import gov.grants.apply.system.globalLibraryV20.HumanNameDataType;
import gov.grants.apply.system.globalLibraryV20.YesNoDataType;
import gov.grants.apply.system.globalLibraryV20.YesNoDataType.Enum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlObject;
import org.kuali.coeus.common.framework.rolodex.Rolodex;
import org.kuali.coeus.common.framework.rolodex.RolodexService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.budget.distributionincome.BudgetProjectIncome;
import org.kuali.kra.budget.document.BudgetDocument;
import org.kuali.kra.proposaldevelopment.bo.DevelopmentProposal;
import org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument;
import org.kuali.kra.questionnaire.answer.Answer;
import org.kuali.kra.questionnaire.answer.AnswerHeader;
import org.kuali.kra.s2s.util.S2SConstants;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.kuali.kra.s2s.util.S2SConstants.FORMVERSION_1_3;

/**
 * 
 * This class is used to generate XML Document object for grants.gov
 * PHS398ChecklistV1.3. This form is generated using XMLBean API's generated by
 * compiling PHS398ChecklistV1.1 schema.
 * 
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
public class PHS398ChecklistV1_3Generator extends PHS398ChecklistBaseGenerator {
	private static final String YNQANSWER_121 = "121";
	private static final Log LOG = LogFactory
			.getLog(PHS398ChecklistV1_3Generator.class);
	List<AnswerHeader> answerHeaders;	
	Enum ynqAnswer;
	/*
	 * This method returns PHS398ChecklistDocument object based on proposal
	 * development document which contains the PHS398ChecklistDocument
	 * informations
	 * ApplicationType,FederalID,ChangeOfPDPI,FormerPDName,ChangeOfInstitution,
	 * FormerInstitutionName,InventionsAndPatents, ProgramIncome and
	 * CertificationExplanation for a particular proposal
	 * 
	 */
	private PHS398Checklist13Document getPHS398Checklist() {
		PHS398Checklist13Document phsChecklistDocument = PHS398Checklist13Document.Factory
				.newInstance();
		PHS398Checklist13 phsChecklist = PHS398Checklist13.Factory.newInstance();
		answerHeaders = getQuestionnaireAnswers(pdDoc.getDevelopmentProposal(), true);
		setPhsCheckListBasicProperties(phsChecklist);
		setFormerPDNameAndIsChangeOfPDPI(phsChecklist);
		setFormerInstitutionNameAndChangeOfInstitution(phsChecklist);
		setIsInventionsAndPatentsAndIsPreviouslyReported(phsChecklist);
		BudgetDocument budgetDoc = null;
		try {
			budgetDoc = proposalBudgetService.getFinalBudgetVersion(pdDoc);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		if (budgetDoc != null && budgetDoc.getBudget() != null) {
			int numPeriods = budgetDoc.getBudget().getBudgetPeriods().size();
			setIncomeBudgetPeriods(phsChecklist, budgetDoc.getBudget()
					.getBudgetProjectIncomes(),numPeriods);
		} else {
			phsChecklist.setProgramIncome(YesNoDataType.N_NO);
		}
		ynqAnswer = getYNQAnswer(YNQANSWER_121);
		phsChecklist.setDisclosurePermission(ynqAnswer);
		phsChecklistDocument.setPHS398Checklist13(phsChecklist);
		return phsChecklistDocument;
	}

	/*
	 * This method will set values to following attributes setFederalID,
	 * setApplicationType ,setFormVersion
	 */
	private void setPhsCheckListBasicProperties(PHS398Checklist13 phsChecklist) {
		phsChecklist.setFormVersion(FORMVERSION_1_3);
		DevelopmentProposal developmentProposal = pdDoc
				.getDevelopmentProposal();
		ApplicationType.Enum applicationEnum = null;
		if (developmentProposal.getProposalTypeCode() != null
				&& Integer.parseInt(developmentProposal.getProposalTypeCode()) < PROPOSAL_TYPE_CODE_6) {
			applicationEnum = ApplicationType.Enum.forInt(Integer
					.valueOf(developmentProposal.getProposalTypeCode()));
		}
		phsChecklist.setApplicationType(applicationEnum);
		String federalId = s2sUtilService.getFederalId(pdDoc);
		if (federalId != null) {
			phsChecklist.setFederalID(federalId);
		}
	}

	/*
	 * This method will set values to income budget periods
	 */
	private static void setIncomeBudgetPeriods(PHS398Checklist13 phsChecklist,
			List<BudgetProjectIncome> projectIncomes, int numPeriods) {
		if (projectIncomes.isEmpty()) {
			phsChecklist.setProgramIncome(YesNoDataType.N_NO);
		} else {
			phsChecklist.setProgramIncome(YesNoDataType.Y_YES);
		}
		phsChecklist.setIncomeBudgetPeriodArray(getIncomeBudgetPeriod(projectIncomes));
	}
	/*
	 * Method to sum up IncomeBudgetPeriod of different period Number
	 */
	private static IncomeBudgetPeriod[] getIncomeBudgetPeriod(
			final List<BudgetProjectIncome> projectIncomes) {
		//TreeMap Used to maintain the order of the Budget periods.
		Map<Integer, IncomeBudgetPeriod> incomeBudgetPeriodMap = new TreeMap<Integer, IncomeBudgetPeriod>();
		BigDecimal anticipatedAmount;
		for (BudgetProjectIncome projectIncome : projectIncomes) {

			Integer budgetPeriodNumber = projectIncome.getBudgetPeriodNumber();
			IncomeBudgetPeriod incomeBudgPeriod = incomeBudgetPeriodMap
					.get(budgetPeriodNumber);
			if (incomeBudgPeriod == null) {
				incomeBudgPeriod = IncomeBudgetPeriod.Factory.newInstance();
				incomeBudgPeriod.setBudgetPeriod(budgetPeriodNumber.intValue());
				anticipatedAmount = BigDecimal.ZERO;
			} else {
				anticipatedAmount = incomeBudgPeriod.getAnticipatedAmount();
			}
			anticipatedAmount = anticipatedAmount.add(projectIncome
					.getProjectIncome().bigDecimalValue());
			incomeBudgPeriod.setAnticipatedAmount(anticipatedAmount);
			String description = getProjectIncomeDescription(projectIncome);
			if (description != null) {
				if (incomeBudgPeriod.getSource() != null) {
					incomeBudgPeriod.setSource(incomeBudgPeriod.getSource()
							+ ";" + description);
				} else {
					incomeBudgPeriod.setSource(description);
				}
			}
			incomeBudgetPeriodMap.put(budgetPeriodNumber, incomeBudgPeriod);
		}
		Collection<IncomeBudgetPeriod> incomeBudgetPeriodCollection = incomeBudgetPeriodMap
				.values();
		return incomeBudgetPeriodCollection.toArray(new IncomeBudgetPeriod[0]);
	}
	/*
	 * This method will get the project income description
	 */
	protected static String getProjectIncomeDescription(BudgetProjectIncome projectIncome) {
		String description = null;
		if (projectIncome.getDescription() != null) {
			if (projectIncome.getDescription().length() > PROJECT_INCOME_DESCRIPTION_MAX_LENGTH) {
				description = projectIncome.getDescription().substring(0,
						PROJECT_INCOME_DESCRIPTION_MAX_LENGTH);
			} else {
				description = projectIncome.getDescription();
			}
		}
		return description;
	}
	/*
	 * This method will set the values to
	 * setIsInventionsAndPatents,setIsPreviouslyReported based on condition
	 */
	private void setIsInventionsAndPatentsAndIsPreviouslyReported(
	        PHS398Checklist13 phsChecklist) {
	    String answer = getAnswer(PROPOSAL_YNQ_QUESTION_118);
	    if (answer != null && !answer.equals(NOT_ANSWERED)) {
	        if (S2SConstants.PROPOSAL_YNQ_ANSWER_Y.equals(answer)) {
	            String explanation = getAnswer(PROPOSAL_YNQ_QUESTION_119);
	            if(explanation != null && !explanation.equals(NOT_ANSWERED)) {
	                if (S2SConstants.PROPOSAL_YNQ_ANSWER_Y.equals(explanation)) {
	                    phsChecklist.setIsInventionsAndPatents(YesNoDataType.Y_YES);
	                    String subQuestionExplanation = getAnswer(PROPOSAL_YNQ_QUESTION_120);
	                    if (subQuestionExplanation != null && !subQuestionExplanation.equals(NOT_ANSWERED)) {
	                        if (S2SConstants.PROPOSAL_YNQ_ANSWER_Y.equals(subQuestionExplanation)) {
	                            phsChecklist.setIsPreviouslyReported(YesNoDataType.Y_YES);  
	                        }else if(S2SConstants.PROPOSAL_YNQ_ANSWER_N.equals(subQuestionExplanation)) {
	                            phsChecklist.setIsPreviouslyReported(YesNoDataType.N_NO);
	                        }
	                    } else {
	                        phsChecklist.setIsPreviouslyReported(null);
	                    }

	                } else if(S2SConstants.PROPOSAL_YNQ_ANSWER_N.equals(explanation)) {
	                    phsChecklist.setIsInventionsAndPatents(YesNoDataType.N_NO);
	                }
	            } else {
	                phsChecklist.setIsInventionsAndPatents(null);
	            }
	        }
	    }
	}

	/*
	 * This method will set the values to setFormerInstitutionName
	 * ,setIsChangeOfInstitution based on condition
	 */
	private void setFormerInstitutionNameAndChangeOfInstitution(
			PHS398Checklist13 phsChecklist) {
	    String answer = getAnswer(PROPOSAL_YNQ_QUESTION_116);
        String explanation = getAnswer(PROPOSAL_YNQ_QUESTION_117);
        
        if (S2SConstants.PROPOSAL_YNQ_ANSWER_Y.equals(answer)) {
            phsChecklist.setIsChangeOfInstitution(YesNoDataType.Y_YES);
            if (explanation != null) {
                phsChecklist.setFormerInstitutionName(explanation);
            }
            else {
                phsChecklist.setFormerInstitutionName(null);
            }   
        } else {
            phsChecklist.setIsChangeOfInstitution(YesNoDataType.N_NO);
        }
	}

	/*
	 * This method will set the values to setFormerPDName ,setIsChangeOfPDPI
	 * based on condition
	 */
	private void setFormerPDNameAndIsChangeOfPDPI(PHS398Checklist13 phsChecklist) {
		String answer = getAnswer(PROPOSAL_YNQ_QUESTION_114);
	    String explanation = getAnswer(PROPOSAL_YNQ_QUESTION_115);
	    if (S2SConstants.PROPOSAL_YNQ_ANSWER_Y.equals(answer)) {
            phsChecklist.setIsChangeOfPDPI(YesNoDataType.Y_YES);
            if (explanation != null) {
                RolodexService rolodexService = KcServiceLocator.getService(RolodexService.class);
                Rolodex rolodex = rolodexService.getRolodex(Integer.valueOf(explanation));
                HumanNameDataType formerPDName = globLibV20Generator
                        .getHumanNameDataType(rolodex);
                if (formerPDName != null
                        && formerPDName.getFirstName() != null
                        && formerPDName.getLastName() != null) {
                    phsChecklist.setFormerPDName(formerPDName);
                }
            }
            else{
                    phsChecklist.setFormerPDName(null);
            }
        } else {
            phsChecklist.setIsChangeOfPDPI(YesNoDataType.N_NO);
        }
    }

	/*
	 * This method will get the YNQ Answer for question id
	 */
	private YesNoDataType.Enum getYNQAnswer(String questionID) {
	    YesNoDataType.Enum answerType = null;
	    String answer = getAnswer(questionID);
	    if (answer != null && !answer.equals(NOT_ANSWERED)) {
	        answerType = "Y".equals(answer) ? YesNoDataType.Y_YES
                : YesNoDataType.N_NO;
	        return answerType;
	    } else {
            return null;
        }
	}
	
	/**
     * 
     * This method is used to get the answer for a particular Questionnaire question
     * question based on the question id.
     * 
     * @param questionId
     *            the question id to be passed.              
     * @return returns the answer for a particular
     *         question based on the question id passed.
     */
	private String getAnswer(String questionId) {
        String answer = null;
        if (answerHeaders != null && !answerHeaders.isEmpty()) {
            for (AnswerHeader answerHeader : answerHeaders) {
                List<Answer> answerDetails = answerHeader.getAnswers();
                for (Answer answers : answerDetails) {
                    if (questionId.equals(answers.getQuestion().getQuestionId())) {
                        answer = answers.getAnswer();
                        return answer;
                    }
                }
            }
        }
        return answer;        
    }

	/**
	 * This method creates {@link XmlObject} of type
	 * {@link PHS398Checklist13Document by populating data from the given
	 * {@link ProposalDevelopmentDocument}
	 * 
	 * @param proposalDevelopmentDocument
	 *            for which the {@link XmlObject} needs to be created
	 * @return {@link XmlObject} which is generated using the given
	 *         {@link ProposalDevelopmentDocument}
	 * @see org.kuali.kra.s2s.generator.S2SFormGenerator#getFormObject(ProposalDevelopmentDocument)
	 */
	public XmlObject getFormObject(
			ProposalDevelopmentDocument proposalDevelopmentDocument) {
		this.pdDoc = proposalDevelopmentDocument;
		return getPHS398Checklist();
	}

	/**
	 * This method type casts the given {@link XmlObject} to the required
	 * generator type and returns back the document of that generator type.
	 * 
	 * @param xmlObject
	 *            which needs to be converted to the document type of the
	 *            required generator
	 * @return {@link XmlObject} document of the required generator type
	 * @see org.kuali.kra.s2s.generator.S2SFormGenerator#getFormObject
	 */
	public XmlObject getFormObject(XmlObject xmlObject) {
		PHS398Checklist13 phsChecklist = (PHS398Checklist13) xmlObject;
		PHS398Checklist13Document phsChecklistDocument = PHS398Checklist13Document.Factory
				.newInstance();
		phsChecklistDocument.setPHS398Checklist13(phsChecklist);
		return phsChecklistDocument;
	}
}
