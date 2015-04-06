---
layout: post
title:  "Setting up and Accessing an AWS EC2 Instance on Windows"
date:   2015-01-15 16:40:01
---

Because the teacher asked me to set up an aws ec2 instance, I have decided to make this post. The purpose of this post is to go step by step on how to set up a free tier Amazon virtual private server.

Before We Begin
---------------

What you need
=============

If you use windows, which is this guide's primary target, you need the following pieces of software, available [here](http://www.chiark.greenend.org.uk/~sgtatham/putty/download.html).

Needed:

 * PuTTY
 * PuTTYgen
 * An Amazon free-tier AWS account

Generating your RSA key
=======================

To make life easier, I highly recommend you generate your own public/private key pair. This way Amazon doesn't have your private key and you could use your key on other servers or services without Amazon having potential access to them.

### **!!!NOTE: ONLY UPLOAD YOUR PUBLIC KEY TO A SERVER OR TO ANYONE ELSE. YOUR SERVER DOES NOT NEED YOUR PRIVATE KEY TO AUTHENTICATE YOU; THAT IS NOT HOW PUBLIC/PRIVATE KEY ENCRYPTION WORKS!!!**

To do this, first open PuTTYgen.exe; then simply click generate. Move your mouse around to generate randomness for the system entropy pool while it generates your keys. Once it generates your keys, simply save your private key. You could password protect it, but it really doesn't matter much. Now keep PuTTYgen open as it also indicates your public key in the text box at the top; you want to save this text to your clipboard.

Log in to [AWS](http://aws.amazon.com/)
---------------

Once you log in to AWS, you should be redirected to the AWS console. There you should see various services available to you. You want to click on the first one on the left, EC2.

Once in the EC2 dashboard, on the left hand side of the page, click the Key Pairs link. Once there, you want to click on the Import Key Pair button. You will be prompted for a name for you key and a text field below will ask for your public key. Go back to PuTTYgen and copy, if you haven't already, the **ENTIRE** copyable text field in the PuTTYgen window. Then paste it into the public key content box.

Your public key should look something like this:
{% highlight bash %}
ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDSeRdyGaI/ilNL0iUiSCj9nw8rCjjCz89BQPhfKZEY/FTHxGAzIyaCCPwDcIkOsauujDgYFKpwJlPn929PN2Uf9WS5dM9OTmSooqQZR9WADFzuJAiFYcg9xkn47PVpDtaQfaHKTtqN3p8ZFZ38eTiOgqPcZcon2jjxjuRrQYRKCEEhEhCBex2bKUGDWgwkBKJAl013bZtKiXcYh1ymLdn7nrq/5HQHuDWLQaT1ReVlQ87C41XQQfjtJCbiHyyjnAlFx1IOwR9+l1B5tgaA4rD887M3y7jUf6KrFxYqizKvesun2qZ5qJoK8VjHl3l5kAtOJoMZRGPSAqqwWxzCxQo1 prussian@HAL9000
{% endhighlight %}

### NOTE: Unless you want me to have access to your server, PLEASE do not copy my RSA public key that is in the example field

Creating a new Instance
-----------------------

Now that your public key is on record, it's time to set up your virtual private server. Because this part is pretty much easy, I'll leave this section brief. The most important thing I want to mention is that I used the Ubuntu instance and for the storage device, I selected the full 30GBs available for free tier

Other thing to note: when you are prompted for which key pair you want, make sure to select the one you added manually. Don't use the Amazon generated one. Also, ignore Amazon's pesky warning about your ssh port being open to the world. Without your private key, attackers won't be able to decrypt the servers response and will fail authenticate.

Accessing your Server
---------------------

If you used the Ubuntu instance, then good; this part is assuming you are using the Ubuntu instance. Else consult AWS's documentation.

Now open up PuTTY.exe. In the field on the left, scroll down and expand the SSH category. Then under the SSH category, click on the Auth category. At the bottom of this configuration, it asks for your private key file (.ppk). Find your private key file.

Now scroll to the top of the left field and click on session. On the Host Name (or IP) text field, you want to put your server's public IP address. To find your IP address go to your list of running EC2 Instances. At the bottom, in the description category, there should be a field indicating the server's public IP. Paste this IP in the appropriate text field in PuTTY. Before launching, I recommend saving your session settings so you don't have to do all this again.

Now that you have it configured, launch PuTTY by clicking the Open button. A terminal window should launch and ask for your username. If you used the Ubuntu Instance, the username is ubuntu.

That's it, enjoy your server.

Troubleshooting
---------------

Fails to login due to (publickey)
=================================

Make sure you didn't save your putty private key file in a place you don't have permissions to read without UAC elevation (e.g. the program files folder). Save it in your documents.

Also, make sure you are logging in as ubuntu, not root.
