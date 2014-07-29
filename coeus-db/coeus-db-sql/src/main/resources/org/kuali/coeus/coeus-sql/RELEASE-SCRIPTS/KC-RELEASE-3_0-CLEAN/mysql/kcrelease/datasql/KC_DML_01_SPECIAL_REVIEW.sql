delimiter /
TRUNCATE TABLE SPECIAL_REVIEW
/
INSERT INTO SPECIAL_REVIEW (SPECIAL_REVIEW_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('1','Human Subjects','admin',NOW(),UUID(),1)
/
INSERT INTO SPECIAL_REVIEW (SPECIAL_REVIEW_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('2','Animal Usage','admin',NOW(),UUID(),1)
/
INSERT INTO SPECIAL_REVIEW (SPECIAL_REVIEW_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('3','Recombinant DNA','admin',NOW(),UUID(),1)
/
INSERT INTO SPECIAL_REVIEW (SPECIAL_REVIEW_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('4','Radioactive Isotopes','admin',NOW(),UUID(),1)
/
INSERT INTO SPECIAL_REVIEW (SPECIAL_REVIEW_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('5','Biohazard Materials','admin',NOW(),UUID(),1)
/
INSERT INTO SPECIAL_REVIEW (SPECIAL_REVIEW_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('6','International Programs','admin',NOW(),UUID(),1)
/
INSERT INTO SPECIAL_REVIEW (SPECIAL_REVIEW_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('7','Space Change','admin',NOW(),UUID(),1)
/
INSERT INTO SPECIAL_REVIEW (SPECIAL_REVIEW_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('8','TLO Review - No conflict (A)','admin',NOW(),UUID(),1)
/
INSERT INTO SPECIAL_REVIEW (SPECIAL_REVIEW_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('9','TLO review - Reviewed, no conflict (B1)','admin',NOW(),UUID(),1)
/
INSERT INTO SPECIAL_REVIEW (SPECIAL_REVIEW_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('10','TLO Review - Potential Conflict (B2)','admin',NOW(),UUID(),1)
/
INSERT INTO SPECIAL_REVIEW (SPECIAL_REVIEW_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('11','TLO PR-Previously Reviewed','admin',NOW(),UUID(),1)
/
INSERT INTO SPECIAL_REVIEW (SPECIAL_REVIEW_CODE,DESCRIPTION,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('12','Foundation Relations','admin',NOW(),UUID(),1)
/
delimiter ;