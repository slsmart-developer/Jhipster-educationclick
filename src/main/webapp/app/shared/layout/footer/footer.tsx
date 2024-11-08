import './footer.scss';

import React from 'react';

import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer page-content">
    <footer className="text-sm text-center py-6 text-base-100 border-base-300 dark:bg-boxdark-2">
      <div className="flex flex-col md:flex-row items-center justify-center text-black dark:text-white">
        <p>&copy; Copyright 2024, Education - Click. All Rights Reserved</p>
      </div>
    </footer>
  </div>
);

export default Footer;
