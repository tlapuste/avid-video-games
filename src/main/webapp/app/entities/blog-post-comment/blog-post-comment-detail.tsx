import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './blog-post-comment.reducer';
import { IBlogPostComment } from 'app/shared/model/blog-post-comment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBlogPostCommentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BlogPostCommentDetail = (props: IBlogPostCommentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { blogPostCommentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          BlogPostComment [<b>{blogPostCommentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="body">Body</span>
          </dt>
          <dd>{blogPostCommentEntity.body}</dd>
          <dt>Commenter</dt>
          <dd>{blogPostCommentEntity.commenter ? blogPostCommentEntity.commenter.id : ''}</dd>
          <dt>Comment</dt>
          <dd>{blogPostCommentEntity.comment ? blogPostCommentEntity.comment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/blog-post-comment" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blog-post-comment/${blogPostCommentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ blogPostComment }: IRootState) => ({
  blogPostCommentEntity: blogPostComment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BlogPostCommentDetail);
