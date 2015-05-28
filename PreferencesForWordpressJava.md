# Introduction #

If you get tired of entering user name, password and xmlrpc url with every command, you can save them in preferences so that jwordpress will pick it up from preferences instead of the command line.

# How? #

Each option can be stored, just save it in net.bican.wordpress in preferences database. For example if you'd like to save your user name, password and url, save it at:

```
net.bican.wordpress:
  pass -> yourpassword
  url -> urltoxmlrpc
  user -> yourusername
```

You can use [jprefctl](http://jprefctl.googlecode.com/) for this:

```
$ jprefctl -w /net/bican/wordpress:pass:yourpassword \
           -w /net/bican/wordpress:url:urltoxmlrpc \
           -w /net/bican/wordpress:user:yourusername
```

should do the trick.