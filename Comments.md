As of version 0.4, it is possible to add/delete/modify comments. See the javadoc for API level operations -- This page is about the command line.

In order to add a comment, create a file that looks like the following:

```
post_id: XXX
content: test
```

and add the comment using `jwordpress -ca comment.txt`.

This is the minimum you need for a new comment. Change post\_id to the related post and content contains the comment text. Possible fields that makes sense in editing and adding comments are:

```
date_created_gmt
parent
content
link
post_id
post_title
author
author_url
author_email
```

Command line options for getting comments will print a similarly structured output for comments.