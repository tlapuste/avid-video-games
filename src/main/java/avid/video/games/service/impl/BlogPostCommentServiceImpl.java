package avid.video.games.service.impl;

import avid.video.games.service.BlogPostCommentService;
import avid.video.games.domain.BlogPostComment;
import avid.video.games.repository.BlogPostCommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BlogPostComment}.
 */
@Service
@Transactional
public class BlogPostCommentServiceImpl implements BlogPostCommentService {

    private final Logger log = LoggerFactory.getLogger(BlogPostCommentServiceImpl.class);

    private final BlogPostCommentRepository blogPostCommentRepository;

    public BlogPostCommentServiceImpl(BlogPostCommentRepository blogPostCommentRepository) {
        this.blogPostCommentRepository = blogPostCommentRepository;
    }

    /**
     * Save a blogPostComment.
     *
     * @param blogPostComment the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BlogPostComment save(BlogPostComment blogPostComment) {
        log.debug("Request to save BlogPostComment : {}", blogPostComment);
        return blogPostCommentRepository.save(blogPostComment);
    }

    /**
     * Get all the blogPostComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BlogPostComment> findAll(Pageable pageable) {
        log.debug("Request to get all BlogPostComments");
        return blogPostCommentRepository.findAll(pageable);
    }

    /**
     * Get one blogPostComment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BlogPostComment> findOne(Long id) {
        log.debug("Request to get BlogPostComment : {}", id);
        return blogPostCommentRepository.findById(id);
    }

    /**
     * Delete the blogPostComment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BlogPostComment : {}", id);
        blogPostCommentRepository.deleteById(id);
    }
}
