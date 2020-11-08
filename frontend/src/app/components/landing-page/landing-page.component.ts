import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../auth/auth.service';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss']
})
export class LandingPageComponent implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  async signIn(): Promise<void>{
      await this.authService.signIn();
  }

}
