---
layout: post
title: Server Side - Database
date: 2015-03-30 10:51:43
---

Without using a Distributed Hash Table, the only way to share data about location is to have a central server.
To store and make this data available will require a database and some type of server side scripting.

MySQL
-----

MySQL is a common database that can scale well with data.
Using queries, a user can store, add, delete and organize data.

## Example queries

To [insert](https://dev.mysql.com/doc/refman/5.0/en/insert.html) data into a table:

	INSERT INTO location (user, latitude, longitude) VALUES("John Doe", 41.75, 72.45);

INSERT is the keyword to insert data INTO a table.
INTO indicates which table to insert into.
The values in the parantheses describes which columns the following values go with. If blank, MySQL will insert values in order from first column to last column.
VALUES indicates what is being inserted.

[Search](https://dev.mysql.com/doc/refman/5.0/en/select.html) a table with a conditional:

	SELECT user FROM location WHERE user = "John Doe";

SELECT means to show data, the variabled following that term will ask which columns in the table to fetch.
FROM and the following variable indicates which table to do this query on.
WHERE means the next statement is a conditional; in the example, I only want to fetch data which has user equal John Doe.

[Delete](https://dev.mysql.com/doc/refman/5.0/en/delete.html) Values from table:

	DELETE FROM location WHERE user = "John Doe";

This query can be dangerous, so it's good to make backups regularly to reverse any damage this may cause.
This one is similar to SELECT, major difference being that this query deletes the values it matches instead of showing them.

Create [table](https://dev.mysql.com/doc/refman/5.0/en/create-table.html).

	CREATE TABLE sometable (col1 datatype, col2 datatype, etc...)

Tables are where values are stored.

PHP
---

PHP is a server side scripting language that allows a user to give the server data to act on and to return dynamic content.
For PHP, Ubuntu offers a package called php5-fpm which is a FastCGI binary for, obviously, PHP;

### Using PHP

In order to use PHP on a webserver, it's required to have some type of common gateway interface, [cgi](https://en.wikipedia.org/wiki/Common_Gateway_Interface).
Once a cgi is installed, such as fastcgi, you may need to configure your webserver to use it.

in [nginx](https://askubuntu.com/questions/134666/what-is-the-easiest-way-to-enable-php-on-nginx) adding this should be sufficent 

	location ~ \.php$
	{
		fastcgi_split_path_info ^(.+\.php)(/.+)$;
		fastcgi_pass unix:/var/run/php5-fpm.sock;
		fastcgi_index index.php;
		include fastcgi_params;
	}

### User Data processing

In PHP there are two major ["superglobals"](https://php.net/manual/en/language.variables.superglobals.php) that can contain user inserted data;
 1. POST
 2. GET

These are also the two major HTTP methods.
POST is the most robust in terms of how much data it can take but can add complexity for simple data communications.

[GET](https://php.net/manual/en/reserved.variables.get.php) on the other hand allows a user to simply insert the data at the end of the URL. everything following a ? will be treated as data to be processed and an & seperated entries. the actual data is specified by a variable and a value like somevar=value

An example:

	example.com/somefile.php?user=johndoe&value=25

for the simplicity, GET is probably easer to work with and more useful for the game project.

### Getting Data From GET

Using htmlspecialchars() in PHP 4 or 5 can take the \_GET superglobal and a variable name (enclosed in brackets) to derive a string.

example, if a user has put user=something:

	$user = htmlspecialchars($_GET["user"]);

$user will equal = "something"
