

# Introduction #

Custom fields are a bit tricky to deal with, so they deserve a mini how-to. In a nutshell, here are the rules:

  1. Custom fields in an update are declared as: [{id=...,key=...,value=...},{...},...]
  1. You can create a new custom field in a post by providing key and value.
  1. If you don't mention an existing custom field in an update, wordpress doesn't delete it, it just doesn't modify it.
  1. If you only provide "id", custom field gets deleted.
  1. If you don't provide "id" but "key" and "value", custom field gets created.

# Custom Fields In Command Line #

## Creating a New Post with Custom Fields ##

Let's create a new post, as simple as possible:

```
custom_fields:[{key=test_field,value=howitworks}]
description:this is a test post for custom fields
title:test for custom fields
```

This will create a new post with a custom field, an you'll be able to see it in the "edit post" page:

![http://wordpress-java.googlecode.com/svn/wiki/img/custom_created.jpg](http://wordpress-java.googlecode.com/svn/wiki/img/custom_created.jpg)

## Modifying a Custom Field in a Post ##

If you retrieve the post created above, you'll see something like:

```
...
custom_fields:[{id=230, value=howitworks, key=test_field}, {id=232, value=1, key=_edit_last}, {id=231, value=1271880179, key=_edit_lock}]
...
```

We're only changing the key "test\_field", so the update will contain:

```
...
custom_fields:[{id=230, value=yesitworks, key=test_field}]
...
```

Note that we don't have to include fields we don't care about.

## Adding a Custom Field to an Existing Post ##

Edit the post as usual, just replace the custom\_fields line with something like this:

```
...
custom_fields:[{value=2ndworks, key=test2_field}]
...
```

So here is the next custom field:

![http://wordpress-java.googlecode.com/svn/wiki/img/custom_added.jpg](http://wordpress-java.googlecode.com/svn/wiki/img/custom_added.jpg)

## Deleting a Custom Field from a Post ##

Now let's delete the first field we created. Notice that it was given an "id" of 230 (it may differ in your case), so we'll just do this:

```
...
custom_fields:[{id=230}]
...
```

And the test\_field will be gone.

# Custom Fields Via the API #

## Creating a Custom Field in Your Code ##

Creating a custom field for your post is easy. Let's suppose you're creating a new post, then it will be something like this:

```
Wordpress wp = new Wordpress(username, password, xmlRpcUrl);
Page newPage = new Page();
... (populate the fields) ...
XmlRpcArray custom_fields = new XmlRpcArray();
CustomField cf = new CustomField();
cf.setKey("test");
cf.setValue("123");
custom_fields.add(cf);
newPage.setCustom_fields(custom_fields);
wp.newPost(newPage,false);
```

Note that we're not setting an ID, leaving it null.

## Modifying Custom Fields in Your Code ##

When you retrieve a page, existing custom fields are returned in getCustom\_fields() function, in an XmlRpcArray consisting of XmlRpcStruct elements. So, iterating over them is as ridiculous as:

```
Wordpress wp = new Wordpress(username, password, xmlRpcUrl);
Page p = wp.getPost(32);
for (Object c:p.getCustom_fields()) {
  XmlRpcStruct xc = (XmlRpcStruct) c;
  CustomField cf = new CustomField();
  cf.fromXmlRpcStruct(xc);
  System.out.println(cf);
}
```

Just change the key and value fields in cf(CustomField) and reconstruct the array, and send it back via editPost().

Deleting is the same, just send a new array with the deleted custom field missing.

# Conclusion #

I know it's not the best interface possible, but it gets the job done. A better option would be adding command line switches that would make things easier. Please let me know (open an issue via [Issues](http://code.google.com/p/wordpress-java/issues/list)) if you have a better idea, I'm willing to implement any good one.