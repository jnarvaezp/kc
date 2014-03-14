-- ACTIVE COMMITTEE MEMBERS ON PROTOCOL
INSERT INTO KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD)
  VALUES( KRIM_TYP_ID_BS_S.NEXTVAL, SYS_GUID(), 1, 'Derived Role: Active Committee Member On Protocol', 'activeCommitteeMemberOnProtocolDerivedRoleTypeService', 'Y', 'KC-PROTOCOL') 
/

INSERT INTO KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
  VALUES(KRIM_ROLE_ID_BS_S.NEXTVAL, SYS_GUID(), 1, 'Active Committee Member On Protocol', 'KC-PROTOCOL', 'Role members are derived from active committee members on the protocol.', KRIM_TYP_ID_BS_S.CURRVAL, 'Y', SYSDATE) 
/

INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES(KRIM_ROLE_PERM_ID_BS_S.NEXTVAL, SYS_GUID(), 1, KRIM_ROLE_ID_BS_S.CURRVAL, (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KC-PROTOCOL' AND NM = 'View Protocol'),'Y') 
/
  
   