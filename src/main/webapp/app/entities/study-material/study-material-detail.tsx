import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './study-material.reducer';

export const StudyMaterialDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const studyMaterialEntity = useAppSelector(state => state.studyMaterial.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="studyMaterialDetailsHeading">Study Material</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{studyMaterialEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{studyMaterialEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{studyMaterialEntity.description}</dd>
          <dt>
            <span id="file">File</span>
          </dt>
          <dd>
            {studyMaterialEntity.file ? (
              <div>
                {studyMaterialEntity.fileContentType ? (
                  <a onClick={openFile(studyMaterialEntity.fileContentType, studyMaterialEntity.file)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {studyMaterialEntity.fileContentType}, {byteSize(studyMaterialEntity.file)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="uploadDate">Upload Date</span>
          </dt>
          <dd>
            {studyMaterialEntity.uploadDate ? (
              <TextFormat value={studyMaterialEntity.uploadDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Teacher</dt>
          <dd>{studyMaterialEntity.teacher ? studyMaterialEntity.teacher.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/study-material" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/study-material/${studyMaterialEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StudyMaterialDetail;
