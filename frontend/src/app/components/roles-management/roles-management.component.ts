import {Component, OnInit} from '@angular/core';
import {FormApiService} from '../../api-services/form-api.service';
import {Toaster} from 'ngx-toast-notifications';

class UserToShow {
  displayName: string;
  email: string;
  isAdmin: boolean;

  constructor(displayName: string, email: string, isAdmin: boolean) {
    this.displayName = displayName;
    this.email = email;
    this.isAdmin = isAdmin;
  }
}

@Component({
  selector: 'app-roles-management',
  templateUrl: './roles-management.component.html',
  styleUrls: ['./roles-management.component.scss']
})
export class RolesManagementComponent implements OnInit {

  public users: Array<UserToShow>;

  constructor(
    private formApiService: FormApiService,
    private toaster: Toaster
  ) {
  }

  ngOnInit(): void {
    this.formApiService.getAllUsers().subscribe(data => {
      this.users = data.map(user => new UserToShow(user.displayName, user.email, user.role === 'admin'));
    }, error => {
      this.toaster.open({
        caption: '😢   Упс... Нам не вдалося завантажити список користувачів',
        duration: 4000,
        type: 'warning'
      });
    });
  }

  toggleRole(email): void {
    const currentUser = this.users.find(user => user.email === email);
    this.formApiService.changeRole(!currentUser.isAdmin, email).subscribe(data => {
      currentUser.isAdmin = !currentUser.isAdmin;
      this.toaster.open({
        caption: '🦄   Роль користувача успішно змінена',
        duration: 4000,
        type: 'success'
      });
    }, error => {
      this.toaster.open({
        caption: '😢   Упс... Нам не вдалося змінити роль користувача',
        duration: 4000,
        type: 'warning'
      });
    });
  }

}
