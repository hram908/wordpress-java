## Note ##

Mailing list for wordpress-java has changed. Please check the link at the left to subscribe to the new group.

## Introduction ##

This is a small client library and a command line interface for interacting with the xmlrpc interface of wordpress.

For command-line options see the OptionsPage.

Examples about using the API is available at ExampleCode.

API documentation is available at the [javadoc](http://wordpress-java.googlecode.com/svn/trunk/doc/index.html) pages.

Please see [Wiki Pages](http://code.google.com/p/wordpress-java/w/list) for more information on specific topics.

## Changelog ##

  * 0.5.1 (Sep 5, 2012):
    * This is smaller release, mostly about infrastructure.
    * Project relies on maven now.
    * Added test cases to verify basic functionality.
    * added deleteCategory() function, to help with tests.
    * Licensing changes.

  * 0.5 (Feb 9, 2012):
    * Corrected field types for page\_id, page\_parent\_id and page\_order.
    * Fixed behavior of mt\_text\_more.
    * Useless warning when input arrays are empty is removed.
    * Fixed type for user\_id

  * 0.4 (Nov 28, 2010):
    * Fixed a problem with excerpts.
    * Fixed the type for postids.
    * Added support for custom fields.
    * Added support for commenting.

  * 0.3 (Jun 5, 2009):
    * Added support for tags.

  * 0.2 (Jan 13, 2009):
    * CLI version sends a page rather than a post, fixed.
    * \[ is now allowed in posts.
    * --newmedia and --overwrite options added to support uploading media.
    * --supportedstatus added to list supported status values for --publish.
    * Fixed the bug that effectively removes all integer and date fields in a request.

  * 0.1 (Feb 14, 2008):
    * Initial release.