import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity } from './teacher.reducer';
import { FiArrowLeft, FiEdit } from 'react-icons/fi';
import { Calendar } from 'primereact/calendar';
import 'primereact/resources/themes/lara-light-cyan/theme.css';

export const TeacherDetail = () => {
  const dispatch = useAppDispatch();
  const [date, setDate] = useState(null);
  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const teacherEntity = useAppSelector(state => state.teacher.entity);
  return (
    <div className="text-dark">
      <div className="d-flex h-100 overflow-hidden">
        <div className="flex-grow-1">
          <main className="d-flex justify-content-center align-items-center mt-3">
            <div className="container">
              <h1 className="text-center text-zinc-600 my-4 font-sans xl:text-4xl lg:text-4xl md:text-3xl sm:text-3xl min-[320px]:text-3xl max-[639px]:text-4xl antialiased font-medium leading-snug tracking-normal ">
                Book Let's Book the Calendar Here
              </h1>
              <div className="row overflow-hidden">
                <div className="col-md-6 col-12 mb-3">
                  <div className="relative flex w-full flex-col rounded-xl bg-white mx-4 my-4 card">
                    <div className="relative mt-2 flex justify-center items-center overflow-hidden bg-white rounded-xl">
                      <img src="../../../content/images/icon/Teacher-boy.jpg" alt="ui/ux review" className="object-cover w-60 h-60" />
                    </div>

                    <div className="p-6 pt-2">
                      <div className="mb-2 text-center">
                        <h5 className="block font-sans text-3xl antialiased font-medium leading-snug tracking-normal text-gray-600">
                          {teacherEntity.name}
                        </h5>
                        <p className="text-zinc-500 text-sm py-2">{teacherEntity.email}</p>

                        <span className="my-1 inline-flex items-center rounded-md bg-indigo-50 px-2 py-1 text-xs font-medium text-indigo-700 ring-1 ring-inset ring-indigo-700/10 mr-1">
                          ICT
                        </span>
                        <span className="my-1 inline-flex items-center rounded-md bg-indigo-50 px-2 py-1 text-xs font-medium text-indigo-700 ring-1 ring-inset ring-indigo-700/10 mr-1">
                          English
                        </span>
                        <h4 className="text-sm mt-4 text-gray-400">Fee per hour</h4>
                        <h5 className="block font-sans text-xl antialiased font-bold leading-snug tracking-normal text-gray-700 flex justify-center items-center">
                          LKR {teacherEntity.feePerHour}
                        </h5>
                        <p>{teacherEntity.subject ? teacherEntity.subject.name : ''}</p>
                      </div>
                      <div className="flex flex-wrap justify-center items-center gap-3 mt-4 group">
                        <span
                          onClick={() => (window.location.href = `/teacher`)}
                          className="cursor-pointer rounded-full border border-gray-900/5 bg-gray-900/5 p-3 text-gray-900 transition-colors hover:border-gray-900/10 hover:bg-gray-900/10 hover:!opacity-100 group-hover:opacity-70"
                        >
                          <FiArrowLeft className="w-5 h-5" />
                        </span>
                        <span
                          onClick={() => (window.location.href = `/teacher/${teacherEntity.id}/edit`)}
                          className="cursor-pointer rounded-full border border-gray-900/5 bg-gray-900/5 p-3 text-gray-900 transition-colors hover:border-gray-900/10 hover:bg-gray-900/10 hover:!opacity-100 group-hover:opacity-70"
                        >
                          <FiEdit className="w-5 h-5" />
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="col-md-5 col-12 mb-3">
                  <div className="relative flex w-full flex-col rounded-xl bg-white mx-4 my-4">
                    <div className="overflow-hidden bg-white rounded-xl">
                      <div className="flex justify-content-center w-full">
                        <Calendar value={date} onChange={e => setDate(e.value)} inline showWeek />
                      </div>
                    </div>
                    <div className="relative mt-2 flex justify-center items-center overflow-hidden bg-white rounded-xl">
                      <button
                        className="block w-96 select-none rounded-lg bg-gray-900 py-3.5 px-7 text-center align-middle font-sans text-sm font-bold uppercase text-white shadow-md shadow-gray-900/10 transition-all hover:shadow-lg hover:shadow-gray-900/20 focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                        type="button"
                        onClick={() => (window.location.href = `/payment`)}
                      >
                        Book now
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </main>
        </div>
      </div>
    </div>
  );
};

export default TeacherDetail;
