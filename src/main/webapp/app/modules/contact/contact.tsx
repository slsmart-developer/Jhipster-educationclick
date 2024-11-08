import React, { useEffect, useRef, useState } from 'react';
import { useAppSelector } from 'app/config/store';
import { motion, useAnimation } from 'framer-motion';
import axios from 'axios';
import { useInView } from 'react-intersection-observer';
import { FaEnvelope, FaLocationArrow, FaPhoneAlt, FaUserAlt } from 'react-icons/fa';
import { FiSend } from 'react-icons/fi';
import { AiFillInstagram, AiFillLinkedin, AiFillTwitterSquare } from 'react-icons/ai';

export const Contact = () => {
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
    <div className="parent" style={{ paddingTop: '130px', paddingBottom: '130px' }}>
      <motion.div initial="hidden" animate={viewDiv && 'visible'} variants={headingAnimation} className="text-center">
        <h3 className="text-boxdark">Contact</h3>
        <h1 className="text-4xl font-weight-bold text-boxdark">
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
      <div className="row pt-lg-5">
        <motion.div className="col-md-6" ref={ref} initial="hidden" animate={viewDiv && 'visible'} variants={contactAnimation}>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                name="name"
                placeholder="Name"
                value={formData.name}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <input
                type="email"
                className="form-control"
                name="email"
                placeholder="Email"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                name="subject"
                placeholder="Subject"
                value={formData.subject}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <textarea
                className="form-control"
                name="message"
                placeholder="Message"
                value={formData.message}
                onChange={handleChange}
                required
              ></textarea>
            </div>
            <button
              type="submit"
              className="btn btn-primary d-inline-flex align-items-center justify-content-center gap-2 rounded-md py-2 px-3 px-lg-4 px-xl-5 font-weight-bold w-50"
            >
              <FiSend className="text-white" /> Send Message
            </button>
          </form>
        </motion.div>
        <motion.div className="col-md-6" initial={{ y: 50, opacity: 0 }} animate={viewDiv && 'visible'} variants={contactAnimation}>
          <div className="mb-3 d-flex align-items-center" style={{ paddingLeft: '30px' }}>
            <FaUserAlt className="me-2 header-text-color" size={20} />
            <h5 className="text-boxdark mb-0 m-lg-2">Mr. Director</h5>
          </div>
          <div className="mb-3 d-flex align-items-center" style={{ paddingLeft: '30px' }}>
            <FaPhoneAlt className="me-2 header-text-color" size={20} />
            <h5 className="text-boxdark mb-0 m-lg-2">041 - 2222222</h5>
          </div>
          <div className="mb-3 d-flex align-items-center" style={{ paddingLeft: '30px' }}>
            <FaEnvelope className="me-2 header-text-color" size={20} />
            <h5 className="text-boxdark mb-0 m-lg-2">info@educationclick.com</h5>
          </div>
          <div className="mb-3 d-flex align-items-center" style={{ paddingLeft: '30px' }}>
            <FaLocationArrow className="me-2 header-text-color" size={20} />
            <h5 className="text-boxdark mb-0 m-lg-2">Colombo, Sri Lanka</h5>
          </div>
          <div className="mt-4 d-flex align-items-center">
            <h5 className="text-boxdark pt-2" style={{ paddingLeft: '30px' }}>
              Social
            </h5>
            <div className="bg-black flex-grow-1 mx-1" style={{ height: '2px', maxWidth: '40px' }}></div>
            <a href="#" className="header-text-color mx-1" target="_blank" rel="noopener noreferrer">
              <AiFillLinkedin size={38} />
            </a>
            <a href="#" className="header-text-color mx-1" target="_blank" rel="noopener noreferrer">
              <AiFillTwitterSquare size={38} />
            </a>
            <a href="#" className="header-text-color mx-1" target="_blank" rel="noopener noreferrer">
              <AiFillInstagram size={38} />
            </a>
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default Contact;
