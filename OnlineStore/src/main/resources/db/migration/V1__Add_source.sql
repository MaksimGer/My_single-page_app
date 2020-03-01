create sequence hibernate_sequence start 8 increment 1;

create table attributes (
   id int8 not null,
   name varchar(255),
   type int2,
   primary key (id)
);

create table categories (
   id int8 not null,
   name varchar(255),
   parent_category int8,
   primary key (id)
);

create table categories_attributes (
  categories_id int8 not null,
  attributes_id int8 not null,
  primary key (categories_id, attributes_id)
);

create table objects (
    id int8 not null,
    name varchar(255),
    category_id int8,
    parent_id int8,
    primary key (id)
);

create table params (
   id int8 not null,
   value varchar(255),
   attribute_id int8,
   product_id int8,
   primary key (id)
);

create table user_roles (
   user_id int8 not null,
   roles varchar(255)
);

create table usr (
    id int8 not null,
    active boolean not null,
    password varchar(255),
    username varchar(255),
    primary key (id)
);

/*--------------------------------- FK -------------------------------------*/
alter table if exists categories
    add constraint category_category_fk
    foreign key (parent_category) references categories;

alter table if exists categories_attributes
    add constraint categoryAttribute_attribute_fk
    foreign key (attributes_id) references attributes;

alter table if exists categories_attributes
    add constraint categoryAttribute_category_fk
    foreign key (categories_id) references categories;

alter table if exists objects
    add constraint object_category_fk
    foreign key (category_id) references categories;

alter table if exists objects
    add constraint object_object_fk
    foreign key (parent_id) references objects;

alter table if exists params
    add constraint param_attribute_fk
    foreign key (attribute_id) references attributes;

alter table if exists params
    add constraint param_object_fk
    foreign key (product_id) references objects;

alter table if exists user_roles
    add constraint userRole_user_fk
    foreign key (user_id) references usr;

/*------------------------------- INSERT -----------------------------------*/
INSERT into attributes (id, name, type) values
    (1, 'author', 1),
    (2, 'text', 1),
    (3, 'price', 2),
    (4, 'count', 2);

INSERT into categories (id, name) values
    (5, 'comment'),
    (6, 'product');

INSERT into categories_attributes (categories_id, attributes_id) values
    (5, 1),
    (5, 2),
    (6, 3),
    (6, 4);

INSERT into usr (id, active, password, username) values
    (7, true, 'admin', 'admin');

INSERT into user_roles (user_id, roles) values
    (7, 'ADMIN');