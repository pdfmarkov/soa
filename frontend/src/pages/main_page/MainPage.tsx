import "./MainPage.css"
import GrayPlate from "../../components/gray_plate/GrayPlate";
import {useState} from "react";
import axios from "axios";
import {IWorkerWithId} from "../../models/IWorkerWithId";
import Worker from "../../components/worker/Worker";
import WorkerActions from "../../components/worker_actions/WorkerActions";
import SeacrhWorkers from "../../components/search_workers/SeacrhWorkers";
import Modal from "../../components/Modal/Modal";

export const tmpWorker: IWorkerWithId = {
    "coordinates": {
        "x": 0,
        "y": 3.1
    },
    "endDate": "2022-11-27T14:44:03",
    "creationDate": "2022-11-28",
    "organization": {
        "id": 1,
        "name": "string"
    },
    "id": 1,
    "name": "string",
    "salary": 1,
    "person": {
        "id": 1,
        "eyeColor": "BLUE",
        "height": 1,
        "location": {
            "id": 1,
            "name": "string",
            "x": 1.1,
            "y": 0,
            "z": 1.1
        },
        "passportID": "string"
    },
    "position": "CLEANER",
    "startDate": "02/01/2018 - 13:45:30 +0000"
}

function MainPage() {
    const [worker, setWorker] = useState<IWorkerWithId>()
    const [workerId, setWorkerId] = useState<number>()

    const [createWorkerVisibility, setCreateWorkerVisibility] = useState(false)

    const [errorVisibility, setErrorVisibility] = useState(false)
    const [workerVisibility, setWorkerVisibility] = useState(false)

    const [endDate, setEndDate] = useState("")
    const [position, setPosition] = useState("CLEANER")

    const [workersEndDate, setWorkersEndDate] = useState(-1)
    const [workersSmallerPosition, setWorkersSmallerPosition] = useState(-1)

    const [fireId, setFireId] = useState<number>()
    const [isFired, setIsFired] = useState(false)
    const [successFired, setSuccessFired] = useState<boolean>()

    const [hirePersonId, setHirePersonId] = useState<number>()
    const [hireOrganizationId, setHireOrganizationId] = useState<number>()
    const [hirePosition, setHirePosition] = useState<string>()
    const [hireStartDate, setHireStartDate] = useState<string>()

    const [hireId, setHireId] = useState<number>()
    const [isHired, setIsHired] = useState(false)
    const [successHired, setSuccessHired] = useState<boolean>()

    const [findByName, setFindByName] = useState("")

    const [tableVisible, setTableVisible] = useState(false)
    const [workers, setWorkers] = useState<IWorkerWithId[]>()
    const [error, setError] = useState(false)

    const [findByNamePage, setFindByNamePage] = useState(0)

    const [isError, setIsError] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")

    const handleChangeFindByNamePage = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setFindByNamePage(possibleNumber)
    }

    const handleChangeFindByName = (event: React.FormEvent) => {
        setFindByName((event.target as HTMLInputElement).value)
    }

    const handleChangeWorkerId = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setWorkerId(possibleNumber)
    }

    const showFindErrorStatus = () => {
        setErrorVisibility(true)
        setTimeout(() => {
            setErrorVisibility(false)
        }, 5000)
    }

    const getWorkerById = () => {
        axios.get<IWorkerWithId>(process.env.REACT_APP_BACKEND_URL + `/workers/${workerId}`)
            .then(r => {
                setWorker(r.data)
                setWorkerVisibility(true)
            })
            .catch(e => {
                submitError("Ошибка! Не удалось найти работника")
            })
    }

    const getBiggerEndDate = () => {
        axios.get(process.env.REACT_APP_BACKEND_URL + `/workers/end-date/${endDate}`)
            .then(r => {
                setWorkersEndDate(r.data.value)
            })
            .catch(e => {
                submitError("Ошибка! Не удалось получить количество")
            })
    }

    const getSmallerPosition = () => {
        axios.get(process.env.REACT_APP_BACKEND_URL + `/workers/position/${position}`)
            .then(r => {
                setWorkersSmallerPosition(r.data.value)
            })
            .catch(e => {
                submitError("Ошибка! Не удалось получить количество")
            })
    }

    const fireById = () => {
        axios.get(process.env.REACT_APP_HR_URL + `/hr/fire/${fireId}`)
            .then(r => {
                setIsFired(true)
                setSuccessFired(true)
                setTimeout(() => {
                    setIsFired(false)
                }, 2000)
            })
            .catch(e => {
                submitError("Ошибка! Не удалось уволить работника")
            })
    }

    const handleChangeEndDate = (event: React.FormEvent) => {
        setEndDate((event.target as HTMLInputElement).value)
    }

    const handleChangePosition = (event: React.FormEvent) => {
        setPosition((event.target as HTMLInputElement).value)
    }

    const handleChangeFireId = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setFireId(possibleNumber)
    }

    const handleChangeHirePersonId = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setHirePersonId(possibleNumber)
    }

    const handleChangeHireOrganizationId = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setHireOrganizationId(possibleNumber)
    }

    const handleChangeHirePosition = (event: React.FormEvent) => {
        setHirePosition((event.target as HTMLInputElement).value)
    }

    const handleChangeHireStartDate = (event: React.FormEvent) => {
        setHireStartDate((event.target as HTMLInputElement).value)
    }

    const hirePerson = () => {
        axios.get(process.env.REACT_APP_HR_URL + `/hr/hire/${hirePersonId}/${hireOrganizationId}/${hirePosition}/${hireStartDate}`)
            .then(r => {
                setIsHired(true)
                setSuccessHired(true)
                setTimeout(() => {
                    setIsHired(false)
                }, 2000)
            })
            .catch(e => {
                submitError("Ошибка! Не удалось нанять работника")
            })
    }

    const findName = () => {
        axios.get(process.env.REACT_APP_BACKEND_URL + `/workers/name/${findByName}`)
            .then(r => {
                setWorkers(r.data)
                setTableVisible(true)
            })
            .catch(e => {
                submitError("Ошибка! Не удалось найти работников")
            })
    }

    const submitError = (text: string) => {
        setErrorMessage(text)
        setIsError(true)
        setTimeout(() => {
            setIsError(false)
        }, 5000)
    }

    return (
        <>
            <div className="container">
                <div className="column">
                    <GrayPlate>
                        <button className="button" onClick={() => setCreateWorkerVisibility(true)}>
                            Создать рабочего
                        </button>
                    </GrayPlate>
                    <GrayPlate>
                        <label>
                            Поиск рабочего по id
                        </label>
                        <div className="find-worker-container">
                            <input className="input find-worker-input" value={workerId} onChange={handleChangeWorkerId}/>
                            <button className="button find-worker-button" onClick={getWorkerById}>
                                Поиск
                            </button>
                        </div>
                        {
                            workerVisibility &&
                            <>
                                <Worker worker={worker!} submitError={submitError}/>
                                <button className="button hide-button" onClick={() => setWorkerVisibility(false)}>
                                    Закрыть
                                </button>
                            </>
                        }
                    </GrayPlate>
                    <GrayPlate>
                        <div className="find-enddate-container">
                            <label>
                                Количество рабочих с большим End date
                            </label>
                            {
                                workersEndDate >= 0 && workersEndDate
                            }
                            <input type="datetime-local" className="input"
                                value={endDate} onChange={handleChangeEndDate}/>
                            <button className="button" onClick={getBiggerEndDate}>
                                Поиск
                            </button>
                        </div>
                    </GrayPlate>
                    <GrayPlate>
                        <div className="find-enddate-container">
                            <label>
                                Количество рабочих с меньшей позицией
                            </label>
                            {
                                workersSmallerPosition >= 0 && workersSmallerPosition
                            }
                            <select value={position} onChange={handleChangePosition}>
                                <option value={"CLEANER"}>CLEANER</option>
                                <option value={"DEVELOPER"}>DEVELOPER</option>
                                <option value={"HUMAN_RESOURCES"}>HUMAN_RESOURCES</option>
                                <option value={"LABORER"}>LABORER</option>
                                <option value={"LEAD_DEVELOPER"}>LEAD_DEVELOPER</option>
                            </select>
                            <button className="button" onClick={getSmallerPosition}>
                                Поиск
                            </button>
                        </div>
                    </GrayPlate>
                    <GrayPlate>
                        <SeacrhWorkers submitError={submitError}/>
                    </GrayPlate>
                </div>

                <div className="column">
                    <GrayPlate>
                        <div className="hire-container">
                            <label>
                                <b>Нанять человека</b>
                            </label>
                            <label>
                                Person ID
                            </label>
                            <input className="worker-input" value={hirePersonId}
                                onChange={handleChangeHirePersonId}/>
                            <label>
                                Organization ID
                            </label>
                            <input className="worker-input" value={hireOrganizationId}
                                onChange={handleChangeHireOrganizationId}/>
                            <label>
                                Position
                            </label>
                            <select value={hirePosition} onChange={handleChangeHirePosition}>
                                <option value={"CLEANER"}>CLEANER</option>
                                <option value={"DEVELOPER"}>DEVELOPER</option>
                                <option value={"HUMAN_RESOURCES"}>HUMAN_RESOURCES</option>
                                <option value={"LABORER"}>LABORER</option>
                                <option value={"LEAD_DEVELOPER"}>LEAD_DEVELOPER</option>
                            </select>
                            <label>
                                Start Date
                            </label>
                            <input className="worker-input" type="datetime-local" value={hireStartDate}
                                onChange={handleChangeHireStartDate}/>
                            <button className="button" onClick={hirePerson}>
                                Нанять
                            </button>
                            {
                                isHired && (successHired ?
                                    <div className="find-status-ok">
                                        Успешно нанят
                                    </div>
                                    :
                                    <div className="find-status">
                                        Нанять не вышло
                                    </div>)
                            }
                        </div>
                    </GrayPlate>
                    <GrayPlate>
                        <label>
                            Уволить человека (ID)
                        </label>
                        <input className="worker-input" value={fireId} onChange={handleChangeFireId}/>
                        <button className="button" onClick={fireById}>
                            Уволить
                        </button>
                        {
                            isFired && (successFired ?
                                <div className="find-status-ok">
                                    Успешно уволен
                                </div>
                                :
                                <div className="find-status">
                                    Уволить не вышло
                                </div>)
                        }
                    </GrayPlate>
                    <GrayPlate>
                        <h4>Поиск по имени</h4>
                        <label>
                            Имя
                        </label>
                        <input className="worker-input" value={findByName}
                            onChange={handleChangeFindByName}/>
                        <button className="button" onClick={findName}>
                            Поиск
                        </button>
                        {
                            tableVisible && <table className="workers-table">
                                <thead>
                                <th>x</th>
                                <th>y</th>
                                <th>name</th>
                                <th>position</th>
                                <th>organization</th>
                                <th>id</th>
                                <th>salary</th>
                                <th>startDate</th>
                                <th>eyeColor</th>
                                </thead>
                                <tbody>
                                {
                                    workers!.map(worker => {
                                        return (
                                            <tr>
                                                <td>{worker.coordinates.x}</td>
                                                <td>{worker.coordinates.y}</td>
                                                <td>{worker.name}</td>
                                                <td>{worker.position}</td>
                                                <td>{worker.organization == null ? "Не состоит" : worker.organization.name}</td>
                                                <td>{worker.id}</td>
                                                <td>{worker.salary}</td>
                                                <td>{worker.startDate}</td>
                                                <td>{worker.person.eyeColor}</td>
                                            </tr>
                                        )
                                    })
                                }
                                </tbody>
                            </table>
                        }
                    </GrayPlate>
                </div>
            </div>
            {
                createWorkerVisibility && <Modal onClose={() => setCreateWorkerVisibility(false)}>
                    <WorkerActions isEdit={false}/>
                </Modal>
            }
            {
                isError && <div className="error-message">
                    {errorMessage}
                </div>
            }
        </>
    )
}

export default MainPage