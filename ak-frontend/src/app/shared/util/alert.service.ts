import { DomSanitizer } from '@angular/platform-browser';
import { JhiConfigService } from '../config.service';
import * as ɵngcc0 from '@angular/core';
export declare type JhiAlertType = 'success' | 'danger' | 'warning' | 'info';
export interface JhiAlert {
    id?: number;
    type: JhiAlertType;
    msg: string;
    params?: any;
    timeout?: number;
    toast?: boolean;
    position?: string;
    scoped?: boolean;
    close?: (alerts: JhiAlert[]) => void;
}
export declare class JhiAlertService {
    private sanitizer;
    private configService;
    //private translateService;
    private alertId;
    private alerts;
    private timeout;
    private toast;
    private i18nEnabled;
    constructor(sanitizer: DomSanitizer, configService: JhiConfigService);
    clear(): void;
    get(): JhiAlert[];
    success(msg: string, params?: any, position?: string): JhiAlert;
    error(msg: string, params?: any, position?: string): JhiAlert;
    warning(msg: string, params?: any, position?: string): JhiAlert;
    info(msg: string, params?: any, position?: string): JhiAlert;
    addAlert(alertOptions: JhiAlert, extAlerts: JhiAlert[]): JhiAlert;
    closeAlert(id: number, extAlerts?: JhiAlert[]): JhiAlert[];
    closeAlertByIndex(index: number, thisAlerts: JhiAlert[]): JhiAlert[];
    isToast(): boolean;
    private factory;
    static ɵfac: ɵngcc0.ɵɵFactoryDef<JhiAlertService, [null, null, { optional: true; }]>;
}

//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYWxlcnQuc2VydmljZS5kLnRzIiwic291cmNlcyI6WyJhbGVydC5zZXJ2aWNlLmQudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7QUFDQTtBQUNBOztBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTs7QUFDQSIsInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7IERvbVNhbml0aXplciB9IGZyb20gJ0Bhbmd1bGFyL3BsYXRmb3JtLWJyb3dzZXInO1xuaW1wb3J0IHsgVHJhbnNsYXRlU2VydmljZSB9IGZyb20gJ0BuZ3gtdHJhbnNsYXRlL2NvcmUnO1xuaW1wb3J0IHsgSmhpQ29uZmlnU2VydmljZSB9IGZyb20gJy4uL2NvbmZpZy5zZXJ2aWNlJztcbmV4cG9ydCBkZWNsYXJlIHR5cGUgSmhpQWxlcnRUeXBlID0gJ3N1Y2Nlc3MnIHwgJ2RhbmdlcicgfCAnd2FybmluZycgfCAnaW5mbyc7XG5leHBvcnQgaW50ZXJmYWNlIEpoaUFsZXJ0IHtcbiAgICBpZD86IG51bWJlcjtcbiAgICB0eXBlOiBKaGlBbGVydFR5cGU7XG4gICAgbXNnOiBzdHJpbmc7XG4gICAgcGFyYW1zPzogYW55O1xuICAgIHRpbWVvdXQ/OiBudW1iZXI7XG4gICAgdG9hc3Q/OiBib29sZWFuO1xuICAgIHBvc2l0aW9uPzogc3RyaW5nO1xuICAgIHNjb3BlZD86IGJvb2xlYW47XG4gICAgY2xvc2U/OiAoYWxlcnRzOiBKaGlBbGVydFtdKSA9PiB2b2lkO1xufVxuZXhwb3J0IGRlY2xhcmUgY2xhc3MgSmhpQWxlcnRTZXJ2aWNlIHtcbiAgICBwcml2YXRlIHNhbml0aXplcjtcbiAgICBwcml2YXRlIGNvbmZpZ1NlcnZpY2U7XG4gICAgcHJpdmF0ZSB0cmFuc2xhdGVTZXJ2aWNlO1xuICAgIHByaXZhdGUgYWxlcnRJZDtcbiAgICBwcml2YXRlIGFsZXJ0cztcbiAgICBwcml2YXRlIHRpbWVvdXQ7XG4gICAgcHJpdmF0ZSB0b2FzdDtcbiAgICBwcml2YXRlIGkxOG5FbmFibGVkO1xuICAgIGNvbnN0cnVjdG9yKHNhbml0aXplcjogRG9tU2FuaXRpemVyLCBjb25maWdTZXJ2aWNlOiBKaGlDb25maWdTZXJ2aWNlLCB0cmFuc2xhdGVTZXJ2aWNlOiBUcmFuc2xhdGVTZXJ2aWNlKTtcbiAgICBjbGVhcigpOiB2b2lkO1xuICAgIGdldCgpOiBKaGlBbGVydFtdO1xuICAgIHN1Y2Nlc3MobXNnOiBzdHJpbmcsIHBhcmFtcz86IGFueSwgcG9zaXRpb24/OiBzdHJpbmcpOiBKaGlBbGVydDtcbiAgICBlcnJvcihtc2c6IHN0cmluZywgcGFyYW1zPzogYW55LCBwb3NpdGlvbj86IHN0cmluZyk6IEpoaUFsZXJ0O1xuICAgIHdhcm5pbmcobXNnOiBzdHJpbmcsIHBhcmFtcz86IGFueSwgcG9zaXRpb24/OiBzdHJpbmcpOiBKaGlBbGVydDtcbiAgICBpbmZvKG1zZzogc3RyaW5nLCBwYXJhbXM/OiBhbnksIHBvc2l0aW9uPzogc3RyaW5nKTogSmhpQWxlcnQ7XG4gICAgYWRkQWxlcnQoYWxlcnRPcHRpb25zOiBKaGlBbGVydCwgZXh0QWxlcnRzOiBKaGlBbGVydFtdKTogSmhpQWxlcnQ7XG4gICAgY2xvc2VBbGVydChpZDogbnVtYmVyLCBleHRBbGVydHM/OiBKaGlBbGVydFtdKTogSmhpQWxlcnRbXTtcbiAgICBjbG9zZUFsZXJ0QnlJbmRleChpbmRleDogbnVtYmVyLCB0aGlzQWxlcnRzOiBKaGlBbGVydFtdKTogSmhpQWxlcnRbXTtcbiAgICBpc1RvYXN0KCk6IGJvb2xlYW47XG4gICAgcHJpdmF0ZSBmYWN0b3J5O1xufVxuIl19