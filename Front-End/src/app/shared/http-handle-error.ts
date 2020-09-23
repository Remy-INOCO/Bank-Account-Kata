import { Observable, of } from 'rxjs';

export class HttpHandleError {
    handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
          console.error(operation + " failed : " + JSON.stringify(error)); // log to console instead
    
          return of(result as T);
        }
    }
}