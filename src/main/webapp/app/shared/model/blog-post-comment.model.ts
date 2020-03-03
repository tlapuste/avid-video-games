import { IBlogUser } from 'app/shared/model/blog-user.model';
import { IBlogPost } from 'app/shared/model/blog-post.model';

export interface IBlogPostComment {
  id?: number;
  body?: string;
  commenter?: IBlogUser;
  comment?: IBlogPost;
}

export const defaultValue: Readonly<IBlogPostComment> = {};
