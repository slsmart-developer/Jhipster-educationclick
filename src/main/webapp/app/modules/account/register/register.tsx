import React, { useState, useEffect } from 'react';
import { ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { Row, Col, Alert, Button } from 'reactstrap';
import { toast } from 'react-toastify';
import { Link } from 'react-router-dom';

import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { handleRegister, reset } from './register.reducer';

export const RegisterPage = () => {
  const [password, setPassword] = useState('');
  const dispatch = useAppDispatch();

  useEffect(
    () => () => {
      dispatch(reset());
    },
    [],
  );

  const handleValidSubmit = ({
    username,
    email,
    firstPassword,
    contactNo,
    dateOfBirth,
    gender,
    guardianName,
    address,
    guardianContact,
    guardianEmail,
  }) => {
    dispatch(
      handleRegister({
        login: username,
        email,
        password: firstPassword,
        contactNo,
        dateOfBirth,
        gender,
        guardianName,
        address,
        guardianContact,
        guardianEmail,

        langKey: 'en',
      }),
    );
  };

  const updatePassword = event => setPassword(event.target.value);

  const successMessage = useAppSelector(state => state.register.successMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(successMessage);
    }
  }, [successMessage]);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1 id="register-title" data-cy="registerTitle">
            Registration
          </h1>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          <ValidatedForm id="register-form" onSubmit={handleValidSubmit}>
            <ValidatedField
              name="username"
              label="Username"
              placeholder="Your username"
              validate={{
                required: { value: true, message: 'Your username is required.' },
                pattern: {
                  value: /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/,
                  message: 'Your username is invalid.',
                },
                minLength: { value: 1, message: 'Your username is required to be at least 1 character.' },
                maxLength: { value: 50, message: 'Your username cannot be longer than 50 characters.' },
              }}
              data-cy="username"
            />
            <ValidatedField
              name="email"
              label="Email"
              placeholder="Your email"
              type="email"
              validate={{
                required: { value: true, message: 'Your email is required.' },
                minLength: { value: 5, message: 'Your email is required to be at least 5 characters.' },
                maxLength: { value: 254, message: 'Your email cannot be longer than 50 characters.' },
                validate: v => isEmail(v) || 'Your email is invalid.',
              }}
              data-cy="email"
            />
            <ValidatedField
              name="firstPassword"
              label="New password"
              placeholder="New password"
              type="password"
              onChange={updatePassword}
              validate={{
                required: { value: true, message: 'Your password is required.' },
                minLength: { value: 4, message: 'Your password is required to be at least 4 characters.' },
                maxLength: { value: 50, message: 'Your password cannot be longer than 50 characters.' },
              }}
              data-cy="firstPassword"
            />
            <PasswordStrengthBar password={password} />
            <ValidatedField
              name="secondPassword"
              label="New password confirmation"
              placeholder="Confirm the new password"
              type="password"
              validate={{
                required: { value: true, message: 'Your confirmation password is required.' },
                minLength: { value: 4, message: 'Your confirmation password is required to be at least 4 characters.' },
                maxLength: { value: 50, message: 'Your confirmation password cannot be longer than 50 characters.' },
                validate: v => v === password || 'The password and its confirmation do not match!',
              }}
              data-cy="secondPassword"
            />

            <ValidatedField
              name="contactNo"
              label="contact No"
              placeholder="Your contact No"
              type="text"
              validate={{
                required: { value: true, message: 'Your contact No is required.' },
                minLength: { value: 10, message: 'Your contact No must be at least 10 digits.' },
                maxLength: { value: 15, message: 'Your contact No cannot be longer than 15 digits.' },
                pattern: {
                  value: /^[0-9]+$/, // This ensures only numbers are accepted
                  message: 'Your contact No can only contain digits.',
                },
              }}
              data-cy="contactNo"
            />

            <ValidatedField
              name="dateOfBirth"
              label="Date of Birth"
              placeholder="Your date of birth"
              type="date"
              validate={{
                required: { value: true, message: 'Your date of birth is required.' },
              }}
              data-cy="dateOfBirth"
            />

            <ValidatedField
              name="gender"
              label="Gender"
              placeholder="Your gender"
              type="text"
              validate={{
                required: { value: true, message: 'Your gender is required.' },
                minLength: { value: 1, message: 'Your gender is required to be at least 1 character.' },
                maxLength: { value: 50, message: 'Your gender cannot be longer than 50 characters.' },
              }}
              data-cy="gender"
            />

            <ValidatedField
              name="guardianName"
              label="Guardian Name"
              placeholder="Your guardian name"
              type="text"
              validate={{
                maxLength: { value: 50, message: 'Your guardian name cannot be longer than 50 characters.' },
              }}
              data-cy="guardianName"
            />

            <ValidatedField
              name="address"
              label="Address"
              placeholder="Your address"
              type="text"
              validate={{
                maxLength: { value: 50, message: 'Your address cannot be longer than 50 characters.' },
              }}
              data-cy="address"
            />

            <ValidatedField
              name="guardianContact"
              label="Guardian Contact"
              placeholder="Your guardian contact"
              type="text"
              validate={{
                minLength: { value: 10, message: 'Your guardian contact must be at least 10 digits.' },
                maxLength: { value: 15, message: 'Your guardian contact cannot be longer than 15 digits.' },
                pattern: {
                  value: /^[0-9]+$/,
                  message: 'Your guardian contact can only contain digits.',
                },
              }}
              data-cy="guardianContact"
            />

            <ValidatedField
              name="guardianEmail"
              label="Guardian Email"
              placeholder="Your guardian email"
              type="email"
              validate={{
                maxLength: { value: 254, message: 'Your guardian email cannot be longer than 254 characters.' },
                validate: v => isEmail(v) || 'Your guardian email is invalid.',
              }}
              data-cy="guardianEmail"
            />

            <ValidatedField
              name="subjects"
              label="Subjects"
              placeholder="Your subjects"
              type="text"
              validate={{
                minLength: { value: 1, message: 'Please select at least one subject.' },
              }}
              data-cy="subjects"
            />

            <Button id="register-submit" color="primary" type="submit" data-cy="submit">
              Register
            </Button>
          </ValidatedForm>
          <p>&nbsp;</p>
          <Alert color="warning">
            <span>If you want to </span>
            <Link to="/login" className="alert-link">
              sign in
            </Link>
            <span>
              , you can try the default accounts:
              <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;) <br />- User (login=&quot;user&quot; and
              password=&quot;user&quot;).
            </span>
          </Alert>
        </Col>
      </Row>
    </div>
  );
};

export default RegisterPage;
