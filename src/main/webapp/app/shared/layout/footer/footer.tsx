import './footer.scss';

import React from 'react';

import { Col, Row } from 'reactstrap';

const Footer = props => (
  <div className="footer page-content">
    <Row>
      <Col md="12">
        <p>Tudor Lapuste © 2020 ||
          Github link for project{' '}
          <a href="https://github.com/tlapuste/avid-video-games" target="_blank" rel="noopener noreferrer">
            Github
          </a>
          !
        </p>
      </Col>
    </Row>
  </div>
);

export default Footer;
