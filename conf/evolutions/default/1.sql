# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table event (
  id                        bigint not null,
  title                     varchar(255),
  created_by_id             bigint,
  created_date              timestamp,
  start_time                timestamp,
  end_time                  timestamp,
  constraint pk_event primary key (id))
;

create table linked_account (
  id                        bigint not null,
  user_id                   bigint,
  provider_user_id          varchar(255),
  provider_key              varchar(255),
  constraint pk_linked_account primary key (id))
;

create table org_unit (
  id                        bigint not null,
  parent_id                 bigint,
  name                      varchar(255),
  rss_feed                  varchar(255),
  ext_calendar_id           varchar(255),
  events_color              varchar(255),
  calendar_id               varchar(255),
  slug                      varchar(255),
  path                      varchar(255),
  org_unit_type             integer,
  created_by_id             bigint,
  created                   timestamp not null,
  constraint ck_org_unit_org_unit_type check (org_unit_type in (0,1)),
  constraint pk_org_unit primary key (id))
;

create table security_role (
  id                        bigint not null,
  role_name                 varchar(255),
  constraint pk_security_role primary key (id))
;

create table signup (
  id                        bigint not null,
  event_id                  bigint,
  person_id                 bigint,
  answer                    varchar(1),
  answered_date             timestamp,
  comment                   varchar(255),
  constraint ck_signup_answer check (answer in ('Y','N')),
  constraint pk_signup primary key (id))
;

create table team_player (
  id                        bigint not null,
  party_id                  bigint,
  player_id                 bigint,
  signed_on                 timestamp,
  number                    integer,
  constraint pk_team_player primary key (id))
;

create table token_action (
  id                        bigint not null,
  token                     varchar(255),
  target_user_id            bigint,
  type                      varchar(2),
  created                   timestamp,
  expires                   timestamp,
  constraint ck_token_action_type check (type in ('EV','PR')),
  constraint uq_token_action_token unique (token),
  constraint pk_token_action primary key (id))
;

create table users (
  id                        bigint not null,
  email                     varchar(255),
  name                      varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  last_login                timestamp,
  date_of_birth             timestamp,
  active                    boolean,
  picture_url               varchar(255),
  contact_id                bigint,
  email_validated           boolean,
  constraint pk_users primary key (id))
;

create table user_permission (
  id                        bigint not null,
  value                     varchar(255),
  constraint pk_user_permission primary key (id))
;


create table org_unit_event (
  org_unit_id                    bigint not null,
  event_id                       bigint not null,
  constraint pk_org_unit_event primary key (org_unit_id, event_id))
;

create table users_security_role (
  users_id                       bigint not null,
  security_role_id               bigint not null,
  constraint pk_users_security_role primary key (users_id, security_role_id))
;

create table users_user_permission (
  users_id                       bigint not null,
  user_permission_id             bigint not null,
  constraint pk_users_user_permission primary key (users_id, user_permission_id))
;
create sequence event_seq;

create sequence linked_account_seq;

create sequence org_unit_seq;

create sequence security_role_seq;

create sequence signup_seq;

create sequence team_player_seq;

create sequence token_action_seq;

create sequence users_seq;

create sequence user_permission_seq;

alter table event add constraint fk_event_createdBy_1 foreign key (created_by_id) references users (id) on delete restrict on update restrict;
create index ix_event_createdBy_1 on event (created_by_id);
alter table linked_account add constraint fk_linked_account_user_2 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_linked_account_user_2 on linked_account (user_id);
alter table org_unit add constraint fk_org_unit_parent_3 foreign key (parent_id) references org_unit (id) on delete restrict on update restrict;
create index ix_org_unit_parent_3 on org_unit (parent_id);
alter table org_unit add constraint fk_org_unit_createdBy_4 foreign key (created_by_id) references users (id) on delete restrict on update restrict;
create index ix_org_unit_createdBy_4 on org_unit (created_by_id);
alter table signup add constraint fk_signup_event_5 foreign key (event_id) references event (id) on delete restrict on update restrict;
create index ix_signup_event_5 on signup (event_id);
alter table signup add constraint fk_signup_person_6 foreign key (person_id) references users (id) on delete restrict on update restrict;
create index ix_signup_person_6 on signup (person_id);
alter table team_player add constraint fk_team_player_party_7 foreign key (party_id) references org_unit (id) on delete restrict on update restrict;
create index ix_team_player_party_7 on team_player (party_id);
alter table team_player add constraint fk_team_player_player_8 foreign key (player_id) references users (id) on delete restrict on update restrict;
create index ix_team_player_player_8 on team_player (player_id);
alter table token_action add constraint fk_token_action_targetUser_9 foreign key (target_user_id) references users (id) on delete restrict on update restrict;
create index ix_token_action_targetUser_9 on token_action (target_user_id);
alter table users add constraint fk_users_contact_10 foreign key (contact_id) references users (id) on delete restrict on update restrict;
create index ix_users_contact_10 on users (contact_id);



alter table org_unit_event add constraint fk_org_unit_event_org_unit_01 foreign key (org_unit_id) references org_unit (id) on delete restrict on update restrict;

alter table org_unit_event add constraint fk_org_unit_event_event_02 foreign key (event_id) references event (id) on delete restrict on update restrict;

alter table users_security_role add constraint fk_users_security_role_users_01 foreign key (users_id) references users (id) on delete restrict on update restrict;

alter table users_security_role add constraint fk_users_security_role_securi_02 foreign key (security_role_id) references security_role (id) on delete restrict on update restrict;

alter table users_user_permission add constraint fk_users_user_permission_user_01 foreign key (users_id) references users (id) on delete restrict on update restrict;

alter table users_user_permission add constraint fk_users_user_permission_user_02 foreign key (user_permission_id) references user_permission (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists event;

drop table if exists org_unit_event;

drop table if exists linked_account;

drop table if exists org_unit;

drop table if exists security_role;

drop table if exists signup;

drop table if exists team_player;

drop table if exists token_action;

drop table if exists users;

drop table if exists users_security_role;

drop table if exists users_user_permission;

drop table if exists user_permission;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists event_seq;

drop sequence if exists linked_account_seq;

drop sequence if exists org_unit_seq;

drop sequence if exists security_role_seq;

drop sequence if exists signup_seq;

drop sequence if exists team_player_seq;

drop sequence if exists token_action_seq;

drop sequence if exists users_seq;

drop sequence if exists user_permission_seq;

