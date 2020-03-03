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

import { IBlogPost, defaultValue } from 'app/shared/model/blog-post.model';

export const ACTION_TYPES = {
  FETCH_BLOGPOST_LIST: 'blogPost/FETCH_BLOGPOST_LIST',
  FETCH_BLOGPOST: 'blogPost/FETCH_BLOGPOST',
  CREATE_BLOGPOST: 'blogPost/CREATE_BLOGPOST',
  UPDATE_BLOGPOST: 'blogPost/UPDATE_BLOGPOST',
  DELETE_BLOGPOST: 'blogPost/DELETE_BLOGPOST',
  RESET: 'blogPost/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBlogPost>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type BlogPostState = Readonly<typeof initialState>;

// Reducer

export default (state: BlogPostState = initialState, action): BlogPostState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BLOGPOST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BLOGPOST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BLOGPOST):
    case REQUEST(ACTION_TYPES.UPDATE_BLOGPOST):
    case REQUEST(ACTION_TYPES.DELETE_BLOGPOST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BLOGPOST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BLOGPOST):
    case FAILURE(ACTION_TYPES.CREATE_BLOGPOST):
    case FAILURE(ACTION_TYPES.UPDATE_BLOGPOST):
    case FAILURE(ACTION_TYPES.DELETE_BLOGPOST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BLOGPOST_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_BLOGPOST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BLOGPOST):
    case SUCCESS(ACTION_TYPES.UPDATE_BLOGPOST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BLOGPOST):
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

const apiUrl = 'api/blog-posts';

// Actions

export const getEntities: ICrudGetAllAction<IBlogPost> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BLOGPOST_LIST,
    payload: axios.get<IBlogPost>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IBlogPost> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BLOGPOST,
    payload: axios.get<IBlogPost>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBlogPost> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BLOGPOST,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IBlogPost> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BLOGPOST,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBlogPost> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BLOGPOST,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
