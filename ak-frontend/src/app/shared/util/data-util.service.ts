import { ElementRef } from '@angular/core';
import { Observable } from 'rxjs';
import { FormGroup } from '@angular/forms';
import * as ɵngcc0 from '@angular/core';
export declare type JhiFileLoadErrorType = 'not.image' | 'could.not.extract';
export interface JhiFileLoadError {
    message: string;
    key: JhiFileLoadErrorType;
    params?: any;
}
/**
 * An utility service for data.
 */
export declare class JhiDataUtils {
    constructor();
    /**
     * Method to abbreviate the text given
     */
    abbreviate(text: string, append?: string): string;
    /**
     * Method to find the byte size of the string provides
     */
    byteSize(base64String: string): string;
    /**
     * Method to open file
     */
    openFile(contentType: string, data: string): void;
    /**
     * Method to convert the file to base64
     */
    toBase64(file: File, cb: Function): void;
    /**
     * Method to clear the input
     */
    clearInputImage(entity: any, elementRef: ElementRef, field: string, fieldContentType: string, idInput: string): void;
    /**
     * Sets the base 64 data & file type of the 1st file on the event (event.target.files[0]) in the passed entity object
     * and returns a promise.
     *
     * @param event the object containing the file (at event.target.files[0])
     * @param entity the object to set the file's 'base 64 data' and 'file type' on
     * @param field the field name to set the file's 'base 64 data' on
     * @param isImage boolean representing if the file represented by the event is an image
     * @returns a promise that resolves to the modified entity if operation is successful, otherwise rejects with an error message
     */
    setFileData(event: any, entity: any, field: string, isImage: boolean): Promise<any>;
    /**
     * Sets the base 64 data & file type of the 1st file on the event (event.target.files[0]) in the passed entity object
     * and returns an observable.
     *
     * @param event the object containing the file (at event.target.files[0])
     * @param editForm the form group where the input field is located
     * @param field the field name to set the file's 'base 64 data' on
     * @param isImage boolean representing if the file represented by the event is an image
     * @returns an observable that loads file to form field and completes if sussessful
     *          or returns error as JhiFileLoadError on failure
     */
    loadFileToForm(event: Event, editForm: FormGroup, field: string, isImage: boolean): Observable<void>;
    /**
     * Method to download file
     */
    downloadFile(contentType: string, data: string, fileName: string): void;
    private endsWith;
    private paddingSize;
    private size;
    private formatAsBytes;
    static ɵfac: ɵngcc0.ɵɵFactoryDef<JhiDataUtils, never>;
}

