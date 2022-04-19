-- Script to create the test environment

-- run each of these statements one by one
create schema micronaut_test;

use micronaut_test;

create table parent (
	parent_id int not null auto_increment primary key,
	name varchar(64) default null
);

create table child (
	child_id int not null auto_increment primary key,
 	name varchar(64) default null,
 	parent_id int not null,
 	foreign key (parent_id) references parent(parent_id)
);
