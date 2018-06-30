import {Component, Inject, OnInit} from '@angular/core';
import {DOCUMENT} from '@angular/common';
import {Breadcrumb} from '../../core/model/breadcrumb.model';
import {AuthService} from '../../core/service/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  private breadcrumbs: Breadcrumb[] = [];

  constructor(
    @Inject(DOCUMENT) private document: any,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.breadcrumbs.push({path: '/', name: 'Home'});
    this.breadcrumbs.push({path: location.pathname, name: this.formatPath(location.pathname)});
  }

  private formatPath(path: string): string {
    path = path.substring(1);
    path = path.charAt(0).toUpperCase() + path.slice(1);
    return path;
  }
}
