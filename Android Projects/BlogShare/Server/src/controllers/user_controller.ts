import { User, USER_MODEL } from "../models/user_model";
import { Errors, Path, POST } from "typescript-rest";
import { createHash } from "../utility/password_utility";

@Path("/user")
export class UserController {
  @POST
  async createUser(body: User) {
    body.password = createHash(body.password);
    var model = new USER_MODEL(body);
    await model.save();
    return body;
  }
}
