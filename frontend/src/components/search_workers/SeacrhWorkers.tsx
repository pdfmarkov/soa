import {useState} from "react";
import "./SearchWorkers.css"
import {IWorkerWithId} from "../../models/IWorkerWithId";
import axios from "axios";
import {tmpWorker} from "../../pages/main_page/MainPage";

interface SearchWorkersProps {
    submitError: (text: string) => void
}

function SeacrhWorkers({submitError}: SearchWorkersProps) {

    const [selectedSortField, setSortField] = useState<string>()

    const [numberOfWorkers, setNumberOfWorkers] = useState(0)

    const [name, setName] = useState<string>()
    const [position, setPosition] = useState<string>()
    const [eyeColor, setEyeColor] = useState<string>()
    const [organizationName, setOrganizationName] = useState<string>()

    const [workers, setWorkers] = useState<IWorkerWithId[]>()
    const [tableVisible, setTableVisible] = useState(false)

    const [page, setPage] = useState(0)

    const handleChangeSortField = (event: React.FormEvent) => {
        setSortField((event.target as HTMLInputElement).value)
    }

    const handleChangeOrganizationName = (event: React.FormEvent) => {
        setOrganizationName((event.target as HTMLInputElement).value)
    }

    const handleChangeNumberOfWorkers = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setNumberOfWorkers(possibleNumber)
    }

    const handleChangeName = (event: React.FormEvent) => {
        setName((event.target as HTMLInputElement).value)
    }

    const handleChangePosition = (event: React.FormEvent) => {
        setPosition((event.target as HTMLInputElement).value)
    }

    const handleChangeEyeColor = (event: React.FormEvent) => {
        setEyeColor((event.target as HTMLInputElement).value)
    }

    const search = () => {
        let filterString = ""

        if (name != "" && name != undefined) {
            filterString += "name=" + name + ";"
        }

        if (organizationName != undefined) {
            filterString += "organization.name=" + organizationName + ";"
        }

        if (position != "" && position != undefined) {
            filterString += "position=" + position + ";"
        }

        if (eyeColor != "" && eyeColor != undefined) {
            filterString += "eyeColor=" + eyeColor + ";"
        }

        let request = "/workers?"

        if (selectedSortField != "" && selectedSortField != undefined) {
            request += "sort=" + selectedSortField + "&"
        }

        if (filterString != "") {
            request += "filter=" + filterString.slice(0,-1) + "&"
        }

        request += "size=" + numberOfWorkers + `&page=${page}`

        axios.get<IWorkerWithId[]>(process.env.REACT_APP_BACKEND_URL + request)
            .then(r => {
                if (r.data.length == 0) {
                    submitError("Ошибка! Не удалось найти рабочих")
                } else {
                    setWorkers(r.data)
                    setTableVisible(true)
                }
            })
            .catch(e => {
                submitError("Ошибка! Не удалось найти рабочих")
            })


    }

    const handleChangePage = (event: React.FormEvent) => {
        let possibleNumber = parseInt((event.target as HTMLInputElement).value)

        if (isNaN(possibleNumber)) {
            possibleNumber = 0
        }

        setPage(possibleNumber)
    }

    return (
        <>
            <h4>Поиск рабочих</h4>
            <div className="search-container">
                <div className="search-cell">
                    <label>
                        Сортировать по...
                    </label>
                    <select value={selectedSortField} onChange={handleChangeSortField}>
                        <option value ={""}></option>
                        <option value={"id"}>id</option>
                        <option value={"coordinates.x"}>coordinates.x</option>
                        <option value={"salary"}>salary</option>
                        <option value={"person.height"}>person.height</option>
                        <option value={"creationDate"}>creationDate</option>
                        <option value={"person.eyeColor"}>person.eyeColor</option>
                        <option value={"person.passportID"}>person.passportID</option>
                        <option value={"-id"}>-id</option>
                        <option value={"-coordinates.x"}>-coordinates.x</option>
                        <option value={"-salary"}>-salary</option>
                        <option value={"-person.height"}>-person.height</option>
                        <option value={"-creationDate"}>-creationDate</option>
                        <option value={"-person.eyeColor"}>-person.eyeColor</option>
                        <option value={"-person.passportID"}>-person.passportID</option>
                    </select>
                </div>
                <div className="search-cell">
                    <label>
                        Количество рабочих
                    </label>
                    <input className="worker-input" value={numberOfWorkers}
                        onChange={handleChangeNumberOfWorkers}/>
                </div>
            </div>
            <div className="search-container">
                <div className="search-cell">
                    <label>
                        Name = ?
                    </label>
                    <input className="worker-input" value={name}
                           onChange={handleChangeName}/>
                </div>
                <div className="search-cell">
                    <label>
                        Organization name = ?
                    </label>
                    <input className="worker-input" value={organizationName}
                           onChange={handleChangeOrganizationName}/>
                </div>
            </div>
            <div className="search-container">
                <div className="search-cell">
                    <label>
                        Position = ?
                    </label>
                    <select value={position} onChange={handleChangePosition}>
                        <option value={""}></option>
                        <option value={"CLEANER"}>CLEANER</option>
                        <option value={"DEVELOPER"}>DEVELOPER</option>
                        <option value={"HUMAN_RESOURCES"}>HUMAN_RESOURCES</option>
                        <option value={"LABORER"}>LABORER</option>
                        <option value={"LEAD_DEVELOPER"}>LEAD_DEVELOPER</option>
                    </select>
                </div>
                <div className="search-cell">
                    <label>
                        Eye color = ?
                    </label>
                    <select value={eyeColor} onChange={handleChangeEyeColor}>
                        <option value={""}></option>
                        <option value={"BLUE"}>BLUE</option>
                        <option value={"GREEN"}>GREEN</option>
                        <option value={"YELLOW"}>YELLOW</option>
                    </select>
                </div>
            </div>
            <div className="search-cell search-cell-page">
                <label>
                    Page = ?
                </label>
                <input className="worker-input" value={page}
                       onChange={handleChangePage}/>
            </div>
            <button className="button" onClick={search}>
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
        </>
    )
}

export default SeacrhWorkers