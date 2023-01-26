import {ICoordinates} from "./ICoordinates";
import {IPersonWithId} from "./IPersonWithId";
import {IOrganizationWithId} from "./IOrganizationWithId";

export interface IWorkerWithId {
    coordinates: ICoordinates,
    endDate: string,
    creationDate: string,
    organization: IOrganizationWithId,
    id: number,
    name: string,
    salary: number,
    person: IPersonWithId,
    position: string,
    startDate: string
}