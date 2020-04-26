/**
 * An utility service for pagination
 */
import * as ɵngcc0 from '@angular/core';
export declare class JhiPaginationUtil {
    constructor();
    /**
     * Method to find whether the sort is defined
     */
    parseAscending(sort: string): boolean;
    /**
     * Method to query params are strings, and need to be parsed
     */
    parsePage(page: string): number;
    /**
     * Method to sort can be in the format `id,asc` or `id`
     */
    parsePredicate(sort: string): string;
    static ɵfac: ɵngcc0.ɵɵFactoryDef<JhiPaginationUtil, never>;
}

//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoicGFnaW5hdGlvbi11dGlsLnNlcnZpY2UuZC50cyIsInNvdXJjZXMiOlsicGFnaW5hdGlvbi11dGlsLnNlcnZpY2UuZC50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtBQUNBO0FBQ0E7O0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTs7QUFDQSIsInNvdXJjZXNDb250ZW50IjpbIi8qKlxuICogQW4gdXRpbGl0eSBzZXJ2aWNlIGZvciBwYWdpbmF0aW9uXG4gKi9cbmV4cG9ydCBkZWNsYXJlIGNsYXNzIEpoaVBhZ2luYXRpb25VdGlsIHtcbiAgICBjb25zdHJ1Y3RvcigpO1xuICAgIC8qKlxuICAgICAqIE1ldGhvZCB0byBmaW5kIHdoZXRoZXIgdGhlIHNvcnQgaXMgZGVmaW5lZFxuICAgICAqL1xuICAgIHBhcnNlQXNjZW5kaW5nKHNvcnQ6IHN0cmluZyk6IGJvb2xlYW47XG4gICAgLyoqXG4gICAgICogTWV0aG9kIHRvIHF1ZXJ5IHBhcmFtcyBhcmUgc3RyaW5ncywgYW5kIG5lZWQgdG8gYmUgcGFyc2VkXG4gICAgICovXG4gICAgcGFyc2VQYWdlKHBhZ2U6IHN0cmluZyk6IG51bWJlcjtcbiAgICAvKipcbiAgICAgKiBNZXRob2QgdG8gc29ydCBjYW4gYmUgaW4gdGhlIGZvcm1hdCBgaWQsYXNjYCBvciBgaWRgXG4gICAgICovXG4gICAgcGFyc2VQcmVkaWNhdGUoc29ydDogc3RyaW5nKTogc3RyaW5nO1xufVxuIl19