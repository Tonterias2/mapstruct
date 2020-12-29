import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEducation } from 'app/shared/model/education.model';

type EntityResponseType = HttpResponse<IEducation>;
type EntityArrayResponseType = HttpResponse<IEducation[]>;

@Injectable({ providedIn: 'root' })
export class EducationService {
  public resourceUrl = SERVER_API_URL + 'api/educations';

  constructor(protected http: HttpClient) {}

  create(education: IEducation): Observable<EntityResponseType> {
    return this.http.post<IEducation>(this.resourceUrl, education, { observe: 'response' });
  }

  update(education: IEducation): Observable<EntityResponseType> {
    return this.http.put<IEducation>(this.resourceUrl, education, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEducation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEducation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
