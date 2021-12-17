import { User, USER_MODEL } from "../models/user_model";
import { Errors, Path, POST } from "typescript-rest";
import { createHash } from "../utility/password_utility";
import jwt from "jsonwebtoken";
import { LoginResponse } from "../models/login_model";

@Path("/user")
export class UserController {
  @POST
  async createUser(body: User) {
    const findUser: User = await USER_MODEL.findOne({
      userId: body.userId,
    });

    if (findUser) {
      throw new Errors.ConflictError("UserId Taken");
    }

    body.password = createHash(body.password);
    var model = new USER_MODEL(body);
    await model.save();

    const user: User = await USER_MODEL.findOne({
      userId: body.userId,
      password: body.password,
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
  }
}
