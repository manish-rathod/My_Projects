import { User, USER_MODEL } from "../models/user_model";
import { Errors, Path, POST } from "typescript-rest";
import { createHash } from "../utility/password_utility";
import jwt from "jsonwebtoken";
import { LoginRequest, LoginResponse } from "../models/login_model";

@Path("/user/login")
export class LoginController {
  @POST
  async loginUser(body: LoginRequest) {
    const user: User = await USER_MODEL.findOne({
      userId: body.userId,
      password: createHash(body.password),
    });

    if (user) {
      const token = jwt.sign({ userId: user._id }, process.env.SECRET_KEY, {
        expiresIn: 86400,
      });
      console.log("token " + token);
      var res: LoginResponse = {
        _id: user._id,
        userId: user.userId,
        userName: user.userName,
        token: token,
        expiresIn: 86400,
      };
      return res;
    }

    throw new Errors.NotFoundError("Userid or password is incorrect");
  }
}
