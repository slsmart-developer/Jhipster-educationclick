import React from 'react';
import { Navigate, useLocation, useNavigate } from 'react-router-dom';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { login } from 'app/shared/reducers/authentication';
import LoginModal from './login-modal';
import { FaEnvelope, FaLocationArrow, FaPhoneAlt, FaUserAlt } from 'react-icons/fa';
import { FiSend } from 'react-icons/fi';
import { AiFillInstagram, AiFillLinkedin, AiFillTwitterSquare } from 'react-icons/ai';
import LoginForm from './login-modal';

export const Login = () => {
  const dispatch = useAppDispatch();
  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const loginError = useAppSelector(state => state.authentication.loginError);
  const navigate = useNavigate();
  const pageLocation = useLocation();

  const handleLogin = (username, password, rememberMe = false) => dispatch(login(username, password, rememberMe));

  const { from } = pageLocation.state || { from: { pathname: '/profile', search: pageLocation.search } };
  if (isAuthenticated) {
    return <Navigate to={from} replace />;
  }

  return (
    <div className="py-5 min-h-screen rounded-sm">
      <div className="container-fluid">
        <div className="row flex-wrap align-items-center min-h-screen pt-5 pb-5">
          <div className="col-xl-6 d-none d-xl-block">
            <div className="py-4 px-5 text-center">
              <img
                className="img-fluid mb-4 items-start"
                src="../../../content/images/logo-education-click.png"
                alt="Logo EducationClick"
                style={{ width: '50%' }}
              />
              <h3 style={{ color: 'rgb(89,89,89)' }}>Welcome to EducationClick</h3>
              <p style={{ color: 'rgb(89,89,89)' }}>Your Pathway to Personalized Learning</p>
              <img className="mt-4 img-fluid" src="../../../content/images/login.svg" alt="Logo" style={{ width: '70%' }} />
            </div>
          </div>

          {/* Right Column - Takes full width on smaller screens and half width on xl screens */}
          <div className="col-xl-6 col-12">
            <div className="p-4 sm:p-5 xl:p-5" style={{ borderLeft: '1px solid #80808047' }}>
              <LoginForm handleLogin={handleLogin} loginError={loginError} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Login;
