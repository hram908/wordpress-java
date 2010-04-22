/*
 * This file is part of jwordpress.
 *
 * jwordpress is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jwordpress is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jwordpress.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.bican.wordpress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import redstone.xmlrpc.XmlRpcArray;
import redstone.xmlrpc.XmlRpcFault;
import redstone.xmlrpc.XmlRpcProxy;
import redstone.xmlrpc.XmlRpcStruct;

interface BloggerBridge {
  /**
   * @param blogid Blog id, not used in wordpress
   * @param username User name
   * @param password Password
   * @param post_ID Page ID to delete
   * @param publish Publish status
   * @return result of deletion
   * @throws XmlRpcFault
   */
  Boolean deletePost(Integer blogid, Integer post_ID, String username,
      String password, String publish) throws XmlRpcFault;

  /**
   * @param placeHolder No reference about what it is in xmlrpc.php, but it's
   *          not used
   * @param blogid Blog id, not used in wordpress
   * @param username User name
   * @param password Password
   * @param template Template, not used in wordpress
   * @return Template page
   * @throws XmlRpcFault
   */
  String getTemplate(Integer placeHolder, Integer blogid, String username,
      String password, String template) throws XmlRpcFault;

  /**
   * @param placeHolder No reference about what it is in xmlrpc.php, but it's
   *          not used
   * @param username User name
   * @param password Password
   * @return User information
   * @throws XmlRpcFault
   */
  XmlRpcStruct getUserInfo(Integer placeHolder, String username, String password)
      throws XmlRpcFault;

  /**
   * @param placeHolder No reference about what it is in xmlrpc.php, but it's
   *          not used
   * @param username User name
   * @param password Password
   * @return Blog that user has
   * @throws XmlRpcFault
   */
  XmlRpcArray getUsersBlogs(Integer placeHolder, String username,
      String password) throws XmlRpcFault;

  /**
   * @param placeHolder No reference about what it is in xmlrpc.php, but it's
   *          not used
   * @param blogid Blog id, not used in wordpress
   * @param username User name
   * @param password Password
   * @param content Content of the template
   * @param template Template, not used in wordpress
   * @return Result of the operation
   * @throws XmlRpcFault
   */
  Boolean setTemplate(Integer placeHolder, Integer blogid, String username,
      String password, String content, String template) throws XmlRpcFault;
}

interface DemoBridge {
  /**
   * @param number1 first number
   * @param number2 second number
   * @return addition of the arguments
   * @throws XmlRpcFault
   */
  Double addTwoNumbers(Double number1, Double number2) throws XmlRpcFault;

  /**
   * @return A very important message
   * @throws XmlRpcFault
   */
  String sayHello() throws XmlRpcFault;
}

interface MetaWebLogBridge {
  /**
   * @param post_ID ID of the post to edit
   * @param username User name
   * @param password Password
   * @param post Page information
   * @param publish Post status
   * @return result of edit
   * @throws XmlRpcFault
   */
  Boolean editPost(Integer post_ID, String username, String password,
      XmlRpcStruct post, String publish) throws XmlRpcFault;

  /**
   * @param post_ID ID of the post to retrieve
   * @param username User name
   * @param password Password
   * @return Page information
   * @throws XmlRpcFault
   */
  XmlRpcStruct getPost(Integer post_ID, String username, String password)
      throws XmlRpcFault;

  /**
   * @param blogid Blog id (not used in wordpress)
   * @param username User name
   * @param password Password
   * @param num_posts Number of posts to retrieve
   * @return List of pages
   * @throws XmlRpcFault
   */
  XmlRpcArray getRecentPosts(Integer blogid, String username, String password,
      Integer num_posts) throws XmlRpcFault;

  /**
   * @param blogid Blog id (not used in wordpress)
   * @param username User name
   * @param password Password
   * @param data Data Structure (type,bits,overwrite)
   * @return result of the upload
   * @throws XmlRpcFault
   */
  XmlRpcStruct newMediaObject(Integer blogid, String username, String password,
      XmlRpcStruct data) throws XmlRpcFault;

  /**
   * @param blogid Blog id (not used in wordpress)
   * @param username User name
   * @param password Password
   * @param post Post structure
   * @param publish Publish status
   * @return post id
   * @throws XmlRpcFault
   */
  String newPost(Integer blogid, String username, String password,
      XmlRpcStruct post, Boolean publish) throws XmlRpcFault;
}

