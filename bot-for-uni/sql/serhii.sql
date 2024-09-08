SELECT * FROM telegram_user_cache;

SELECT *FROM statement_cache;

SELECT *FROM statement;

SELECT * FROM statement_info;

UPDATE statement_info
SET status=true,is_ready=false
WHERE id=8;

DELETE FROM statement_info;
-- DROP TABLE statement_info;

DELETE FROM telegram_user_cache ;

DELETE FROM statement;

DELETE FROM statement_cache;

DROP TABLE telegram_user_cache ;
DROP TABLE statement_cache ;


-- INSERT INTO statement_info (id,is_ready,status)
-- VALUES (9,false,true);