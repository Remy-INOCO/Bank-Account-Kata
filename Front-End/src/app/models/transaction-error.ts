export interface ITransactionError {
    transaction: string,
    amountAboveBalance: boolean,
    server: string
}