interface MovableTypeBridge {
  /**
   * @return List of methods the server supports
   * @throws XmlRpcFault
   */
  XmlRpcArray supportedMethods() throws XmlRpcFault;

  /**
   * @return List of supported text filters
   * @throws XmlRpcFault
   */
  XmlRpcArray supportedTextFilters() throws XmlRpcFault;

  /**
   * @param PostId Post id
   * @return List of trackbacks for the post
   * @throws XmlRpcFault
   */
  XmlRpcArray getTrackbackPings(Integer PostId) throws XmlRpcFault;
}

interface PingbackBridge {
  /**
   * @param pagelinkedfrom Source
   * @param pagelinkedto Destination
   * @return response string
   * @throws XmlRpcFault
   */
  String ping(String pagelinkedfrom, String pagelinkedto) throws XmlRpcFault;
}

interface PingbackExtensionsBridge {
  /**
   * @param url url of the page queried
   * @return Array of URLs
   * @throws XmlRpcFault
   */
  XmlRpcArray getPingbacks(String url) throws XmlRpcFault;
}

/**
 * 
 * The utility class that links xmlrpc calls to Java functions.
 * 
 * @author Can Bican &lt;can@bican.net&gt;
 * 
 */
public class Wordpress {

  private static byte[] getBytesFromFile(File file) {
    byte[] result = null;
    InputStream is;
    try {
      is = new FileInputStream(file);

      // Get the size of the file
      long length = file.length();

      // You cannot create an array using a long type.
      // It needs to be an int type.
      // Before converting to an int type, check
      // to ensure that file is not larger than Integer.MAX_VALUE.
      if (length > Integer.MAX_VALUE) {
        // File is too large
      }

      // Create the byte array to hold the data
      byte[] bytes = new byte[(int) length];

      // Read in the bytes
      int offset = 0;
      int numRead = 0;
      while (offset < bytes.length
          && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
        offset += numRead;
      }

      // Ensure all the bytes have been read in
      if (offset < bytes.length) {
        throw new IOException(
            "Could not completely read file " + file.getName()); //$NON-NLS-1$
      }

      // Close the input stream and return bytes
      is.close();
      result = bytes;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  private BloggerBridge blogger;

  private DemoBridge demo = null;

  private MovableTypeBridge mt = null;

  private MetaWebLogBridge mw = null;

  private String password = null;

  private PingbackBridge pingback = null;

  private PingbackExtensionsBridge pingbackExt = null;

  private String username = null;

  private WordpressBridge wp = null;

  private String xmlRpcUrl = null;

  @SuppressWarnings("unused")
  private Wordpress() {
    // no default constructor - class needs username, password and url
  }

  /**
   * @param username User name
   * @param password Password
   * @param xmlRpcUrl xmlrpc communication point, usually blogurl/xmlrpc.php
   * @throws MalformedURLException If the URL is faulty
   */
  public Wordpress(String username, String password, String xmlRpcUrl)
      throws MalformedURLException {
    this.username = username;
    this.password = password;
    this.xmlRpcUrl = xmlRpcUrl;
    initMetaWebLog();
  }

  /**
   * @param number1 First number
   * @param number2 Second number
   * @return addition of two numbers
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public double addTwoNumbers(double number1, double number2)
      throws XmlRpcFault {
    return this.demo.addTwoNumbers(number1, number2);
  }

  /**
   * @param post_ID ID of the page to delete
   * @param publish Publish status
   * @return Result of deletion
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public Boolean deletePage(int post_ID, String publish) throws XmlRpcFault {
    return this.wp.deletePage(0, this.username, this.password, Integer
        .valueOf(post_ID), publish);
  }

  /**
   * @param post_ID ID of the post to delete
   * @param publish Publish status
   * @return result of deletion
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public Boolean deletePost(int post_ID, String publish) throws XmlRpcFault {
    return this.blogger.deletePost(0, post_ID, this.username, this.password,
        publish);
  }

  /**
   * @param post_ID ID of the post to edit
   * @param page Page information
   * @param publish Publish status
   * @return Result of edit
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public Boolean editPage(int post_ID, Page page, String publish)
      throws XmlRpcFault {
    XmlRpcStruct post = page.toXmlRpcStruct();
    return this.wp.editPage(0, post_ID, this.username, this.password, post,
        publish);
  }

  /**
   * @param post_ID ID of the post to edit
   * @param page Page information
   * @param publish Publish status
   * @return result of edit
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public Boolean editPost(int post_ID, Page page, String publish)
      throws XmlRpcFault {
    XmlRpcStruct post = page.toXmlRpcStruct();
    return this.mw.editPost(post_ID, this.username, this.password, post,
        publish);
  }

  @SuppressWarnings("unchecked")
  private List fillFromXmlRpcArray(XmlRpcArray r, Class cl) {
    List result = null;
    try {
      result = new ArrayList();
      for (Object o : r) {
        XmlRpcMapped n = (XmlRpcMapped) cl.newInstance();
        if (o instanceof String) {
          result.add(o);
        } else {
          n.fromXmlRpcStruct((XmlRpcStruct) o);
        }
        result.add(n);
      }
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return result;
  }

  private List<String> fromStringArray(XmlRpcArray r) {
    List<String> result;
    result = new ArrayList<String>();
    for (Object object : r) {
      result.add((String) object);
    }
    return result;
  }

  /**
   * @return List of authors
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  @SuppressWarnings("unchecked")
  public List<Author> getAuthors() throws XmlRpcFault {
    XmlRpcArray r = this.wp.getAuthors(0, this.username, this.password);
    return fillFromXmlRpcArray(r, Author.class);
  }

  /**
   * @return List of categories
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  @SuppressWarnings("unchecked")
  public List<Category> getCategories() throws XmlRpcFault {
    XmlRpcArray r = this.wp.getCategories(0, this.username, this.password);
    return fillFromXmlRpcArray(r, Category.class);
  }

  /**
   * @param pageid Page ID
   * @return The <code>Page</code> object.
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public Page getPage(int pageid) throws XmlRpcFault {
    XmlRpcStruct r = this.wp.getPage(0, pageid, this.username, this.password);
    Page result = new Page();
    result.fromXmlRpcStruct(r);
    return result;
  }

  /**
   * @return List of Pages, short format
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  @SuppressWarnings("unchecked")
  public List<PageDefinition> getPageList() throws XmlRpcFault {
    XmlRpcArray r = this.wp.getPageList(0, this.username, this.password);
    return fillFromXmlRpcArray(r, PageDefinition.class);
  }

  /**
   * @return List of Pages, in full format
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  @SuppressWarnings("unchecked")
  public List<Page> getPages() throws XmlRpcFault {
    XmlRpcArray r = this.wp.getPages(0, this.username, this.password);
    return fillFromXmlRpcArray(r, Page.class);
  }

  /**
   * @param postId Post ID
   * @return Trackbacks for the post
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  @SuppressWarnings("unchecked")
  public List<Ping> getTrackbackPings(int postId) throws XmlRpcFault {
    XmlRpcArray r = this.mt.getTrackbackPings(postId);
    return fillFromXmlRpcArray(r, Ping.class);
  }

  /**
   * @param url Url of the page queried
   * @return List of URLs
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public List<URL> getPingbacks(String url) throws XmlRpcFault {
    List<URL> result = null;
    try {
      XmlRpcArray r = this.pingbackExt.getPingbacks(url);
      result = new ArrayList<URL>();
      for (Object rec : r) {
        result.add(new URL((String) rec));
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * @param post_ID ID of the post to retrieve
   * @return Page information
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public Page getPost(int post_ID) throws XmlRpcFault {
    XmlRpcStruct r = this.mw.getPost(post_ID, this.username, this.password);
    Page result = new Page();
    result.fromXmlRpcStruct(r);
    return result;
  }

  /**
   * @param num_posts Number of posts to be retrieved.
   * @return List of pages.
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  @SuppressWarnings("unchecked")
  public List<Page> getRecentPosts(int num_posts) throws XmlRpcFault {
    XmlRpcArray r = this.mw.getRecentPosts(0, this.username, this.password,
        num_posts);
    return fillFromXmlRpcArray(r, Page.class);
  }

  /**
   * @return Template page
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public String getTemplate() throws XmlRpcFault {
    return this.blogger.getTemplate(0, 0, this.username, this.password, "");
  }

  /**
   * @return The user information
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public User getUserInfo() throws XmlRpcFault {
    XmlRpcStruct r = this.blogger.getUserInfo(0, this.username, this.password);
    User result = new User();
    result.fromXmlRpcStruct(r);
    return result;
  }

  /**
   * @return List of blogs the user has (only one in wordpress case)
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  @SuppressWarnings("unchecked")
  public List<Blog> getUsersBlogs() throws XmlRpcFault {
    XmlRpcArray r = this.blogger.getUsersBlogs(0, this.username, this.password);
    return fillFromXmlRpcArray(r, Blog.class);
  }

  private void initMetaWebLog() throws MalformedURLException {
    final URL url = new URL(this.xmlRpcUrl);
    this.wp = (WordpressBridge) XmlRpcProxy.createProxy(url, "wp",
        new Class[] { WordpressBridge.class }, true);
    this.mw = (MetaWebLogBridge) XmlRpcProxy.createProxy(url, "metaWeblog",
        new Class[] { MetaWebLogBridge.class }, true);
    this.mt = (MovableTypeBridge) XmlRpcProxy.createProxy(url, "mt",
        new Class[] { MovableTypeBridge.class }, true);
    this.demo = (DemoBridge) XmlRpcProxy.createProxy(url, "demo",
        new Class[] { DemoBridge.class }, true);
    this.pingback = (PingbackBridge) XmlRpcProxy.createProxy(url, "pingback",
        new Class[] { PingbackBridge.class }, true);
    this.blogger = (BloggerBridge) XmlRpcProxy.createProxy(url, "blogger",
        new Class[] { BloggerBridge.class }, true);
    this.pingbackExt = (PingbackExtensionsBridge) XmlRpcProxy.createProxy(url,
        "pingback.extensions", new Class[] { PingbackExtensionsBridge.class },
        true);
  }

  /**
   * @param name Category name
   * @param slug Category short name
   * @param parentId Parent ID
   * @return New category id
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  @SuppressWarnings("unchecked")
  public int newCategory(String name, String slug, int parentId)
      throws XmlRpcFault {
    XmlRpcStruct h = new XmlRpcStruct();
    h.put("name", name);
    h.put("slug", slug);
    h.put("parent_id", parentId);
    return this.wp.newCategory(0, this.username, this.password, h);
  }

  /**
   * @param mimeType Mime type of the file
   * @param file File name
   * @param overwrite true/false
   * @return new object location
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public MediaObject newMediaObject(String mimeType, File file,
      Boolean overwrite) throws XmlRpcFault {
    Attachment att = new Attachment();
    att.setType(mimeType);
    att.setOverwrite(overwrite);
    att.setName(file.getName());
    att.setBits(getBytesFromFile(file));
    XmlRpcStruct d = att.toXmlRpcStruct();
    XmlRpcStruct r = this.mw.newMediaObject(0, this.username, this.password, d);
    MediaObject result = new MediaObject();
    result.fromXmlRpcStruct(r);
    return result;
  }

  /**
   * @param post Page information
   * @param publish Publish status
   * @return Post ID
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public String newPage(Page post, String publish) throws XmlRpcFault {
    return this.wp.newPage(0, this.username, this.password, post
        .toXmlRpcStruct(), publish);
  }

  /**
   * @param page Post information
   * @param publish Publish status
   * @return Post id
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public String newPost(Page page, boolean publish) throws XmlRpcFault {
    return this.mw.newPost(0, this.username, this.password, page
        .toXmlRpcStruct(), publish);
  }

  /**
   * @param pagelinkedfrom Source
   * @param pagelinkedto Destination
   * @return response for ping
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public String ping(String pagelinkedfrom, String pagelinkedto)
      throws XmlRpcFault {
    return this.pingback.ping(pagelinkedfrom, pagelinkedto);
  }

  /**
   * @return A very important message
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public String sayHello() throws XmlRpcFault {
    return this.demo.sayHello();
  }

  /**
   * @param content Content of the template
   * @return Result of the operation
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public boolean setTemplate(String content) throws XmlRpcFault {
    return this.blogger.setTemplate(0, 0, this.username, this.password,
        content, "");
  }

  /**
   * @param category Category to search
   * @param max_results Maximum results to return
   * @return List of suggested categories
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public List<Category> suggestCategories(String category, Integer max_results)
      throws XmlRpcFault {
    throw new UnsupportedOperationException(); // couldn't quite figure out the
    // response.
  }

  /**
   * @return List of supported methods
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public List<String> supportedMethods() throws XmlRpcFault {
    return fromStringArray(this.mt.supportedMethods());
  }

  /**
   * @return List of supported text filters
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public List<String> supportedTextFilters() throws XmlRpcFault {
    return fromStringArray(this.mt.supportedTextFilters());
  }

  /**
   * @return List of supported post status values
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public List<PostAndPageStatus> getPostStatusList() throws XmlRpcFault {
    return processKeyValList(this.wp.getPostStatusList(0, this.username,
        this.password));
  }

  /**
   * @return List of supported page status values
   * @throws XmlRpcFault Generic exception for xml-rpc operations
   */
  public List<PostAndPageStatus> getPageStatusList() throws XmlRpcFault {
    return processKeyValList(this.wp.getPageStatusList(0, this.username,
        this.password));
  }

  private List<PostAndPageStatus> processKeyValList(XmlRpcStruct r) {
    String response = r.toString();
    response = response.replaceAll("[{}]", "").replaceAll(",  *", ",");
    String[] responses = response.split(",");
    List<PostAndPageStatus> result = new ArrayList<PostAndPageStatus>();
    for (String rp : responses) {
      String[] keyval = rp.split("=");
      PostAndPageStatus pp = new PostAndPageStatus();
      pp.setStatus(keyval[0]);
      pp.setDescription(keyval[1]);
      result.add(pp);
    }
    return result;
  }
}

interface WordpressBridge {

  /**
   * @param blogid Blog id, not used in wordpress
   * @param username User name
   * @param password Password
   * @param post_ID ID of the post to delete
   * @param publish Publish status
   * @return Result of deletion--
   * @throws XmlRpcFault
   */
  Boolean deletePage(Integer blogid, String username, String password,
      Integer post_ID, String publish) throws XmlRpcFault;

  /**
   * @param blogid Blog id, not used in wordpress
   * @param post_ID ID of the post to edit
   * @param username User name
   * @param password Password
   * @param post Post information
   * @param publish Publish status
   * @return Result of edit
   * @throws XmlRpcFault
   */
  Boolean editPage(Integer blogid, Integer post_ID, String username,
      String password, XmlRpcStruct post, String publish) throws XmlRpcFault;

  /**
   * @param blogid Blog id, not used in wordpress
   * @param username User name
   * @param password Password
   * @return Array of Authors
   * @throws XmlRpcFault
   */
  XmlRpcArray getAuthors(int blogid, String username, String password)
      throws XmlRpcFault;

  /**
   * @param blogid Blog id, not used in wordpress
   * @param username User name
   * @param password Password
   * @return Array of categories
   * @throws XmlRpcFault
   */
  XmlRpcArray getCategories(int blogid, String username, String password)
      throws XmlRpcFault;

  /**
   * @param blogid Blog id, not used in wordpress
   * @param pageid Page id
   * @param username User name
   * @param password Password
   * @return Page information
   * @throws XmlRpcFault
   */
  XmlRpcStruct getPage(Integer blogid, Integer pageid, String username,
      String password) throws XmlRpcFault;

  /**
   * @param blogid Blog id, not used in wordpress
   * @param username User name
   * @param password Password
   * @return Array of Pages
   * @throws XmlRpcFault
   */
  XmlRpcArray getPageList(Integer blogid, String username, String password)
      throws XmlRpcFault;

  /**
   * @param blogid Blog id, not used in wordpress
   * @param username User name
   * @param password Password
   * @return Array of Pages
   * @throws XmlRpcFault
   */
  XmlRpcArray getPages(Integer blogid, String username, String password)
      throws XmlRpcFault;

  /**
   * @param blogid Blog id, not used in wordpress
   * @param username User name
   * @param password Password
   * @param category Category information
   * @return new category id
   * @throws XmlRpcFault
   */
  Integer newCategory(Integer blogid, String username, String password,
      XmlRpcStruct category) throws XmlRpcFault;

  /**
   * @param blogid Blog id, not used in wordpress
   * @param username User name
   * @param password Password
   * @param post Page information
   * @param publish Publish status
   * @return Post ID
   * @throws XmlRpcFault
   */
  String newPage(Integer blogid, String username, String password,
      XmlRpcStruct post, String publish) throws XmlRpcFault;

  /**
   * @param blogid Blog id, not used in wordpress
   * @param username User name
   * @param password Password
   * @param category Category to search
   * @param max_results Maximum results to return
   * @return List of suggested categories
   * @throws XmlRpcFault
   */
  XmlRpcArray suggestCategories(Integer blogid, String username,
      String password, String category, Integer max_results) throws XmlRpcFault;

  XmlRpcStruct getPostStatusList(Integer blogid, String username,
      String password) throws XmlRpcFault;

  XmlRpcStruct getPageStatusList(Integer blogid, String username,
      String password) throws XmlRpcFault;
}
