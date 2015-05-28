# Introduction #

Beginning from version **0.3**, it's possible to edit/create/delete tags from posts.

# Details #

The related key is 'mt\_keywords'. It's a string of comma separated values. Adding line like the one below will add three tags to the post:

```
  mt_keywords:testtag,testtag2,another tag
```

Removing tags is as easy as editing. This will remove the second tag from the example above:

```
  mt_keywords:testtag,another tag
```