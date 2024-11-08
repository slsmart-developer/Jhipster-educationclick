import React from 'react';
import { motion } from 'framer-motion';

export const About = () => {
  return (
    <div className="dark-bg-boxdark my-5" style={{ paddingTop: '130px', paddingBottom: '130px' }}>
      <div className="parent pt-16 my-16">
        <div>
          <motion.div
            className="mb-20"
            initial={{ y: -200, opacity: 0 }}
            animate={{
              y: 0,
              opacity: 1,
              transition: { duration: 1, type: 'spring' },
            }}
          >
            <h3 className="text-boxdark text-center">About us ?</h3>
            <h1 className="text-4xl font-weight-bold text-center text-boxdark">
              Education <span className="header-text-color">Click</span>
            </h1>
            <div className="d-flex justify-content-center mt-4">
              <div className="side-line"></div>
              <div className="middle-line">
                <div className="inner-circle"></div>
              </div>
              <div className="side-line"></div>
            </div>
          </motion.div>
          <div className="row">
            <div className="col-md-6">
              <motion.div
                initial={{ x: -200, opacity: 0 }}
                animate={{
                  x: 0,
                  opacity: 1,
                  transition: { duration: 1, delay: 1.25 },
                }}
              >
                <img
                  src="../../../content/images/aboutSipeka.svg"
                  alt="About SiPeKa"
                  className="w-75 h-75"
                  style={{ transform: 'translateY(-12%)', paddingTop: '100px' }}
                  title="About SiPeKa"
                />
              </motion.div>
            </div>
            <div className="col-md-6" style={{ paddingTop: '100px' }}>
              <motion.div
                initial={{ x: 200, opacity: 0 }}
                animate={{
                  x: 0,
                  opacity: 1,
                  transition: { duration: 1, delay: 1.25 },
                }}
              >
                <p className="font-weight-medium mb-4 text-boxdark">
                  Education Click is a versatile online platform that connects students all over the world to the best instructors both
                  locally and internationally.
                </p>
                <p className="font-weight-medium mb-4 text-boxdark">
                  We're dedicated to giving you the very best of professionalism that you search for, with a focus on dependability,
                  integrity, and quality.
                </p>
                <p className="font-weight-medium mb-4 text-boxdark">
                  We hope you enjoy our products as much as we enjoy offering them to you.
                </p>
              </motion.div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default About;
