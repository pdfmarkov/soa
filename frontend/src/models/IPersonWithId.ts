import {ILocationWithId} from "./ILocationWithId";

export interface IPersonWithId {
    id: number
    eyeColor: string,
    height: number,
    location: ILocationWithId
    passportID: string
}