import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const { account } = props;

  return (
    <Row>
      <Col md="9">
        <h2>Welcome to Avid Game Reviews!</h2>
        {account && account.login ? (
          <div>
            <Alert color="success">You are logged in as user {account.login}.</Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              If you want to
              <Link to="/login" className="alert-link">
                {' '}
                sign in
              </Link>
              , you can try the default accounts:
              <br />- Administrator (to create and edit blog entries) (login=&quot;admin&quot; and password=&quot;admin&quot;)
              <br />- User (to comment on the blog entries) (login=&quot;user&quot; and password=&quot;user&quot;).
            </Alert>

            <Alert color="warning">
              😭 No account yet?&nbsp; How are you ever gonna leave your witty comments?
              <Link to="/account/register" className="alert-link">
                Register and make your unique voice heard!
              </Link>
            </Alert>
          </div>
        )}
      </Col>
      <Col md="3" className="pad">
        {/*<span className="hipster rounded" />*/}
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
