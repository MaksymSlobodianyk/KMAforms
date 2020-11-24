import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../auth/auth.service';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss']
})
export class LandingPageComponent implements OnInit {

  public startLoading = false;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  async signIn(): Promise<void>{
    this.startLoading = true;
    await this.authService.signIn();
  }

}
