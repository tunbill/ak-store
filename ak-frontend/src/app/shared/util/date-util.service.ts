/**
 * An utility service for date.
 */

import * as ɵngcc0 from '@angular/core';
export declare class JhiDateUtils {
    private pattern;
    private datePipe;
    constructor();
    /**
     * Method to convert the date time from server into JS date object
     */
    convertDateTimeFromServer(date: any): Date;
    /**
     * Method to convert the date from server into JS date object
     */
    convertLocalDateFromServer(date: any): Date;
    /**
     * Method to convert the JS date object into specified date pattern
     */
    convertLocalDateToServer(date: any, pattern?: string): string;
    /**
     * Method to get the default date pattern
     */
    dateformat(): string;
    toDate(date: any): Date;
    static ɵfac: ɵngcc0.ɵɵFactoryDef<JhiDateUtils, never>;
}

//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZGF0ZS11dGlsLnNlcnZpY2UuZC50cyIsInNvdXJjZXMiOlsiZGF0ZS11dGlsLnNlcnZpY2UuZC50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtBQUNBO0FBQ0E7O0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBOztBQUNBIiwic291cmNlc0NvbnRlbnQiOlsiLyoqXG4gKiBBbiB1dGlsaXR5IHNlcnZpY2UgZm9yIGRhdGUuXG4gKi9cbmV4cG9ydCBkZWNsYXJlIGNsYXNzIEpoaURhdGVVdGlscyB7XG4gICAgcHJpdmF0ZSBwYXR0ZXJuO1xuICAgIHByaXZhdGUgZGF0ZVBpcGU7XG4gICAgY29uc3RydWN0b3IoKTtcbiAgICAvKipcbiAgICAgKiBNZXRob2QgdG8gY29udmVydCB0aGUgZGF0ZSB0aW1lIGZyb20gc2VydmVyIGludG8gSlMgZGF0ZSBvYmplY3RcbiAgICAgKi9cbiAgICBjb252ZXJ0RGF0ZVRpbWVGcm9tU2VydmVyKGRhdGU6IGFueSk6IERhdGU7XG4gICAgLyoqXG4gICAgICogTWV0aG9kIHRvIGNvbnZlcnQgdGhlIGRhdGUgZnJvbSBzZXJ2ZXIgaW50byBKUyBkYXRlIG9iamVjdFxuICAgICAqL1xuICAgIGNvbnZlcnRMb2NhbERhdGVGcm9tU2VydmVyKGRhdGU6IGFueSk6IERhdGU7XG4gICAgLyoqXG4gICAgICogTWV0aG9kIHRvIGNvbnZlcnQgdGhlIEpTIGRhdGUgb2JqZWN0IGludG8gc3BlY2lmaWVkIGRhdGUgcGF0dGVyblxuICAgICAqL1xuICAgIGNvbnZlcnRMb2NhbERhdGVUb1NlcnZlcihkYXRlOiBhbnksIHBhdHRlcm4/OiBzdHJpbmcpOiBzdHJpbmc7XG4gICAgLyoqXG4gICAgICogTWV0aG9kIHRvIGdldCB0aGUgZGVmYXVsdCBkYXRlIHBhdHRlcm5cbiAgICAgKi9cbiAgICBkYXRlZm9ybWF0KCk6IHN0cmluZztcbiAgICB0b0RhdGUoZGF0ZTogYW55KTogRGF0ZTtcbn1cbiJdfQ==