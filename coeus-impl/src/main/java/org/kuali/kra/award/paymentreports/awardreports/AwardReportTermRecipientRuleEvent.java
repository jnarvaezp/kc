/*
 * Kuali Coeus, a comprehensive research administration system for higher education.
 * 
 * Copyright 2005-2015 Kuali, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kuali.kra.award.paymentreports.awardreports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.coeus.sys.framework.rule.KcDocumentEventBase;
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.award.home.Award;
import org.kuali.rice.krad.rules.rule.BusinessRule;

/**
 * This the AwardPaymentScheduleRuleEvent
 */
public class AwardReportTermRecipientRuleEvent extends KcDocumentEventBase {
    private static final Log LOG = LogFactory.getLog(AwardReportTermRecipientRuleEvent.class);
    
    private Award award;
    private AwardReportTerm parentAwardReportTerm;
    private AwardReportTermRecipient awardReportTermRecipientItem;
    
    public AwardReportTermRecipientRuleEvent(String errorPathPrefix, 
                                            AwardDocument awardDocument,
                                            Award award,
                                            AwardReportTerm parentAwardReportTerm,
                                            AwardReportTermRecipient awardReportTermRecipientItem) {
        super("Payment Schedule item", errorPathPrefix, awardDocument);
        this.award = award;
        this.awardReportTermRecipientItem = awardReportTermRecipientItem;
        this.parentAwardReportTerm = parentAwardReportTerm;
    }

    /**
     * Convenience method to return an Award
     * @return
     */
    public Award getAward() {
        return award;
    }
    
    /**
     * Convenience method to return an AwardDocument
     * @return
     */
    public AwardDocument getAwardDocument() {
        return (AwardDocument) getDocument();
    }
    
    /**
     * This method returns the payment schedule item for validation
     * @return
     */
    public AwardReportTermRecipient getAwardReportTermRecipientItemForValidation() {
        return awardReportTermRecipientItem;
    }   

    @Override
    protected void logEvent() {
        LOG.info("Logging AwardPaymentScheduleRuleEvent");
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class getRuleInterfaceClass() {
        return AwardReportTermRecipientRule.class;
    }

    @Override
    public boolean invokeRuleMethod(BusinessRule rule) {
        return ((AwardReportTermRecipientRule)rule).processAwardReportTermRecipientBusinessRules(this);
    }

    /**
     * Gets the parentAwardReportTerm attribute. 
     * @return Returns the parentAwardReportTerm.
     */
    public AwardReportTerm getParentAwardReportTerm() {
        return parentAwardReportTerm;
    }

    /**
     * Sets the parentAwardReportTerm attribute value.
     * @param parentAwardReportTerm The parentAwardReportTerm to set.
     */
    public void setParentAwardReportTerm(AwardReportTerm parentAwardReportTerm) {
        this.parentAwardReportTerm = parentAwardReportTerm;
    }   
}
