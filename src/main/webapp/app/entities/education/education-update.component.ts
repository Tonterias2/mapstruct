import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEducation, Education } from 'app/shared/model/education.model';
import { EducationService } from './education.service';

@Component({
  selector: 'jhi-education-update',
  templateUrl: './education-update.component.html',
})
export class EducationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    degreeName: [],
    institute: [],
    yearOfPassing: [],
  });

  constructor(protected educationService: EducationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ education }) => {
      this.updateForm(education);
    });
  }

  updateForm(education: IEducation): void {
    this.editForm.patchValue({
      id: education.id,
      degreeName: education.degreeName,
      institute: education.institute,
      yearOfPassing: education.yearOfPassing,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const education = this.createFromForm();
    if (education.id !== undefined) {
      this.subscribeToSaveResponse(this.educationService.update(education));
    } else {
      this.subscribeToSaveResponse(this.educationService.create(education));
    }
  }

  private createFromForm(): IEducation {
    return {
      ...new Education(),
      id: this.editForm.get(['id'])!.value,
      degreeName: this.editForm.get(['degreeName'])!.value,
      institute: this.editForm.get(['institute'])!.value,
      yearOfPassing: this.editForm.get(['yearOfPassing'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEducation>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
