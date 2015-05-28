#summary Explanation of command line options.

# Introduction #

(NOT COMPLETE YET)

Command line interface of wordpress-java covers most, but not all functionality of the API as a text/only interface.

## List of options ##

`jwordpress -?` will output:

```
usage:
 -?,--help                 Print usage information
 -a,--authors              Get author list
 -c,--categories           Get category list
 -cn,--newcategory <arg>   New category (uses --slug and --parentid)
 -h,--url <arg>            Specify the url to xmlrpc.php
 -l,--publish <arg>        Publish status for "new" options
 -mn,--newmedia <arg>      New media file (uses --overwrite)
 -od,--deletepost <arg>    Delete post (needs --publish)
 -oe,--editpost <arg>      Edit post (needs --postid and --publish
 -oi,--postid <arg>        Post id for pages and posts
 -on,--newpost <arg>       New post from file <arg> (needs --publish)
 -or,--recentposts <arg>   Get recent posts
 -os,--getpost <arg>       Get post
 -ov,--overwrite           Allow overwrite in uploading new media
 -p,--pass <arg>           Password
 -pd,--deletepage <arg>    Delete page (needs --publish)
 -pe,--editpage <arg>      Edit page (needs --postid and --publish
 -pg,--pages               Get page list (full)
 -pi,--parentid <arg>      Parent id for categories
 -pl,--pagelist            Get page list
 -pn,--newpage <arg>       New page from file <arg> (needs --publish)
 -ps,--page <arg>          Get page
 -s,--slug <arg>           Slug for categories
 -sm,--supportedmethods    List supported methods
 -so,--supportedstatus     Print supported page and post status values
 -st,--supportedfilters    List supported text filters
 -u,--user <arg>           User name
 -us,--userinfo            Get user information
```


## Detailed Explanation of Options ##

### --user , --pass and --url ###

These are the options you should supply for most of the other options. Specifically, --url points to the location of xmlrpc.php, usually http://blogaddress/xmlrpc.php . Please see the document PreferencesForWordpressJava for not having to enter these options with every command.

### --authors ###

Prints out a list of authors registered in the system. Fields are ':' separated. These are the field names in the output:

`User_id:User_login:display_name`

### --categories ###

Prints out a list of categories in the system. Fields are ':' separated. These are the field names in the output:

`Category Id:Category Name:Description:Html Url:Parent Id:Rss Url`

### --newcategory ###

Creates a new category. For example,

`jwordpress --newcategory 'Test category' --slug 'test' --parentid 0`

will create a top-level category whose short name is "test".

### --editpost ###

Replaces a post with the new content. For example,

`jwordpress --editpost post.txt --postid 18 --publish publish`

will replace post #18 with the contents of the file post.txt, and publish it immediately. For the format of post.txt, see the page PostFormat and for possible values of --publish, see PublishValues .

### --deletepost ###

Deletes a post. For example,

`jwordpress --deletepost 18 --publish publish`

will delete the post whose id is 18.

### --newpost ###

Creates a new post. For example.

`jwordpress --newpost post.txt --publish publish`

will create a new post from post.txt.

### --recentposts ###

Retrieves the list of last n posts. `jwordpress --recentposts 10` will dump the latest 10 posts.

### --getpost ###

Retrieves a post by post id. `jwordpress --getpost 18` will retrieve the post with the id 18.

### --deletepage ###

Deletes a page, just like --deletepost would delete a post.

### --editpage ###

Edits a page, just like --editpost would edit a post.

### --pages ###

Returns all pages in the system. `jwordpress --pages` will dump all page entries to stdout.

### --pagelist ###

Returns all pages in the system, but not in full. It just dumps the name, parent id and the publishing date.

### --newpage ###

Creates a new page, just like --newpost would create a new page.

### --page ###

Retrieves a page by page id, just like --getpost would retrieve a post.

### --supportedmethods ###

Lists all methods supported by the xmlrpc interface. Hardly useful in command line interface.

### --supportedfilters ###

Lists all supported filters in wordpress installation. Hardly useful in command line interface.

### --supportedstatus ###

Lists all supported publish values for posts and pages, which might be different.

### --userinfo ###

Displays information about the authenticated user.

### --newmedia ###

Uploads a media file (picture, movie, etc.). Requires a file name as the argument. One can use --overwrite to enable overwriting of old uploads. How the mime type of the file is determined is explained in MimeTypes.