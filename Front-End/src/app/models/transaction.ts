import { OPERATION } from './operation';

export interface Transaction {
    id: number
    idUser: number
    operation: OPERATION
}