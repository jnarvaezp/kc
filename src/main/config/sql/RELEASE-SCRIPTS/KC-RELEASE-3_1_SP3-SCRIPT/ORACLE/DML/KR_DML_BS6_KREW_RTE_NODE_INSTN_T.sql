DECLARE brch_id NUMBER;
BEGIN
  SELECT KREW_RTE_NODE_S.NEXTVAL INTO brch_id FROM DUAL;
  INSERT INTO KREW_RTE_BRCH_T (RTE_BRCH_ID,NM,VER_NBR) 
    VALUES (brch_id,'PRIMARY',12);
  INSERT INTO KREW_RTE_NODE_INSTN_T (RTE_NODE_INSTN_ID,DOC_HDR_ID,RTE_NODE_ID,BRCH_ID,ACTV_IND,CMPLT_IND,INIT_IND,VER_NBR) 
    VALUES (KREW_RTE_NODE_S.NEXTVAL,(SELECT DOC_HDR_ID FROM KREW_DOC_HDR_T WHERE TTL = 'Proposal Person Certification'),(SELECT RTE_NODE_ID FROM KREW_RTE_NODE_T WHERE DOC_TYP_ID = (SELECT DOC_TYP_ID FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'KcSharedMaintenanceDocument')),brch_id,0,1,0,5);
END;
/
INSERT INTO KREW_INIT_RTE_NODE_INSTN_T (DOC_HDR_ID,RTE_NODE_INSTN_ID) 
  VALUES ((SELECT DOC_HDR_ID FROM KREW_DOC_HDR_T WHERE TTL = 'Proposal Person Certification'),KREW_RTE_NODE_S.CURRVAL);

COMMIT;