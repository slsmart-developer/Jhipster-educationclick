import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './timeslot.reducer';

export const TimeslotDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const timeslotEntity = useAppSelector(state => state.timeslot.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="timeslotDetailsHeading">Timeslot</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{timeslotEntity.id}</dd>
          <dt>
            <span id="startTime">Start Time</span>
          </dt>
          <dd>{timeslotEntity.startTime ? <TextFormat value={timeslotEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endTime">End Time</span>
          </dt>
          <dd>{timeslotEntity.endTime ? <TextFormat value={timeslotEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="availability">Availability</span>
          </dt>
          <dd>{timeslotEntity.availability ? 'true' : 'false'}</dd>
          <dt>Teacher</dt>
          <dd>{timeslotEntity.teacher ? timeslotEntity.teacher.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/timeslot" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/timeslot/${timeslotEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TimeslotDetail;
