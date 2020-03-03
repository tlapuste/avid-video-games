import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBlogPostComment, defaultValue } from 'app/shared/model/blog-post-comment.model';

export const ACTION_TYPES = {
  FETCH_BLOGPOSTCOMMENT_LIST: 'blogPostComment/FETCH_BLOGPOSTCOMMENT_LIST',
  FETCH_BLOGPOSTCOMMENT: 'blogPostComment/FETCH_BLOGPOSTCOMMENT',
  CREATE_BLOGPOSTCOMMENT: 'blogPostComment/CREATE_BLOGPOSTCOMMENT',
  UPDATE_BLOGPOSTCOMMENT: 'blogPostComment/UPDATE_BLOGPOSTCOMMENT',
  DELETE_BLOGPOSTCOMMENT: 'blogPostComment/DELETE_BLOGPOSTCOMMENT',
  RESET: 'blogPostComment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBlogPostComment>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type BlogPostCommentState = Readonly<typeof initialState>;

// Reducer

export default (state: BlogPostCommentState = initialState, action): BlogPostCommentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BLOGPOSTCOMMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BLOGPOSTCOMMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BLOGPOSTCOMMENT):
    case REQUEST(ACTION_TYPES.UPDATE_BLOGPOSTCOMMENT):
    case REQUEST(ACTION_TYPES.DELETE_BLOGPOSTCOMMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BLOGPOSTCOMMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BLOGPOSTCOMMENT):
    case FAILURE(ACTION_TYPES.CREATE_BLOGPOSTCOMMENT):
    case FAILURE(ACTION_TYPES.UPDATE_BLOGPOSTCOMMENT):
    case FAILURE(ACTION_TYPES.DELETE_BLOGPOSTCOMMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLOGPOSTCOMMENT_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_BLOGPOSTCOMMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BLOGPOSTCOMMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_BLOGPOSTCOMMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BLOGPOSTCOMMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/blog-post-comments';

// Actions

export const getEntities: ICrudGetAllAction<IBlogPostComment> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BLOGPOSTCOMMENT_LIST,
    payload: axios.get<IBlogPostComment>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IBlogPostComment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BLOGPOSTCOMMENT,
    payload: axios.get<IBlogPostComment>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBlogPostComment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BLOGPOSTCOMMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IBlogPostComment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BLOGPOSTCOMMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBlogPostComment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BLOGPOSTCOMMENT,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
