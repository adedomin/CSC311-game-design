---
layout: post
title:  Setting up an FTP and Generating A SSL Certificate
date:   2015-01-17 19:00:01
---

Now with my AWS instance up, it's time to make it into a webserver. For the sake of the assignment, I'll go through the steps of how I will do this and also explain some basic Unix-like commands and utilities to accomplish this goal.

Sending files to the Server
---------------------------

There are pretty much two ways to accomplish this:

 1. I could use scp since I have access to my server through ssh.
 2. Set up an FTP server and use an FTP client to transfer files to it

Because I know nothing of window's terminal nor care to set up cygwin, I'm not going to bother using scp. So for this task, I will set up an FTP service on my AWS server.

Setting up an FTP server
========================

Ubuntu offers vsftpd as an ftp service choice, so I'll be using that.

To install it:

{% highlight bash %}
sudo apt-get install vsftpd
{% endhighlight %}

Now that it's installed I have to configure it.

To configure it I'm going to use vim, I could have also used other editors like nano which some claim is easier for beginners.

{% highlight bash %}
sudo vim /etc/vsftpd.conf
{% endhighlight %}

first things first, I want to make sure anonymous login is disabled and it seems by default it is. Scrolling through the config file, I am reminded that I forgot to generate a TLS certificate, so before I go on I'm going to do that now.

Brief Detour - Generating a TLS (SSL) Certificate
===========================================

With a TLS certificate, people can't snoop on what I'm uploading to my server; by default vsftpd is set to use the "snakeoil" cert which is useless.

First, I must generate an RSA key. To do this, I will switch to the root user and do this in the root user's home directory; that way no other user can read the generated private key.

To switch to the root user and the root user's home directory, I run:

{% highlight bash %}
sudo su
cd ~
{% endhighlight %}

Then, for the sake of organization I will create a directory to house a private key and a new cert. To generate the physical key, I will use openssl.

{% highlight bash %}
mkdir -m0700 private
openssl genpkey -algorithm RSA -out private/key.pem -pkeyopt rsa_keygen_bits:4096
{% endhighlight %}

Now with my RSA key I can create a new self signed, TLS certificate:

{% highlight bash %}
openssl req -key private/key.pem -x509 -new -out self-signed-cert.crt
{% endhighlight %}

When running this command I will be prompted for more information
I filled out the different fields as follows:

{% highlight bash %}
You are about to be asked to enter information that will be incorporated
into your certificate request.
What you are about to enter is what is called a Distinguished Name or a DN.
There are quite a few fields but you can leave some blank
For some fields there will be a default value,
If you enter '.', the field will be left blank.
-----
Country Name (2 letter code) [AU]:US
State or Province Name (full name) [Some-State]:Connecticut
Locality Name (eg, city) []:Manchester
Organization Name (eg, company) [Internet Widgits Pty Ltd]:CSC-311 Game Design Project
Organizational Unit Name (eg, section) []:Student Developer
Common Name (e.g. server FQDN or YOUR name) []:Anthony DeDominic
Email Address []:dedominica@my.easternct.edu
{% endhighlight %}

Success, now I have a private key and a self signed cert, all that's needed for TLS connections.

Now I'll move the keys to the more appropriate locations:

 1. /etc/ssl/private/
 2. /etc/ssl/certs/

{% highlight bash %}
mv private/key.pem /etc/ssl/private/my-key.pem
mv self-signed-cert.crt /etc/ssl/certs/my-selfsigned-cert.crt
{% endhighlight %}

Now type exit to log out of the root user and back to the ubuntu user.

Returning to FTP
================

I open up the vsftpd.conf file again, /etc/vsftpd.conf, with vim. I navigate to the bottom of the config file and I set rsa\_cert\_file and rsa\_private\_key\_file equal to my self signed cert and private key location.

To force tls, I added the following to my config file.
{% highlight bash %}
ssl_enable=YES
allow_anon_ssl=NO
force_local_data_ssl=YES

#ssl is deprecated and unsafe
ssl_tlsv1=YES
ssl_sslv2=NO
ssl_sslv3=NO

require_ssl_reuse=NO
ssl_ciphers=HIGH
{% endhighlight %}

Here are some other critical things I have in my config:

{% highlight bash %}
write_enable=YES
chroot_local_user=YES
{% endhighlight %}

After finishing configuring, I simply restart vsftpd and I should be done.

{% highlight bash %}
sudo service vsftpd restart
{% endhighlight %}

Now it's time to define an ftp user or my webserver user

To create a simple user, I just run:

{% highlight bash %}
sudo useradd -m ec2-webserver
sudo passwd ec2-webserver #to set the user's password
{% endhighlight %}

Because I chroot into the home folder, it's important that my user doesn't own it's home folder. Instead, the folders the user can write to should be pre-defined and the root of the home folder owned by root itself.

{% highlight bash %}
sudo chown root:root /home/ec2-webserver && sudo chmod 755 /home/ec2-webserver
sudo mkdir /home/ec2-webserver/css
sudo mkdir /home/ec2-webserver/html
sudo mkdir /home/ec2-webserver/php
sudo mkdir /home/ec2-webserver/files
sudo mkdir /home/ec2-webserver/etc
sudo chown ec2-webserver:ec2-webserver /home/ec2-webserver/*
{% endhighlight %}

### FTP - Other notes

Apparently Amazon AWS's "security groups" makes you define custom rules for what ports to allow inbound traffic on. I disabled mine by allowing all inbound traffic by editing the security group in the EC2 dashboard. IPtables (which is built in linux) is perfectly fine as a firewall, Amazon's is just a nuisance.

Also, Windows explorer's built in ftp client doesn't support ftp with ssl, so you have to use something like filezilla.

Since filezilla is on sourceforge, and I'm using windows (purely for the benefit of other windows users who may need help, since I can't stand windows myself), I'd have to download some malware downloader thing that can load god knows what binaries on my machine; to avoid this, I recommend [chocolatey](https://chocolatey.org) which will allow you to type choco install filezilla without worrying about the malware garbage sourceforge tries to push on windows users.

If you are following along and can't login to your ftp, try setting your pam_service_name=ftp. That fixed my login issues.

for further reading on other server configurations, see [Ubuntu's Server Guide](https://help.ubuntu.com/14.04/serverguide/index.html)
