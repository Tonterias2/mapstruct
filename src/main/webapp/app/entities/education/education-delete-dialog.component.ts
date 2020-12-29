import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEducation } from 'app/shared/model/education.model';
import { EducationService } from './education.service';

@Component({
  templateUrl: './education-delete-dialog.component.html',
})
export class EducationDeleteDialogComponent {
  education?: IEducation;

  constructor(protected educationService: EducationService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.educationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('educationListModification');
      this.activeModal.close();
    });
  }
}
