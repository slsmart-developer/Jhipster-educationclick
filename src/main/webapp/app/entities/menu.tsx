import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/teacher">
        Teacher
      </MenuItem>
      <MenuItem icon="asterisk" to="/timeslot">
        Timeslot
      </MenuItem>
      <MenuItem icon="asterisk" to="/student">
        Student
      </MenuItem>
      <MenuItem icon="asterisk" to="/booking">
        Booking
      </MenuItem>
      <MenuItem icon="asterisk" to="/subject">
        Subject
      </MenuItem>
      <MenuItem icon="asterisk" to="/meeting">
        Meeting
      </MenuItem>
      <MenuItem icon="asterisk" to="/study-material">
        Study Material
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
