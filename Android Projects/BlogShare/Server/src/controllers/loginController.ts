import { Path, POST } from "typescript-rest";

@Path("/login")
export class LoginController {
  @POST
  async loginUser(body: any) {
    return body;
  }
}
