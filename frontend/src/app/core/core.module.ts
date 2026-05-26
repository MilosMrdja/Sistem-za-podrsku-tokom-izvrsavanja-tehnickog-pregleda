import { NgModule, Optional, SkipSelf } from '@angular/core';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { apiErrorInterceptor } from './interceptors/api-error.interceptor';

@NgModule({
  providers: [provideHttpClient(withInterceptors([apiErrorInterceptor]))],
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parent: CoreModule) {
    if (parent) {
      throw new Error('CoreModule je već učitan.');
    }
  }
}
