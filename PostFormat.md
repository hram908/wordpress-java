# Introduction #

This page explains the output format, used in editing and creating new posts and pages.

In order to post a page, the best method is to retrieve an existing post and use it as the template for the new post. Neither the API nor the command line interfaces makes any assumptions about mandatory fields or their syntax.

# Example Post #

An example post, (which can be reproduced by `jwordpress --recentposts 1`) may contain the following lines (and more):

```
categories:[cat1, cat2, cat3]
date_created_gmt:20090328T12:30:00
dateCreated:20090328T14:30:00
description:This the post entry.<br/>
 And its continuation.
excerpt:null
```

So each field and its content is separated by ':'. If the content of the field continues in the next line, next line must not start with the regular expression `[a-z0-9]+:`.

If the character next to ':' is '[', the field is recognized as an array. Array elements are separated by ','.

Note that all date fields have the format YYYYMMMDD'T'HH:MM:SS.

If the contents of the field is 'null', then it is ignored.