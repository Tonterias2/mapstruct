import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MapstructTestModule } from '../../../test.module';
import { EducationUpdateComponent } from 'app/entities/education/education-update.component';
import { EducationService } from 'app/entities/education/education.service';
import { Education } from 'app/shared/model/education.model';

describe('Component Tests', () => {
  describe('Education Management Update Component', () => {
    let comp: EducationUpdateComponent;
    let fixture: ComponentFixture<EducationUpdateComponent>;
    let service: EducationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MapstructTestModule],
        declarations: [EducationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EducationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EducationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EducationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Education(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Education();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
