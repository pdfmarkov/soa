import {ICoordinates} from "./ICoordinates";
import {IOrganization} from "./IOrganization";
import {IPerson} from "./IPerson";

export interface IWorker {
    coordinates: ICoordinates,
    endDate: string,
    id?: number,
    creationDate?: string,
    organization: IOrganization,
    name: string,
    salary: number,
    person: IPerson,
    position?: string,
    startDate: string
}