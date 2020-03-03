export interface IBlogPost {
  id?: number;
  title?: string;
  body?: string;
}

export const defaultValue: Readonly<IBlogPost> = {};