//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZGF0YS11dGlsLnNlcnZpY2UuZC50cyIsInNvdXJjZXMiOlsiZGF0YS11dGlsLnNlcnZpY2UuZC50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtBQUNBO0FBQ0E7O0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTs7QUFDQSIsInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7IEVsZW1lbnRSZWYgfSBmcm9tICdAYW5ndWxhci9jb3JlJztcbmltcG9ydCB7IE9ic2VydmFibGUgfSBmcm9tICdyeGpzJztcbmltcG9ydCB7IEZvcm1Hcm91cCB9IGZyb20gJ0Bhbmd1bGFyL2Zvcm1zJztcbmV4cG9ydCBkZWNsYXJlIHR5cGUgSmhpRmlsZUxvYWRFcnJvclR5cGUgPSAnbm90LmltYWdlJyB8ICdjb3VsZC5ub3QuZXh0cmFjdCc7XG5leHBvcnQgaW50ZXJmYWNlIEpoaUZpbGVMb2FkRXJyb3Ige1xuICAgIG1lc3NhZ2U6IHN0cmluZztcbiAgICBrZXk6IEpoaUZpbGVMb2FkRXJyb3JUeXBlO1xuICAgIHBhcmFtcz86IGFueTtcbn1cbi8qKlxuICogQW4gdXRpbGl0eSBzZXJ2aWNlIGZvciBkYXRhLlxuICovXG5leHBvcnQgZGVjbGFyZSBjbGFzcyBKaGlEYXRhVXRpbHMge1xuICAgIGNvbnN0cnVjdG9yKCk7XG4gICAgLyoqXG4gICAgICogTWV0aG9kIHRvIGFiYnJldmlhdGUgdGhlIHRleHQgZ2l2ZW5cbiAgICAgKi9cbiAgICBhYmJyZXZpYXRlKHRleHQ6IHN0cmluZywgYXBwZW5kPzogc3RyaW5nKTogc3RyaW5nO1xuICAgIC8qKlxuICAgICAqIE1ldGhvZCB0byBmaW5kIHRoZSBieXRlIHNpemUgb2YgdGhlIHN0cmluZyBwcm92aWRlc1xuICAgICAqL1xuICAgIGJ5dGVTaXplKGJhc2U2NFN0cmluZzogc3RyaW5nKTogc3RyaW5nO1xuICAgIC8qKlxuICAgICAqIE1ldGhvZCB0byBvcGVuIGZpbGVcbiAgICAgKi9cbiAgICBvcGVuRmlsZShjb250ZW50VHlwZTogc3RyaW5nLCBkYXRhOiBzdHJpbmcpOiB2b2lkO1xuICAgIC8qKlxuICAgICAqIE1ldGhvZCB0byBjb252ZXJ0IHRoZSBmaWxlIHRvIGJhc2U2NFxuICAgICAqL1xuICAgIHRvQmFzZTY0KGZpbGU6IEZpbGUsIGNiOiBGdW5jdGlvbik6IHZvaWQ7XG4gICAgLyoqXG4gICAgICogTWV0aG9kIHRvIGNsZWFyIHRoZSBpbnB1dFxuICAgICAqL1xuICAgIGNsZWFySW5wdXRJbWFnZShlbnRpdHk6IGFueSwgZWxlbWVudFJlZjogRWxlbWVudFJlZiwgZmllbGQ6IHN0cmluZywgZmllbGRDb250ZW50VHlwZTogc3RyaW5nLCBpZElucHV0OiBzdHJpbmcpOiB2b2lkO1xuICAgIC8qKlxuICAgICAqIFNldHMgdGhlIGJhc2UgNjQgZGF0YSAmIGZpbGUgdHlwZSBvZiB0aGUgMXN0IGZpbGUgb24gdGhlIGV2ZW50IChldmVudC50YXJnZXQuZmlsZXNbMF0pIGluIHRoZSBwYXNzZWQgZW50aXR5IG9iamVjdFxuICAgICAqIGFuZCByZXR1cm5zIGEgcHJvbWlzZS5cbiAgICAgKlxuICAgICAqIEBwYXJhbSBldmVudCB0aGUgb2JqZWN0IGNvbnRhaW5pbmcgdGhlIGZpbGUgKGF0IGV2ZW50LnRhcmdldC5maWxlc1swXSlcbiAgICAgKiBAcGFyYW0gZW50aXR5IHRoZSBvYmplY3QgdG8gc2V0IHRoZSBmaWxlJ3MgJ2Jhc2UgNjQgZGF0YScgYW5kICdmaWxlIHR5cGUnIG9uXG4gICAgICogQHBhcmFtIGZpZWxkIHRoZSBmaWVsZCBuYW1lIHRvIHNldCB0aGUgZmlsZSdzICdiYXNlIDY0IGRhdGEnIG9uXG4gICAgICogQHBhcmFtIGlzSW1hZ2UgYm9vbGVhbiByZXByZXNlbnRpbmcgaWYgdGhlIGZpbGUgcmVwcmVzZW50ZWQgYnkgdGhlIGV2ZW50IGlzIGFuIGltYWdlXG4gICAgICogQHJldHVybnMgYSBwcm9taXNlIHRoYXQgcmVzb2x2ZXMgdG8gdGhlIG1vZGlmaWVkIGVudGl0eSBpZiBvcGVyYXRpb24gaXMgc3VjY2Vzc2Z1bCwgb3RoZXJ3aXNlIHJlamVjdHMgd2l0aCBhbiBlcnJvciBtZXNzYWdlXG4gICAgICovXG4gICAgc2V0RmlsZURhdGEoZXZlbnQ6IGFueSwgZW50aXR5OiBhbnksIGZpZWxkOiBzdHJpbmcsIGlzSW1hZ2U6IGJvb2xlYW4pOiBQcm9taXNlPGFueT47XG4gICAgLyoqXG4gICAgICogU2V0cyB0aGUgYmFzZSA2NCBkYXRhICYgZmlsZSB0eXBlIG9mIHRoZSAxc3QgZmlsZSBvbiB0aGUgZXZlbnQgKGV2ZW50LnRhcmdldC5maWxlc1swXSkgaW4gdGhlIHBhc3NlZCBlbnRpdHkgb2JqZWN0XG4gICAgICogYW5kIHJldHVybnMgYW4gb2JzZXJ2YWJsZS5cbiAgICAgKlxuICAgICAqIEBwYXJhbSBldmVudCB0aGUgb2JqZWN0IGNvbnRhaW5pbmcgdGhlIGZpbGUgKGF0IGV2ZW50LnRhcmdldC5maWxlc1swXSlcbiAgICAgKiBAcGFyYW0gZWRpdEZvcm0gdGhlIGZvcm0gZ3JvdXAgd2hlcmUgdGhlIGlucHV0IGZpZWxkIGlzIGxvY2F0ZWRcbiAgICAgKiBAcGFyYW0gZmllbGQgdGhlIGZpZWxkIG5hbWUgdG8gc2V0IHRoZSBmaWxlJ3MgJ2Jhc2UgNjQgZGF0YScgb25cbiAgICAgKiBAcGFyYW0gaXNJbWFnZSBib29sZWFuIHJlcHJlc2VudGluZyBpZiB0aGUgZmlsZSByZXByZXNlbnRlZCBieSB0aGUgZXZlbnQgaXMgYW4gaW1hZ2VcbiAgICAgKiBAcmV0dXJucyBhbiBvYnNlcnZhYmxlIHRoYXQgbG9hZHMgZmlsZSB0byBmb3JtIGZpZWxkIGFuZCBjb21wbGV0ZXMgaWYgc3Vzc2Vzc2Z1bFxuICAgICAqICAgICAgICAgIG9yIHJldHVybnMgZXJyb3IgYXMgSmhpRmlsZUxvYWRFcnJvciBvbiBmYWlsdXJlXG4gICAgICovXG4gICAgbG9hZEZpbGVUb0Zvcm0oZXZlbnQ6IEV2ZW50LCBlZGl0Rm9ybTogRm9ybUdyb3VwLCBmaWVsZDogc3RyaW5nLCBpc0ltYWdlOiBib29sZWFuKTogT2JzZXJ2YWJsZTx2b2lkPjtcbiAgICAvKipcbiAgICAgKiBNZXRob2QgdG8gZG93bmxvYWQgZmlsZVxuICAgICAqL1xuICAgIGRvd25sb2FkRmlsZShjb250ZW50VHlwZTogc3RyaW5nLCBkYXRhOiBzdHJpbmcsIGZpbGVOYW1lOiBzdHJpbmcpOiB2b2lkO1xuICAgIHByaXZhdGUgZW5kc1dpdGg7XG4gICAgcHJpdmF0ZSBwYWRkaW5nU2l6ZTtcbiAgICBwcml2YXRlIHNpemU7XG4gICAgcHJpdmF0ZSBmb3JtYXRBc0J5dGVzO1xufVxuIl19