export interface IEducation {
  id?: number;
  degreeName?: string;
  institute?: string;
  yearOfPassing?: number;
}

export class Education implements IEducation {
  constructor(public id?: number, public degreeName?: string, public institute?: string, public yearOfPassing?: number) {}
}
