import { IBlogPost } from 'app/shared/model/blog-post.model';
import { IUser } from 'app/shared/model/user.model';

export interface IBlogPostComment {
  id?: number;
  body?: string;
  commenter?: IUser;
  comment?: IBlogPost;
}

export const defaultValue: Readonly<IBlogPostComment> = {};
