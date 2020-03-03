package avid.video.games.web.rest.vm;

import java.util.List;

/**
 * View Model object for viewing an individual blog post
 */
public class BlogPostVM {
    private String title;
    private String body;
    private List<BlogPostCommentVM> blogPostCommentVMS;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<BlogPostCommentVM> getBlogPostCommentVMS() {
        return blogPostCommentVMS;
    }

    public void setBlogPostCommentVMS(List<BlogPostCommentVM> blogPostCommentVMS) {
        this.blogPostCommentVMS = blogPostCommentVMS;
    }
}
