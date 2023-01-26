import "./Worker.css"
import {IWorkerWithId} from "../../models/IWorkerWithId";
import axios from "axios";
import {useState} from "react";
import Modal from "../Modal/Modal";
import WorkerActions from "../worker_actions/WorkerActions";
import {IWorker} from "../../models/IWorker";

interface WorkerProps {
    worker: IWorkerWithId
    submitError: (text: string) => void
}

function Worker({worker, submitError}: WorkerProps) {

    const [isSuccessDelete, setSuccessDelete] = useState(false)
    const [isNotSuccessDelete, setNotSuccessDelete] = useState(false)

    const [editWorkerVisibility, setEditWorkerVisibility] = useState(false)

    const convert = (work: IWorker) => {
        let startDate = work.startDate
        let date_ = new Date(Number(startDate.slice(6, 10)), Number(startDate.slice(3,5)) - 1, Number(startDate.slice(0,2)),
            Number(startDate.slice(13,15)), Number(startDate.slice(16,18)), Number(startDate.slice(19,21)))
        work = {...work, startDate: date_.toISOString().slice(0,-1)}
        return work
    }

    const deleteWorker = () => {
        axios.delete(process.env.REACT_APP_BACKEND_URL + `/workers/${worker.id}`)
            .then(r => {
                setSuccessDelete(true)
                setTimeout(() => {
                    setSuccessDelete(false)
                }, 2000)
            })
            .catch(r => {
                submitError("Ошибка! Не удалось удалить рабочего")
            })
    }

    return (
        <>
            <div className="worker-container">
                <p className="label">
                    Информация о работнике
                </p>

                <p>Coordinates: {worker.coordinates.x} {worker.coordinates.y}</p>
                <p>Start date: {worker.startDate}</p>
                <p>End date: {worker.endDate}</p>
                <p>Creation date: {worker.creationDate}</p>
                <p>Organization name: {worker.organization.name}</p>
                <p>Organization ID: {worker.organization.id}</p>
                <p>Worker ID: {worker.id}</p>
                <p>Worker name: {worker.name}</p>
                <p>Person ID: {worker.person.id}</p>
                <p>Person eye color: {worker.person.eyeColor}</p>
                <p>Person height: {worker.person.height}</p>
                <p>Person passport ID: {worker.person.passportID}</p>
                <p>Location name: {worker.person.location.name}</p>
                <p>Location coordinates: {worker.person.location.x} {worker.person.location.y} {worker.person.location.z}</p>
                <p>Location ID: {worker.person.location.id}</p>
                <p>Position: {worker.position}</p>

                <button className="button worker-button" onClick={() => setEditWorkerVisibility(true)}>
                    Редактировать
                </button>

                <button className="button worker-button" onClick={deleteWorker}>
                    Удалить
                </button>
                {
                    isSuccessDelete && <div className="find-status find-status-green">
                        Рабочий успешно удален
                    </div>
                }
                {
                    isNotSuccessDelete && <div className="find-status">
                        Не удалось найти рабочего
                    </div>
                }
            </div>
            {
                editWorkerVisibility && <Modal onClose={() => setEditWorkerVisibility(false)}>
                    <WorkerActions isEdit={true} workerID={worker.id} worker={convert(worker)}></WorkerActions>
                </Modal>
            }
        </>
    )
}

export default Worker