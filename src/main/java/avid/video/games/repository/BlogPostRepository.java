package avid.video.games.repository;

import avid.video.games.domain.BlogPost;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BlogPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

}
