import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BlogPostComment from './blog-post-comment';
import BlogPostCommentDetail from './blog-post-comment-detail';
import BlogPostCommentUpdate from './blog-post-comment-update';
import BlogPostCommentDeleteDialog from './blog-post-comment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BlogPostCommentDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BlogPostCommentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BlogPostCommentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BlogPostCommentDetail} />
      <ErrorBoundaryRoute path={match.url} component={BlogPostComment} />
    </Switch>
  </>
);

export default Routes;
