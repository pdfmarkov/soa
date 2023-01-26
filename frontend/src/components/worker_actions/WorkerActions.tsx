import "./WorkerActions.css"
import {IWorker} from "../../models/IWorker";
import {useState} from "react";
import axios from "axios";
import {IWorkerWithId} from "../../models/IWorkerWithId";

interface WorkerActionsProps {
    isEdit: boolean,
    worker?: IWorker,
    workerID?: number
}

function WorkerActions({isEdit, worker, workerID}: WorkerActionsProps) {
    const createEmptyWorker = () => {
        return {
            "coordinates": {
                "x": 0,
                "y": 0
            },
            "organization": {
                "name": ""
            },
            "endDate": "",
            "name": "",
            "salary": 0,
            "person": {
                "height": 0,
                "location": {
                    "name": "",
                    "x": 0,
                    "y": 0,
                    "z": 0
                },
                "passportID": "",
                "eyeColor": "BLUE"
            },
            "position": "CLEANER",
            "startDate": ""
        }
    }

    const [isAction, setAction] = useState(false)
    const [isSuccessAction, setSuccessAction] = useState<boolean>()

    const [aWorker, setAWorker] = useState<IWorker>(isEdit ? worker! : createEmptyWorker())

    const handleChangeCoordX = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setAWorker(prev => {
            return {
                ...prev,
                coordinates: {
                    ...prev.coordinates,
                    x: possibleNumber
                }
            }
        })
    }

    const handleChangeCoordY = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setAWorker(prev => {
            return {
                ...prev,
                coordinates: {
                    ...prev.coordinates,
                    y: possibleNumber
                }
            }
        })
    }

    const handleChangeWorkerName = (event: React.FormEvent) => {
        setAWorker(prev => {
            return {
                ...prev,
                name: (event.target as HTMLInputElement).value
            }
        })
    }

    const handleChangeSalary = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setAWorker(prev => {
            return {
                ...prev,
                salary: possibleNumber
            }
        })
    }

    const handleChangeOrganizationName = (event: React.FormEvent) => {
        setAWorker(prev => {
            return {
                ...prev,
                organization: {
                    ...prev.organization,
                    name: (event.target as HTMLInputElement).value
                }
            }
        })
    }

    const handleChangePersonPassportID = (event: React.FormEvent) => {
        setAWorker(prev => {
            return {
                ...prev,
                person: {
                    ...prev.person,
                    passportID: (event.target as HTMLInputElement).value
                }
            }
        })
    }

    const handlePersonHeight = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setAWorker(prev => {
            return {
                ...prev,
                person: {
                    ...prev.person,
                    height: possibleNumber
                }
            }
        })
    }

    const handleChangeLocationCoordX = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setAWorker(prev => {
            return {
                ...prev,
                person: {
                    ...prev.person,
                    location: {
                        ...prev.person.location,
                        x: possibleNumber
                    }
                }
            }
        })
    }

    const handleChangeLocationCoordY = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setAWorker(prev => {
            return {
                ...prev,
                person: {
                    ...prev.person,
                    location: {
                        ...prev.person.location,
                        y: possibleNumber
                    }
                }
            }
        })
    }

    const handleChangeLocationCoordZ = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setAWorker(prev => {
            return {
                ...prev,
                person: {
                    ...prev.person,
                    location: {
                        ...prev.person.location,
                        z: possibleNumber
                    }
                }
            }
        })
    }

    const handleChangePosition = (event: React.FormEvent) => {
        setAWorker(prev => {
            return {
                ...prev,
                position: (event.target as HTMLInputElement).value
            }
        })
    }

    const handleChangeEyeColor = (event: React.FormEvent) => {
        setAWorker(prev => {
            return {
                ...prev,
                person: {
                    ...prev.person,
                    eyeColor: (event.target as HTMLInputElement).value
                }
            }
        })
    }

    const handleChangeStartDate = (event: React.FormEvent) => {
        setAWorker(prev => {
            return {
                ...prev,
                startDate: (event.target as HTMLInputElement).value + ":00"
            }
        })
    }

    const handleChangeEndDate = (event: React.FormEvent) => {
        setAWorker(prev => {
            return {
                ...prev,
                endDate: (event.target as HTMLInputElement).value + ":00"
            }
        })
    }

    const actionButton = () => {
        let sendWorker = aWorker
        let startDate = new Date(aWorker.startDate)
        let x =`${('0' + (startDate.getMonth() + 1)).slice(-2)}/${('0' + startDate.getDate()).slice(-2)}/${startDate.getFullYear()} - ${('0' + startDate.getHours()).slice(-2)}:${('0' + startDate.getMinutes()).slice(-2)}:${('0' + startDate.getSeconds()).slice(-2)} +0000`
        sendWorker.startDate = x
        if (!isEdit) {
            axios.post(process.env.REACT_APP_BACKEND_URL + `/workers`, sendWorker)
                .then(r => {
                    setSuccessAction(true)
                    setAction(true)
                    setTimeout(() => {
                        setAction(false)
                    }, 2000)
                })
                .catch(e => {
                    setSuccessAction(false)
                    setAction(true)
                    setTimeout(() => {
                        setAction(false)
                    }, 2000)
                })
        } else {
            sendWorker = {...sendWorker, id: undefined, creationDate: undefined}
            let location_ = {...sendWorker.person.location, id: undefined}
            let person_ = {...sendWorker.person, location: location_, id: undefined}
            let organization_ = {...sendWorker.organization, id: undefined}
            sendWorker = {...sendWorker, organization: organization_, person: person_}
            axios.put(process.env.REACT_APP_BACKEND_URL + `/workers/${workerID}`, sendWorker)
                .then(r => {
                    setSuccessAction(true)
                    setAction(true)
                    setTimeout(() => {
                        setAction(false)
                    }, 2000)
                })
                .catch(e => {
                    setSuccessAction(false)
                    setAction(true)
                    setTimeout(() => {
                        setAction(false)
                    }, 2000)
                })
        }
    }

    return (
        <div className="worker-form-container">
            {
                isEdit ? <h3>Изменение работника</h3> : <h3>Создание работника</h3>
            }
            <div className="worker-coordinates-input-block">
                <div className="worker-coordinates-input-cell">
                    <label>
                        X
                    </label>
                    <input className="worker-input" value={aWorker.coordinates.x}
                        onChange={handleChangeCoordX}></input>
                </div>
                <div className="worker-coordinates-input-cell">
                    <label>
                        Y
                    </label>
                    <input className="worker-input" value={aWorker.coordinates.y}
                        onChange={handleChangeCoordY}></input>
                </div>
            </div>
            <label>Name</label>
            <input className="worker-input" value={aWorker.name}
                onChange={handleChangeWorkerName}></input>
            <label>Salary</label>
            <input className="worker-input" value={aWorker.salary}
                onChange={handleChangeSalary}></input>
            <label>Position</label>
            <select value={aWorker.position} onChange={handleChangePosition}>
                <option value={"CLEANER"}>CLEANER</option>
                <option value={"DEVELOPER"}>DEVELOPER</option>
                <option value={"HUMAN_RESOURCES"}>HUMAN_RESOURCES</option>
                <option value={"LABORER"}>LABORER</option>
                <option value={"LEAD_DEVELOPER"}>LEAD_DEVELOPER</option>
            </select>
            <label>Start date</label>
            <input className="worker-input" type="datetime-local" value={aWorker.startDate}
                onChange={handleChangeStartDate}></input>
            <label>End date</label>
            <input className="worker-input" type="datetime-local" value={aWorker.endDate}
                onChange={handleChangeEndDate}></input>
            <label>Organization name</label>
            <input className="worker-input" value={aWorker.organization.name}
                onChange={handleChangeOrganizationName}></input>
            <label>Person eye color</label>
            <select value={aWorker.person.eyeColor} onChange={handleChangeEyeColor}>
                <option value={"BLUE"}>BLUE</option>
                <option value={"GREEN"}>GREEN</option>
                <option value={"YELLOW"}>YELLOW</option>
            </select>
            <label>Person passport ID</label>
            <input className="worker-input" value={aWorker.person.passportID}
                onChange={handleChangePersonPassportID}></input>
            <label>Person height</label>
            <input className="worker-input" value={aWorker.person.height}
                onChange={handlePersonHeight}></input>
            <div className="worker-coordinates-input-block">
                <div className="worker-location-coordinates-input-cell">
                    <label>
                        X
                    </label>
                    <input className="worker-input" value={aWorker.person.location.x}
                        onChange={handleChangeLocationCoordX}></input>
                </div>
                <div className="worker-location-coordinates-input-cell">
                    <label>
                        Y
                    </label>
                    <input className="worker-input" value={aWorker.person.location.y}
                        onChange={handleChangeLocationCoordY}></input>
                </div>
                <div className="worker-location-coordinates-input-cell">
                    <label>
                        Z
                    </label>
                    <input className="worker-input" value={aWorker.person.location.z}
                        onChange={handleChangeLocationCoordZ}></input>
                </div>
            </div>
            <button className="button" onClick={actionButton}>
                {isEdit ? "Сохранить изменения" : "Создать работника"}
            </button>
            {
                isAction && (isSuccessAction ?
                    <div className="find-status-ok">
                        {isEdit ? "Успешно изменено" : "Успешно создано"}
                    </div>
                    :
                    <div className="find-status">
                        {isEdit ? "Не удалось изменить" : "Не удалось создать"}
                    </div>)
            }
        </div>
    )
}

export default  WorkerActions