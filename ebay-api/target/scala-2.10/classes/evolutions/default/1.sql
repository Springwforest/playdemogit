# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ebay_account_task (
  id                        integer auto_increment not null,
  account                   varchar(255) not null,
  token                     Text not null,
  end_time_from             varchar(255) not null,
  end_time_to               varchar(255) not null,
  create_time               varchar(255) not null,
  task_status               integer,
  constraint pk_ebay_account_task primary key (id))
;

create table ebay_task_logs (
  id                        integer auto_increment not null,
  account                   varchar(255) not null,
  file_path                 varchar(255) not null,
  create_time               datetime not null,
  constraint pk_ebay_task_logs primary key (id))
;

create table file_package_result (
  result                    tinyint(1) default 0)
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table ebay_account_task;

drop table ebay_task_logs;

drop table file_package_result;

SET FOREIGN_KEY_CHECKS=1;

