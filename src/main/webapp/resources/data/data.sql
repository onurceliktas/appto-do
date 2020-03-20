- CREATE DATABASE todo;
- CREATE TABLE "user"(
   id serial PRIMARY KEY,
   firstName VARCHAR (50) NOT NULL,
   lastName VARCHAR (50) NOT null,
   userName VARCHAR (50) unique not null,
   password VARCHAR (120) not null,
   status int not null
);

- CREATE TABLE role(
   id serial PRIMARY KEY,
   name VARCHAR (50) NOT NULL,
   status INT NOT NULL
);

- CREATE TABLE userRole(
   id serial PRIMARY KEY,
   "user" BIGINT references "user"(id),
   role BIGINT references role(id),
   status int not null
);

- CREATE TABLE task(
   id serial PRIMARY KEY,
   descr TEXT NOT NULL,
   startDate DATE null,
   endDate DATE null,
   taskStatus int not null,
   owner BIGINT references "user"(id),
   status INT
);
