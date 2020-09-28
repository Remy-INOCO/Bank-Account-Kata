import { Operation } from './operation';

export interface ITransaction {
    id?: number;
    idUser?: number;
    operation: Operation;
    wording: string;
    date: Date;
    amount: number;
    balance?: number;
}

export interface TransactionResolved {
    transactions: ITransaction[];
    errorMessage?: string;
}
