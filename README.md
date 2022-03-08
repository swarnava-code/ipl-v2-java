# IPL Project

## Play with CSV file using Java
1. Number of matches played per year of all the years in IPL.
2. Number of matches won of all teams over all the years of IPL.
3. For the year 2016 get the extra runs conceded per team.
4. For the year 2015 get the top economical bowlers.
5. Create your own scenario.


## Some important command to check mysql db from terminal
1. $ service mysql status
2. $ service mysql start
3. $ service mysql stop
4. $ mysql –u `my_username` –p

     Enter passsword:`my_password`

5. mysql> create database `zs_db`;
6. mysql> SHOW DATABASES;
7. mysql> USE `MY_SCHEMA_NAME`
8. mysql> SHOW TABLES;
9. mysql> select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME='`tableName`';


## Some important command to check pgsql db from terminal
1. $ psql --version
2. To switch pgsql terminal:

     $ sudo -u `postgres` psql

     [sudo] password for swarnava:`my_system_pwd`

3. To list the databases:

     =# select datname from pg_database;

4. To connect with the database:

     =# \c `database_name`;

5. To list the tables:

     =# \dt;
