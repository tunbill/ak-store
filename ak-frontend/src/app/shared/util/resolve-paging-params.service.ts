import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { JhiPaginationUtil } from './pagination-util.service';
import * as ɵngcc0 from '@angular/core';
export declare class JhiResolvePagingParams implements Resolve<any> {
    private paginationUtil;
    constructor(paginationUtil: JhiPaginationUtil);
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): {
        page: number;
        predicate: string;
        ascending: boolean;
    };
    static ɵfac: ɵngcc0.ɵɵFactoryDef<JhiResolvePagingParams, never>;
}

//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoicmVzb2x2ZS1wYWdpbmctcGFyYW1zLnNlcnZpY2UuZC50cyIsInNvdXJjZXMiOlsicmVzb2x2ZS1wYWdpbmctcGFyYW1zLnNlcnZpY2UuZC50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtBQUNBOztBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7O0FBQ0EiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBBY3RpdmF0ZWRSb3V0ZVNuYXBzaG90LCBSZXNvbHZlLCBSb3V0ZXJTdGF0ZVNuYXBzaG90IH0gZnJvbSAnQGFuZ3VsYXIvcm91dGVyJztcbmltcG9ydCB7IEpoaVBhZ2luYXRpb25VdGlsIH0gZnJvbSAnLi9wYWdpbmF0aW9uLXV0aWwuc2VydmljZSc7XG5leHBvcnQgZGVjbGFyZSBjbGFzcyBKaGlSZXNvbHZlUGFnaW5nUGFyYW1zIGltcGxlbWVudHMgUmVzb2x2ZTxhbnk+IHtcbiAgICBwcml2YXRlIHBhZ2luYXRpb25VdGlsO1xuICAgIGNvbnN0cnVjdG9yKHBhZ2luYXRpb25VdGlsOiBKaGlQYWdpbmF0aW9uVXRpbCk7XG4gICAgcmVzb2x2ZShyb3V0ZTogQWN0aXZhdGVkUm91dGVTbmFwc2hvdCwgc3RhdGU6IFJvdXRlclN0YXRlU25hcHNob3QpOiB7XG4gICAgICAgIHBhZ2U6IG51bWJlcjtcbiAgICAgICAgcHJlZGljYXRlOiBzdHJpbmc7XG4gICAgICAgIGFzY2VuZGluZzogYm9vbGVhbjtcbiAgICB9O1xufVxuIl19