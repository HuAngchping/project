/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2008-7-4 16:22:00                            */
/*==============================================================*/


alter table SUBJECT
   drop constraint FK_SUBJECT_REF_PARENT;

alter table SUBJECT_CONTENT
   drop constraint FK_SUJBECT;

alter table SUBJECT_CONTENT
   drop constraint FK_SUB_CONTENT_LOCALE;

alter table SUBJECT_IMAGE
   drop constraint FK_SUBJ_CONT_IMG;

drop index IDX_SUBJ_EXT_ID;

drop index IDX_FK_SUBJECT;

drop table SUBJECT cascade constraints;

drop index IDX_SUBJ_ID_FK2;

drop index IDX_LOCAL_ID_FK2;

drop table SUBJECT_CONTENT cascade constraints;

drop index IDX_FK_CONTENT;

drop table SUBJECT_IMAGE cascade constraints;

drop table SUBJECT_LOCALE cascade constraints;

/*==============================================================*/
/* Table: SUBJECT                                               */
/*==============================================================*/
create table SUBJECT  (
   SUBJECT_ID           NUMBER(20)                      not null,
   PARENT_ID            NUMBER(20),
   EXTERNAL_ID          VARCHAR2(1024)                  not null,
   constraint PK_SUBJECT primary key (SUBJECT_ID)
);

/*==============================================================*/
/* Index: IDX_FK_SUBJECT                                        */
/*==============================================================*/
create index IDX_FK_SUBJECT on SUBJECT (
   PARENT_ID ASC
);

/*==============================================================*/
/* Index: IDX_SUBJ_EXT_ID                                       */
/*==============================================================*/
create index IDX_SUBJ_EXT_ID on SUBJECT (
   EXTERNAL_ID ASC
);

/*==============================================================*/
/* Table: SUBJECT_CONTENT                                       */
/*==============================================================*/
create table SUBJECT_CONTENT  (
   ID                   Number(20)                      not null,
   SUBJECT_ID           NUMBER(20)                      not null,
   LOCALE_ID            VARCHAR(32)                     not null,
   NAME                 VARCHAR2(1024)                  not null,
   DESCRIPTION          CLOB,
   KEYWORDS             VARCHAR2(2000),
   CONTENT              CLOB,
   LAST_UPDATED_TIME    DATE                            not null,
   LAST_UPDATED_BY      DATE,
   constraint PK_SUBJECT_CONTENT primary key (ID)
);

/*==============================================================*/
/* Index: IDX_LOCAL_ID_FK2                                      */
/*==============================================================*/
create index IDX_LOCAL_ID_FK2 on SUBJECT_CONTENT (
   LOCALE_ID ASC
);

/*==============================================================*/
/* Index: IDX_SUBJ_ID_FK2                                       */
/*==============================================================*/
create index IDX_SUBJ_ID_FK2 on SUBJECT_CONTENT (
   SUBJECT_ID ASC
);

/*==============================================================*/
/* Table: SUBJECT_IMAGE                                         */
/*==============================================================*/
create table SUBJECT_IMAGE  (
   IMAGE_ID             Number(20)                      not null,
   CONTENT_ID           Number(20)                      not null,
   MIME_TYPE            VARCHAR2(64)                    not null,
   BINARY               BLOB                            not null,
   DESCRIPTION          VARCHAR2(1024),
   FILENAME             VARCHAR2(255),
   WIDTH                Number(4),
   HEIGTH               Number(4),
   constraint PK_SUBJECT_IMAGE primary key (IMAGE_ID)
);

/*==============================================================*/
/* Index: IDX_FK_CONTENT                                        */
/*==============================================================*/
create index IDX_FK_CONTENT on SUBJECT_IMAGE (
   CONTENT_ID ASC
);

/*==============================================================*/
/* Table: SUBJECT_LOCALE                                        */
/*==============================================================*/
create table SUBJECT_LOCALE  (
   ID                   VARCHAR(32)                     not null,
   LANGUAGE             VARCHAR(64)                     not null,
   COUNTRY              VARCHAR(64),
   constraint PK_SUBJECT_LOCALE primary key (ID)
);

alter table SUBJECT
   add constraint FK_SUBJECT_REF_PARENT foreign key (PARENT_ID)
      references SUBJECT (SUBJECT_ID)
      on delete cascade;

alter table SUBJECT_CONTENT
   add constraint FK_SUJBECT foreign key (SUBJECT_ID)
      references SUBJECT (SUBJECT_ID)
      on delete cascade;

alter table SUBJECT_CONTENT
   add constraint FK_SUB_CONTENT_LOCALE foreign key (LOCALE_ID)
      references SUBJECT_LOCALE (ID)
      on delete cascade;

alter table SUBJECT_IMAGE
   add constraint FK_SUBJ_CONT_IMG foreign key (CONTENT_ID)
      references SUBJECT_CONTENT (ID)
      on delete cascade;



