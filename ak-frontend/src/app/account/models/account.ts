export interface LoginReqBody {
  username: string;
  password: string;
  rememberMe?: boolean;
}

export interface LoginResBody {
  id_token: string;
}

export interface RegisterReqBody {
  email: string;
  login: string;
  password: string;
}
