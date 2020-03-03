import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getEntities as getBlogUsers } from 'app/entities/blog-user/blog-user.reducer';
import { IBlogPost } from 'app/shared/model/blog-post.model';
import { getEntities as getBlogPosts } from 'app/entities/blog-post/blog-post.reducer';
import { getEntity, updateEntity, createEntity, reset } from './blog-post-comment.reducer';
import { IBlogPostComment } from 'app/shared/model/blog-post-comment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBlogPostCommentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BlogPostCommentUpdate = (props: IBlogPostCommentUpdateProps) => {
  const [commenterId, setCommenterId] = useState('0');
  const [commentId, setCommentId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { blogPostCommentEntity, blogUsers, blogPosts, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/blog-post-comment');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getBlogUsers();
    props.getBlogPosts();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...blogPostCommentEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="avidApp.blogPostComment.home.createOrEditLabel">Create or edit a BlogPostComment</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : blogPostCommentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="blog-post-comment-id">ID</Label>
                  <AvInput id="blog-post-comment-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="bodyLabel" for="blog-post-comment-body">
                  Body
                </Label>
                <AvField id="blog-post-comment-body" type="text" name="body" />
              </AvGroup>
              <AvGroup>
                <Label for="blog-post-comment-commenter">Commenter</Label>
                <AvInput id="blog-post-comment-commenter" type="select" className="form-control" name="commenter.id">
                  <option value="" key="0" />
                  {blogUsers
                    ? blogUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="blog-post-comment-comment">Comment</Label>
                <AvInput id="blog-post-comment-comment" type="select" className="form-control" name="comment.id">
                  <option value="" key="0" />
                  {blogPosts
                    ? blogPosts.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/blog-post-comment" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  blogUsers: storeState.blogUser.entities,
  blogPosts: storeState.blogPost.entities,
  blogPostCommentEntity: storeState.blogPostComment.entity,
  loading: storeState.blogPostComment.loading,
  updating: storeState.blogPostComment.updating,
  updateSuccess: storeState.blogPostComment.updateSuccess
});

const mapDispatchToProps = {
  getBlogUsers,
  getBlogPosts,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BlogPostCommentUpdate);
