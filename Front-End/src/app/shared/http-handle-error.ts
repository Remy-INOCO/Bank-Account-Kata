import { Observable, of } from 'rxjs';

export class HttpHandleError {
  handleError<T>(operation = 'operation', result?: T): any {
    return (error: any): Observable<T> => {
      console.error(operation + ' failed : ' + JSON.stringify(error));

      return of(result as T);
    };
  }
}
