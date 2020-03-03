package avid.video.games.repository;

import avid.video.games.domain.BlogPostComment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BlogPostComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogPostCommentRepository extends JpaRepository<BlogPostComment, Long> {

}
