package avid.video.games.repository;

import avid.video.games.domain.BlogPost;
import avid.video.games.domain.BlogPostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the BlogPostComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogPostCommentRepository extends JpaRepository<BlogPostComment, Long> {

    // Select all where things work
//    select
//          bu.first_name,
//          bu.last_name,
//          bpc.body
//    from blog_post bp
//    inner join blog_post_comment bpc on bpc.comment_id=bp.id
//    inner join blog_user bu on bu.id=bpc.commenter_id
//    where bp.id = 1;

    List<BlogPostComment> findAllByComment(BlogPost blogPost);
}
