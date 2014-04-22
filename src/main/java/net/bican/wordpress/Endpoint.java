package net.bican.wordpress;

import java.net.URL;

import com.google.common.base.Objects;

public class Endpoint {
  private String blogUrl;

  public Endpoint(String blogUrl) {
    this.blogUrl = blogUrl;
  }

  public String getBlogUrl() {
    return this.blogUrl;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.blogUrl);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Endpoint)) {
      return false;
    }
    Endpoint other = (Endpoint) o;
    return Objects.equal(this.blogUrl, other.blogUrl);
  }

  public URL getXmlRpc() {
    // TODO Auto-generated method stub
    return null;
  }

}
