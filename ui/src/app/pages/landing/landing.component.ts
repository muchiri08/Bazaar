import { Component } from '@angular/core';
import { NzTypographyModule } from 'ng-zorro-antd/typography';
import { HeaderComponent } from "../../layouts/header/header.component";

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [NzTypographyModule, HeaderComponent],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent {

}
