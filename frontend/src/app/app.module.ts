import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { UiKitModule } from './ui-kit/ui-kit.module';

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, CoreModule, UiKitModule, AppRoutingModule],
  bootstrap: [AppComponent],
})
export class AppModule {}
