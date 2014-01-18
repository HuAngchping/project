exec ctx_ddl.drop_preference ('otasdm_lexer');

drop index IDX_SUBJ_EXT_ID;

drop index IDX_SUBJ_CONTENT_NAME;

drop index IDX_SUBJ_CONTENT_DESC;

drop index IDX_SUBJ_CONTENT_KEY;

drop index IDX_SUBJ_CONTENT_CONT;

exec ctx_ddl.create_preference ('otasdm_lexer', 'BASIC_LEXER');

create index IDX_SUBJ_EXT_ID on SUBJECT(EXTERNAL_ID) indextype is ctxsys.context parameters('lexer otasdm_lexer');

create index IDX_SUBJ_CONTENT_NAME on SUBJECT_CONTENT(NAME) indextype is ctxsys.context parameters('lexer otasdm_lexer');

create index IDX_SUBJ_CONTENT_DESC on SUBJECT_CONTENT(DESCRIPTION) indextype is ctxsys.context parameters('lexer otasdm_lexer');

create index IDX_SUBJ_CONTENT_KEY on SUBJECT_CONTENT(KEYWORDS) indextype is ctxsys.context parameters('lexer otasdm_lexer');

create index IDX_SUBJ_CONTENT_CONT on SUBJECT_CONTENT(CONTENT) indextype is ctxsys.context parameters('lexer otasdm_lexer');

--同步索引
EXEC CTX_DDL.SYNC_INDEX('IDX_SUBJ_EXT_ID', '2M');

EXEC CTX_DDL.SYNC_INDEX('IDX_SUBJ_CONTENT_NAME', '2M');

EXEC CTX_DDL.SYNC_INDEX('IDX_SUBJ_CONTENT_DESC', '2M');

EXEC CTX_DDL.SYNC_INDEX('IDX_SUBJ_CONTENT_KEY', '2M');

EXEC CTX_DDL.SYNC_INDEX('IDX_SUBJ_CONTENT_CONT', '2M');

--优化索引
exec ctx_ddl.optimize_index ('IDX_SUBJ_EXT_ID', 'full');

exec ctx_ddl.optimize_index ('IDX_SUBJ_CONTENT_NAME', 'full');

exec ctx_ddl.optimize_index ('IDX_SUBJ_CONTENT_DESC', 'full');

exec ctx_ddl.optimize_index ('IDX_SUBJ_CONTENT_KEY', 'full');

exec ctx_ddl.optimize_index ('IDX_SUBJ_CONTENT_CONT', 'full');
