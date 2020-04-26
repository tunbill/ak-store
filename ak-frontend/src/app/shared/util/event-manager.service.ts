import { Observable, Observer, Subscription } from 'rxjs';
import { JhiEventWithContent } from './event-with-content.model';
/**
 * An utility class to manage RX events
 */
import * as ɵngcc0 from '@angular/core';
export declare class JhiEventManager {
    observable: Observable<JhiEventWithContent<any> | string>;
    observer: Observer<JhiEventWithContent<any> | string>;
    constructor();
    /**
     * Method to broadcast the event to observer
     */
    broadcast(event: JhiEventWithContent<any> | string): void;
    /**
     * Method to subscribe to an event with callback
     */
    subscribe(eventName: string, callback: any): Subscription;
    /**
     * Method to unsubscribe the subscription
     */
    destroy(subscriber: Subscription): void;
    static ɵfac: ɵngcc0.ɵɵFactoryDef<JhiEventManager, never>;
}

//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZXZlbnQtbWFuYWdlci5zZXJ2aWNlLmQudHMiLCJzb3VyY2VzIjpbImV2ZW50LW1hbmFnZXIuc2VydmljZS5kLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7O0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7O0FBQ0EiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBPYnNlcnZhYmxlLCBPYnNlcnZlciwgU3Vic2NyaXB0aW9uIH0gZnJvbSAncnhqcyc7XG5pbXBvcnQgeyBKaGlFdmVudFdpdGhDb250ZW50IH0gZnJvbSAnLi9ldmVudC13aXRoLWNvbnRlbnQubW9kZWwnO1xuLyoqXG4gKiBBbiB1dGlsaXR5IGNsYXNzIHRvIG1hbmFnZSBSWCBldmVudHNcbiAqL1xuZXhwb3J0IGRlY2xhcmUgY2xhc3MgSmhpRXZlbnRNYW5hZ2VyIHtcbiAgICBvYnNlcnZhYmxlOiBPYnNlcnZhYmxlPEpoaUV2ZW50V2l0aENvbnRlbnQ8YW55PiB8IHN0cmluZz47XG4gICAgb2JzZXJ2ZXI6IE9ic2VydmVyPEpoaUV2ZW50V2l0aENvbnRlbnQ8YW55PiB8IHN0cmluZz47XG4gICAgY29uc3RydWN0b3IoKTtcbiAgICAvKipcbiAgICAgKiBNZXRob2QgdG8gYnJvYWRjYXN0IHRoZSBldmVudCB0byBvYnNlcnZlclxuICAgICAqL1xuICAgIGJyb2FkY2FzdChldmVudDogSmhpRXZlbnRXaXRoQ29udGVudDxhbnk+IHwgc3RyaW5nKTogdm9pZDtcbiAgICAvKipcbiAgICAgKiBNZXRob2QgdG8gc3Vic2NyaWJlIHRvIGFuIGV2ZW50IHdpdGggY2FsbGJhY2tcbiAgICAgKi9cbiAgICBzdWJzY3JpYmUoZXZlbnROYW1lOiBzdHJpbmcsIGNhbGxiYWNrOiBhbnkpOiBTdWJzY3JpcHRpb247XG4gICAgLyoqXG4gICAgICogTWV0aG9kIHRvIHVuc3Vic2NyaWJlIHRoZSBzdWJzY3JpcHRpb25cbiAgICAgKi9cbiAgICBkZXN0cm95KHN1YnNjcmliZXI6IFN1YnNjcmlwdGlvbik6IHZvaWQ7XG59XG4iXX0=