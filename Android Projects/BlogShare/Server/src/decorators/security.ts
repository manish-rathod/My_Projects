var httpContext = require("express-http-context");
import { Request } from "express";
import jwt from "jsonwebtoken";
import { User, USER_MODEL } from "../models/user_model";
import { Errors, PreProcessor } from "typescript-rest";

const verifyUser = async (req: Request) => {
  var result: any;

  try {
    var token = req.headers["authorization"];

    result = jwt.verify(token.split(" ")[1], process.env.SECRET_KEY);
    console.log("Token Verified");
  } catch {
    throw new Errors.UnauthorizedError("Token Invalid");
  }

  console.log(result);

  const user: User = await USER_MODEL.findOne({
    _id: result["userId"],
  });

  console.log("user before set" + user);

  if (user) {
    console.log("setting user in context");
    httpContext.set("user", user);
    console.log("user after set " + httpContext.get("user"));
    return req;
  } else {
    throw new Errors.NotFoundError("User not found");
  }
};

export const Verify = () => PreProcessor(verifyUser);

export const getUser = (): User => {
  return httpContext.get("user");
};
