import { IBlogPostCommentVM } from 'app/shared/model/blog-post-comment-vm.model';

export interface IBlogPostVM {
  comment?: string;
  authorName?: string;
  blogPostCommentVMS?: IBlogPostCommentVM[];
}

export const defaultValue: Readonly<IBlogPostVM> = {};
