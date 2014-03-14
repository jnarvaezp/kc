DELIMITER /
INSERT INTO KRIM_PERM_ID_BS_S VALUES(NULL)
/
INSERT INTO KRIM_PERM_T (PERM_ID,PERM_TMPL_ID,NMSPC_CD,NM,DESC_TXT,ACTV_IND,OBJ_ID,VER_NBR)
VALUES ((SELECT (MAX(ID)) FROM KRIM_PERM_ID_BS_S),(SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-IDM' AND NM = 'View Document Section'),'KC-IACUC','View IACUC Protocol Online Review Comments','View IACUC Protocol Online Review Comments','Y',UUID(),1)
/
INSERT INTO KRIM_ROLE_PERM_ID_BS_S VALUES(NULL)
/
INSERT INTO KRIM_ROLE_PERM_T
(ROLE_PERM_ID,ROLE_ID,PERM_ID,ACTV_IND,VER_NBR,OBJ_ID) VALUES
((SELECT (MAX(ID)) FROM KRIM_ROLE_PERM_ID_BS_S),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'IACUC Administrator' AND NMSPC_CD='KC-UNT'),
(SELECT (MAX(ID)) FROM KRIM_PERM_ID_BS_S),'Y',1,UUID())
/
INSERT INTO KRIM_PERM_ID_BS_S VALUES(NULL)
/
INSERT INTO KRIM_PERM_T (PERM_ID,PERM_TMPL_ID,NMSPC_CD,NM,DESC_TXT,ACTV_IND,OBJ_ID,VER_NBR)
VALUES ((SELECT (MAX(ID)) FROM KRIM_PERM_ID_BS_S),(SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-IDM' AND NM = 'Edit Document Section'),'KC-IACUC','Maintain IACUC Protocol Online Review Comments','Maintain IACUC Protocol Online Review Comments','Y',UUID(),1)
/
INSERT INTO KRIM_ROLE_PERM_ID_BS_S VALUES(NULL)
/
INSERT INTO KRIM_ROLE_PERM_T
(ROLE_PERM_ID,ROLE_ID,PERM_ID,ACTV_IND,VER_NBR,OBJ_ID) VALUES
((SELECT (MAX(ID)) FROM KRIM_ROLE_PERM_ID_BS_S),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'IACUC Administrator' AND NMSPC_CD='KC-UNT'),
(SELECT (MAX(ID)) FROM KRIM_PERM_ID_BS_S),'Y',1,UUID())
/
INSERT INTO KRIM_PERM_ID_BS_S VALUES(NULL)
/
INSERT INTO KRIM_PERM_T (PERM_ID,PERM_TMPL_ID,NMSPC_CD,NM,DESC_TXT,ACTV_IND,OBJ_ID,VER_NBR)
VALUES ((SELECT (MAX(ID)) FROM KRIM_PERM_ID_BS_S),(SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-IDM' AND NM = 'Edit Document Section'),'KC-IACUC','Maintain IACUC Protocol Online Reviews','Maintain IACUC Protocol Online Reviews','Y',UUID(),1)
/
INSERT INTO KRIM_ROLE_PERM_ID_BS_S VALUES(NULL)
/
INSERT INTO KRIM_ROLE_PERM_T
(ROLE_PERM_ID,ROLE_ID,PERM_ID,ACTV_IND,VER_NBR,OBJ_ID) VALUES
((SELECT (MAX(ID)) FROM KRIM_ROLE_PERM_ID_BS_S),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'IACUC Administrator' AND NMSPC_CD='KC-UNT'),
(SELECT (MAX(ID)) FROM KRIM_PERM_ID_BS_S),'Y',1,UUID())
/
DELIMITER ;