export class LoginResponse {
  userName: string;
  userId: string;
  token: string;
  expiresIn: number;
  refToken?: string;
  refTokenEcpiresIn?: number;
}

export class LoginRequest {
  userId: string;
  password: string;
}
