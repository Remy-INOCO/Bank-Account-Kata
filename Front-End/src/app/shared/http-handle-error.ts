import { Observable, of, throwError } from 'rxjs';
import { IErrorMessage } from '../models/error-message';

export class HttpHandleError {
  handleError<T>(operation = 'operation', result?: T): any {
    return (error: any): Observable<T> => {

      switch (error.error.statusCode) {
        case 400:
        case 401:
        case 404:
          console.error(operation + ' failed : ' + JSON.stringify(error.error));
          return throwError(error.error as T);

        default:
          console.error(operation + ' failed : ' + JSON.stringify(error));
          return throwError(error);
      }
    };
  }
}
