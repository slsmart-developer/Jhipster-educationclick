import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Teacher from './teacher';
import Timeslot from './timeslot';
import Student from './student';
import Booking from './booking';
import Subject from './subject';
import Meeting from './meeting';
import StudyMaterial from './study-material';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="teacher/*" element={<Teacher />} />
        <Route path="timeslot/*" element={<Timeslot />} />
        <Route path="student/*" element={<Student />} />
        <Route path="booking/*" element={<Booking />} />
        <Route path="subject/*" element={<Subject />} />
        <Route path="meeting/*" element={<Meeting />} />
        <Route path="study-material/*" element={<StudyMaterial />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
