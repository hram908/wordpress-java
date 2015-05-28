Wordpress recently supports listing of possible values for 'post\_status' and 'page\_status' fields, so just try:

```
jwordpress --supportedstatus
```

to get a list. Otherwise, here is what you should at least know:

`publish`: Changes are immediately visible.

`draft`: Changes are not visible, until the `publish` status is set to `publish`.

However, on the command line argument '--publish', either true or false is accepted. When set to false, page is draft, otherwise it's published.

_**Note:** Fields override command line arguments, so if you need something other than 'published' or 'draft', specify it in the field._