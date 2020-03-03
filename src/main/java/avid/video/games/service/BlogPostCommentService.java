package avid.video.games.service;

import avid.video.games.domain.BlogPostComment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link BlogPostComment}.
 */
public interface BlogPostCommentService {

    /**
     * Save a blogPostComment.
     *
     * @param blogPostComment the entity to save.
     * @return the persisted entity.
     */
    BlogPostComment save(BlogPostComment blogPostComment);

    /**
     * Get all the blogPostComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BlogPostComment> findAll(Pageable pageable);

    /**
     * Get the "id" blogPostComment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlogPostComment> findOne(Long id);

    /**
     * Delete the "id" blogPostComment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
