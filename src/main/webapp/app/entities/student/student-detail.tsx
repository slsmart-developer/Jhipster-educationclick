import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './student.reducer';

export const StudentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const studentEntity = useAppSelector(state => state.student.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="studentDetailsHeading">Student</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{studentEntity.id}</dd>
          <dt>
            <span id="firstName">First Name</span>
          </dt>
          <dd>{studentEntity.firstName}</dd>
          <dt>
            <span id="lastName">Last Name</span>
          </dt>
          <dd>{studentEntity.lastName}</dd>
          <dt>
            <span id="contactNo">Contact No</span>
          </dt>
          <dd>{studentEntity.contactNo}</dd>
          <dt>
            <span id="dateOfBirth">Date Of Birth</span>
          </dt>
          <dd>
            {studentEntity.dateOfBirth ? <TextFormat value={studentEntity.dateOfBirth} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="gender">Gender</span>
          </dt>
          <dd>{studentEntity.gender}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{studentEntity.email}</dd>
          <dt>
            <span id="password">Password</span>
          </dt>
          <dd>{studentEntity.password}</dd>
          <dt>
            <span id="username">Username</span>
          </dt>
          <dd>{studentEntity.username}</dd>
          <dt>
            <span id="guardianName">Guardian Name</span>
          </dt>
          <dd>{studentEntity.guardianName}</dd>
          <dt>
            <span id="address">Address</span>
          </dt>
          <dd>{studentEntity.address}</dd>
          <dt>
            <span id="guardianContact">Guardian Contact</span>
          </dt>
          <dd>{studentEntity.guardianContact}</dd>
          <dt>
            <span id="guardianEmail">Guardian Email</span>
          </dt>
          <dd>{studentEntity.guardianEmail}</dd>
        </dl>
        <Button tag={Link} to="/student" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/student/${studentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StudentDetail;
