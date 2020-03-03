import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './blog-post.reducer';
import { IBlogPost } from 'app/shared/model/blog-post.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBlogPostDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BlogPostDetail = (props: IBlogPostDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { blogPostEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          BlogPost [<b>{blogPostEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{blogPostEntity.title}</dd>
          <dt>
            <span id="body">Body</span>
          </dt>
          <dd>{blogPostEntity.body}</dd>
        </dl>
        <Button tag={Link} to="/blog-post" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blog-post/${blogPostEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ blogPost }: IRootState) => ({
  blogPostEntity: blogPost.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BlogPostDetail);
