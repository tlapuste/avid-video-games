package avid.video.games.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A BlogPostComment.
 */
@Entity
@Table(name = "blog_post_comment")
public class BlogPostComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body")
    private String body;

    @ManyToOne
    @JsonIgnoreProperties("blogPostComments")
    private User commenter;

    @ManyToOne
    @JsonIgnoreProperties("blogPostComments")
    private BlogPost post;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public BlogPostComment body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getCommenter() {
        return commenter;
    }

    public BlogPostComment commenter(User blogUser) {
        this.commenter = blogUser;
        return this;
    }

    public void setCommenter(User blogUser) {
        this.commenter = blogUser;
    }

    public BlogPost getPost() {
        return post;
    }

    public BlogPostComment post(BlogPost blogPost) {
        this.post = blogPost;
        return this;
    }

    public void setPost(BlogPost blogPost) {
        this.post = blogPost;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogPostComment)) {
            return false;
        }
        return id != null && id.equals(((BlogPostComment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BlogPostComment{" +
            "id=" + getId() +
            ", body='" + getBody() + "'" +
            "}";
    }
}
