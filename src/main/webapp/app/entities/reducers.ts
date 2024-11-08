import teacher from 'app/entities/teacher/teacher.reducer';
import timeslot from 'app/entities/timeslot/timeslot.reducer';
import student from 'app/entities/student/student.reducer';
import booking from 'app/entities/booking/booking.reducer';
import subject from 'app/entities/subject/subject.reducer';
import meeting from 'app/entities/meeting/meeting.reducer';
import studyMaterial from 'app/entities/study-material/study-material.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  teacher,
  timeslot,
  student,
  booking,
  subject,
  meeting,
  studyMaterial,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
