import { Component } from '@angular/core';
import { AlignLeftOutline } from '@ant-design/icons-angular/icons';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';
import { NzIconModule, provideNzIconsPatch } from 'ng-zorro-antd/icon';
import { NzTypographyModule } from 'ng-zorro-antd/typography';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NzButtonModule, NzTypographyModule, NzDrawerModule, NzIconModule],
  providers: [provideNzIconsPatch([AlignLeftOutline])],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  visible: boolean = false;

  open() {
    this.visible = true;
  }

  close() {
    this.visible = false;
  }
}
