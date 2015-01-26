---
layout: post
title: Configuring a Webserver
date: 2015-01-25 19:45:24
---

### NOTE: I AM NO LONGER USING AWS EC2. My EC2 instance is ridiculously slow. Editing in vim is painful and slow and I can't imagine working with this thing for my video game project.

### All this means is that instead of a LAMP stack, this post will be about what I have on my current server, a LEMP stack.

NGINX
=====

The E in LEMP stands for Nginx, a reverse proxy server that does both websites and even email.

The major difference between Apache and Nginx is that Nginx was built from the ground up for virtual servers and concurrency.

End of the day, it doesn't really matter much whether I go with Nginx or Apache.

Configuring Nginx
=================

The main config file in nginx can be found at /etc/nginx/nginx.conf. There you'll find a default mail and http config.

The default http config will be just fine, all that needs to be done is to set up a server. 

Setting up a server
===================

To set up a server, just go to /etc/nginx/sites-available. The default file will get one started.

In the default file, make sure to set ipv6only=off; not everyone can access IPv6 internet yet.

The root option sets the directory for which the server looks for the files to serve. For instance, if your html is located at /home/website/, set your root to this.

if you want to rename the file, just use mv and specify a different filename.
	
To enable this new server, just use the ln command to symbolically link the file to the sites-enabled directory.

{% highlight bash %}
ln -s /etc/nginx/sites-available/whatever-you-named-it /etc/nginx/sites-enabled/blah-blah-blah
{% endhighlight %}

that's basically it, just restart nginx and your site should load when you nagivate to it's IP.

### Next post, setting up PHP on Nginx
