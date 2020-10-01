import { Observable, of, throwError } from 'rxjs';

export class HttpHandleError {
  handleError<T>(operation = 'operation'): any {
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
