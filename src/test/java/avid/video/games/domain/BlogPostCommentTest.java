package avid.video.games.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import avid.video.games.web.rest.TestUtil;

public class BlogPostCommentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlogPostComment.class);
        BlogPostComment blogPostComment1 = new BlogPostComment();
        blogPostComment1.setId(1L);
        BlogPostComment blogPostComment2 = new BlogPostComment();
        blogPostComment2.setId(blogPostComment1.getId());
        assertThat(blogPostComment1).isEqualTo(blogPostComment2);
        blogPostComment2.setId(2L);
        assertThat(blogPostComment1).isNotEqualTo(blogPostComment2);
        blogPostComment1.setId(null);
        assertThat(blogPostComment1).isNotEqualTo(blogPostComment2);
    }
}
