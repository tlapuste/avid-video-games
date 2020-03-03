package avid.video.games.web.rest.vm;

/**
 * View Model object for viewing an individual blog post's comments
 */
public class BlogPostCommentVM {
    private String comment;
    private String authorName;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
