import { FormGroup } from '@angular/forms';

export function handleFormError(form: FormGroup, field: string): boolean {
    return form.controls[field].invalid && (form.controls[field].dirty || form.controls[field].touched);
}
