import './home.scss';
import React, { useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import { useAppSelector } from 'app/config/store';
import { motion, useAnimation } from 'framer-motion';
import axios from 'axios';
import { useInView } from 'react-intersection-observer';
import { About } from '../about/about';
import { Contact } from '../contact/contact';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);
  const form = useRef();
  const [ref, inView] = useInView({ threshold: 0.3, triggerOnce: true });
  const [viewDiv, setViewDiv] = useState(false);
  const animation = useAnimation();

  const [formData, setFormData] = useState({
    name: '',
    email: '',
    subject: '',
    message: '',
  });

  const handleChange = e => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      const response = await axios.post('/api/email/sendEmail', formData);
      console.log(response.data);
      // Reset form after successful submission
      setFormData({ name: '', email: '', subject: '', message: '' });
    } catch (error) {
      console.error('Error sending email:', error);
    }
  };

  useEffect(() => {
    if (inView) {
      setViewDiv(true);
    } else {
      setViewDiv(false);
    }
  }, [inView, animation]);

  const contactAnimation = {
    hidden: {
      y: -200,
      opacity: 0,
    },
    visible: {
      y: 0,
      opacity: 1,
      transition: {
        delay: 0.75,
        duration: 0.5,
      },
    },
  };

  const headingAnimation = {
    hidden: {
      y: -200,
      opacity: 0,
    },
    visible: {
      y: 0,
      opacity: 1,
      transition: { duration: 1, type: 'spring' },
    },
  };

  return (
    <div className="container">
      <div className="row align-items-center" style={{ paddingTop: '130px', paddingBottom: '130px' }}>
        <motion.div initial={{ y: 100 }} animate={{ y: 0 }} transition={{ duration: 1 }} className="col-12 col-lg-8 py-5">
          <h1 className="header-text-color font-bold mb-0 sm:text-left py-2 sm:text-center text-right">
            Welcome to Education Click
            <br />
            Your Pathway to Personalized Learning
          </h1>
          <p className="pt-8 text-left max-w-xl mb-2 font-medium sm:text-center dark:text-white py-2">
            Empowering every student to find their ideal academic path effortlessly.
          </p>

          <div className="d-flex flex-column flex-sm-row justify-center justify-sm-start">
            <Link to={'/login'} className="flex items-center">
              <button className="btn-primary text-white mb-sm-0 me-sm-3 mr3-l mt-3">
                <span>Login</span>
              </button>
            </Link>

            <Link to={'/account/register'} className="flex items-center">
              <button className="btn-primary text-white m-lg-3">
                <span>Register</span>
              </button>
            </Link>
          </div>
        </motion.div>
        <motion.div className="col-12 col-lg-4" initial={{ x: -100 }} animate={{ x: 0 }} transition={{ duration: 1 }}>
          <div className="order-lg-3 pt-4 pt-lg-0">
            <img src="../../../content/images/bannerImg.png" title="Banner EducationClick" alt="Banner EducationClick" className="w-100" />
          </div>
        </motion.div>
      </div>

      {/*About*/}
      <About />

      {/*Contact*/}
      <Contact />
    </div>
  );
};

export default Home;
