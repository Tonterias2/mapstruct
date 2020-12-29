import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEducation, Education } from 'app/shared/model/education.model';
import { EducationService } from './education.service';
import { EducationComponent } from './education.component';
import { EducationDetailComponent } from './education-detail.component';
import { EducationUpdateComponent } from './education-update.component';

@Injectable({ providedIn: 'root' })
export class EducationResolve implements Resolve<IEducation> {
  constructor(private service: EducationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEducation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((education: HttpResponse<Education>) => {
          if (education.body) {
            return of(education.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Education());
  }
}

export const educationRoute: Routes = [
  {
    path: '',
    component: EducationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Educations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EducationDetailComponent,
    resolve: {
      education: EducationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Educations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EducationUpdateComponent,
    resolve: {
      education: EducationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Educations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EducationUpdateComponent,
    resolve: {
      education: EducationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Educations',
    },
    canActivate: [UserRouteAccessService],
  },
];
