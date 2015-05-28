There is a _Category_ class in the API, which is used for returning category list in a blog. It contains these attributes:

  * Category ID
  * Category Name
  * Description
  * Url of the category
  * Parent Category ID
  * Url of the RSS link

Common sense suggests that the same structure applies to the _categories_ attribute in the _Page_ class, however this is not the case. When posting or editing a new article, _xmlRpcArray_ in _categories_ attribute is an array of strings. So, for example, if you'd like to edit a post to add a new category, this is how to do it:

```
...
Page recentPost = wp.getRecentPosts(1).get(0);  // or some other way to get a post
XmlRpcArray s = recentPost.getCategories();
s.add("anothercategoryname");
recentPost.setCategories(s);
wp.editPost(postid,recentPost,publishStatus);
...
```