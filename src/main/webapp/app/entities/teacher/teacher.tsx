import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { getPaginationState } from 'react-jhipster';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { deleteEntity, getEntities, reset } from './teacher.reducer';
import { FiEdit, FiEye, FiTrash } from 'react-icons/fi';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const Teacher = () => {
  const dispatch = useAppDispatch();
  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const teacherList = useAppSelector(state => state.teacher.entities);
  const loading = useAppSelector(state => state.teacher.loading);
  const links = useAppSelector(state => state.teacher.links);
  const updateSuccess = useAppSelector(state => state.teacher.updateSuccess);
  const [loadModal, setLoadModal] = useState(false);
  const [teacherToDeleteId, setTeacherToDeleteId] = useState(null);
  const handleClose = () => {
    setLoadModal(false);
  };
  const openModal = id => {
    setLoadModal(true);
    setTeacherToDeleteId(id);
  };
  const confirmDelete = () => {
    if (teacherToDeleteId) {
      dispatch(deleteEntity(teacherToDeleteId));
      setTeacherToDeleteId(null);
      handleClose();
    }
  };
  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h1 className="text-center text-zinc-600 my-4 font-sans xl:text-4xl lg:text-4xl md:text-3xl sm:text-3xl min-[320px]:text-3xl max-[639px]:text-4xl antialiased font-medium leading-snug tracking-normal ">
        Book Your Teachers Here For Tuitoring Sessions
      </h1>
      <InfiniteScroll
        dataLength={teacherList ? teacherList.length : 0}
        next={handleLoadMore}
        hasMore={paginationState.activePage - 1 < links.next}
        loader={<div className="loader">Loading ...</div>}
      >
        {teacherList && teacherList.length > 0 ? (
          <main className="flex justify-center items-center flex-wrap mt-8">
            {teacherList.map((teacher, i) => (
              <div className="relative flex w-full max-w-[25rem] flex-col rounded-xl bg-white shadow-lg mx-4 my-4">
                <div className="relative mt-2 flex justify-center items-center overflow-hidden bg-white rounded-xl">
                  <img src="../../../content/images/icon/Teacher-boy.jpg" alt="ui/ux review" className="object-cover w-60 h-60" />
                </div>

                <div className="p-6 pt-2">
                  <div className="mb-2 text-center">
                    <h5 className="block font-sans text-2xl antialiased font-medium leading-snug tracking-normal text-blue-gray-900">
                      {teacher.name}
                    </h5>
                    <p className="text-zinc-500 text-sm mt-1">{teacher.email}</p>
                    <p className="text-zinc-500 text-sm mt-1">0779707981</p>
                  </div>
                  <div className="flex flex-wrap justify-center items-center gap-3 mt-4 group">
                    <span
                      onClick={() => (window.location.href = `/teacher/${teacher.id}`)}
                      className="cursor-pointer rounded-full border border-gray-900/5 bg-gray-900/5 p-3 text-gray-900 transition-colors hover:border-gray-900/10 hover:bg-gray-900/10 hover:!opacity-100 group-hover:opacity-70"
                    >
                      <FiEye className="w-5 h-5" />
                    </span>
                    <span
                      onClick={() => (window.location.href = `/teacher/${teacher.id}/edit`)}
                      className="cursor-pointer rounded-full border border-gray-900/5 bg-gray-900/5 p-3 text-gray-900 transition-colors hover:border-gray-900/10 hover:bg-gray-900/10 hover:!opacity-100 group-hover:opacity-70"
                    >
                      <FiEdit className="w-5 h-5" />
                    </span>
                    <span
                      onClick={() => openModal(teacher.id)}
                      className="cursor-pointer rounded-full border border-gray-900/5 bg-gray-900/5 p-3 text-gray-900 transition-colors hover:border-gray-900/10 hover:bg-gray-900/10 hover:!opacity-100 group-hover:opacity-70"
                    >
                      <FiTrash className="w-5 h-5" />
                    </span>
                  </div>
                </div>
                <div className="p-6 pt-1">
                  <button
                    className="block w-full select-none rounded-lg bg-gray-900 py-3.5 px-7 text-center align-middle font-sans text-sm font-bold uppercase text-white shadow-md shadow-gray-900/10 transition-all hover:shadow-lg hover:shadow-gray-900/20 focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                    type="button"
                    onClick={() => (window.location.href = `/teacher-profile`)}
                  >
                    Book now (LKR {teacher.feePerHour})
                  </button>
                </div>
              </div>
            ))}
          </main>
        ) : (
          !loading && <div className="alert alert-warning">No Teachers found</div>
        )}
      </InfiniteScroll>

      <Modal isOpen={loadModal} toggle={handleClose}>
        <ModalHeader toggle={handleClose} data-cy="teacherDeleteDialogHeading">
          Confirm delete operation
        </ModalHeader>
        <ModalBody id="educationclickApp.teacher.delete.question">Are you sure you want to delete Teacher?</ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp; Cancel
          </Button>
          <Button id="jhi-confirm-delete-teacher" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp; Delete
          </Button>
        </ModalFooter>
      </Modal>
    </div>
  );
};

export default Teacher;
