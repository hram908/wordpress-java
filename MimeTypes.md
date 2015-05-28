# Introduction #

This is actually about how Java determines mime types of files.

# Details #

As explained in [here](http://java.sun.com/j2ee/sdk_1.3/techdocs/api/javax/activation/MimetypesFileTypeMap.html), mime types in java are determined in such a manner:

  1. The file .mime.types in the user's home directory.
  1. The file <java.home>/lib/mime.types.
  1. The file or resource named META-INF/mime.types.
  1. The file or resource named META-INF/mimetypes.default.

So, if you upload a file (say, a png file) and the mime type appears as

```
type:application/octet-stream
```

Just create a file ~/.mime.types and add an entry like this:

```
image/png png
```

Which should fix it.

A more general solution would be copying /etc/mime.types to ~/.mime.types, which contains a huge amount of file extensions.